package com.bangkit.kunjungin.ui

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.bangkit.kunjungin.R
import com.bangkit.kunjungin.data.local.pref.PlaceType
import com.bangkit.kunjungin.databinding.ActivityMainBinding
import com.bangkit.kunjungin.ui.auth.LoginActivity
import com.bangkit.kunjungin.ui.preference.PreferenceActivity
import com.bangkit.kunjungin.utils.ViewModelFactory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private var token = ""
    private var cityId = 1
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    val PERMISSION_ID = 1010
    private var isLocationUpdated = false
    private var userId = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        val selectedTypes = intent.getParcelableArrayListExtra<PlaceType>("selectedTypes")
        setupUser()
        requestPermission()

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
            val location: Location? = task.result
            val selectedTypes = intent.getParcelableArrayListExtra<PlaceType>("selectedTypes")
            val randomPlaceType = selectedTypes?.shuffled()?.first()
            val selectedPlaceTypeName = randomPlaceType?.name
            if (location == null) {
                newLocationData()
            } else {
                Log.d("Debug:", "Your Location:" + location.longitude)
                mainViewModel.getNearbyPlaces(
                    selectedPlaceTypeName.toString(),
                    location.latitude.toString(),
                    location.longitude.toString(),
                    cityId,
                    token
                )
                isLocationUpdated = true
                showLoading()
            }
        }
    }

    private fun setupUser() {
        mainViewModel.getSession().observe(this@MainActivity) {
            token = it.token
            cityId = it.cityId
            userId = it.userId
            if (!it.isLogin) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } else {
                if (!it.recommendationStatus) {
                    redirectToPreferenceActivity()
                }
            }
        }
    }

    private fun redirectToPreferenceActivity() {
        val intent = Intent(this, PreferenceActivity::class.java)
        intent.putExtra("token", token)
        intent.putExtra("userId", userId)
        startActivity(intent)
        finish()
    }

    private fun checkPermission(): Boolean {
        if (
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSION_ID
        )
    }

    private fun handlePermissionResult(grantResults: IntArray) {
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (isLocationEnabled()) {
                getLastLocation()
            } else {
                Toast.makeText(
                    this,
                    "Harap aktifkan Lokasi perangkat Anda untuk rekomendasi yang lebih akurat",
                    Toast.LENGTH_SHORT
                ).show()
                mainViewModel.getNearbyPlaces(
                    "Museum",
                    "-7.607950",
                    "110.203690",
                    cityId,
                    token
                )
            }
        } else {
            Toast.makeText(
                this,
                "Izin lokasi tidak diberikan, data tidak dapat dimuat",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            PERMISSION_ID -> {
                handlePermissionResult(grantResults)
            }
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun getLastLocation() {
        if (checkPermission()) {
            if (isLocationEnabled()) {
                fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
                    val location: Location? = task.result
                    if (location == null) {
                        newLocationData()
                    } else {
                        Log.d("Debug:", "Your Location:" + location.longitude)
                        mainViewModel.getNearbyPlaces(
                            "Museum",
                            location.latitude.toString(),
                            location.longitude.toString(),
                            cityId,
                            token
                        )
                        isLocationUpdated = true
                    }
                }
            } else {
                Toast.makeText(this, "Harap aktifkan Lokasi perangkat Anda", Toast.LENGTH_SHORT)
                    .show()
            }
        } else {
            requestPermission()
        }
    }

    private fun newLocationData() {
        val locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 1
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest, locationCallback, Looper.myLooper()
        )
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val lastLocation: Location = locationResult.lastLocation!!
            Log.d("Debug:", "your last last location: " + lastLocation.longitude.toString())
        }
    }

    private fun showLoading() {
        mainViewModel.loading.observe(this) {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }
    }
}