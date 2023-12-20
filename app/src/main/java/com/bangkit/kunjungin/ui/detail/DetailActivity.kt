package com.bangkit.kunjungin.ui.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.kunjungin.R
import com.bangkit.kunjungin.databinding.ActivityDetailBinding
import com.bangkit.kunjungin.ui.home.NearbyPlaceAdapter.Companion.DESTINATION_ID
import com.bangkit.kunjungin.utils.ViewModelFactory
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private var token = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUser()
    }

    private fun setupData() {
        val placeId = intent.getIntExtra(DESTINATION_ID, 0)
        detailViewModel.getPlaceDetails(placeId, token)
        Log.d("Token", "$token")

        detailViewModel.getPlaceDetailsResponse.observe(this@DetailActivity) { placeDetailsResponse ->
            placeDetailsResponse?.result?.let { result ->
                binding.detailTitle.text = result.name
                binding.detailLocation.text = result.address
                binding.description.text = result.description
                binding.distanceToLocation.text = result.rating
                val apiKey = "AIzaSyDsoEFR73xhtFhtlzBaXaXKLybnH9gp8BI"
                val maxWidth = 400
                val photoUrl = "https://maps.googleapis.com/maps/api/place/photo" +
                        "?maxwidth=$maxWidth" +
                        "&photo_reference=${result.photoReference}" +
                        "&key=$apiKey"

                Picasso.get().load(photoUrl).fit().into(binding.detailImage)
            }
        }
    }

    private fun setupUser() {
        detailViewModel.getSession().observe(this@DetailActivity) {
            token = it.token
            Log.d("Token", "$token")
            setupData()
        }

        binding.btnBack.setOnClickListener {
            finish()
        }
        binding.btnShare.setOnClickListener {
            shareDetails()
        }
    }

    private fun shareDetails() {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        val shareBody = "Check out this amazing place: ${binding.detailTitle.text}"
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
        startActivity(Intent.createChooser(shareIntent, "Bagikan via"))
    }
}