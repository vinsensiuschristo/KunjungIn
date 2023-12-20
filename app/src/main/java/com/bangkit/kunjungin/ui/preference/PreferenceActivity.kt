package com.bangkit.kunjungin.ui.preference

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.kunjungin.R
import com.bangkit.kunjungin.data.local.pref.PlaceType
import com.bangkit.kunjungin.databinding.ActivityPreferenceBinding
import com.bangkit.kunjungin.ui.MainActivity
import com.bangkit.kunjungin.utils.ViewModelFactory

class PreferenceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPreferenceBinding
    private val preferenceViewModel by viewModels<PreferenceViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private val selectedTypes = mutableListOf<PlaceType>()
    private var token: String = ""
    private var userId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preference)
        binding = ActivityPreferenceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        token = intent.getStringExtra("token")!!
        Log.d("PreferenceActivity", "Token: $token")
        userId = intent.getIntExtra("userId", 0)
        Log.d("PreferenceActivity", "UserId: $userId")
        val saveButton = binding.saveButton

        saveButton.isEnabled = false
        saveButton.setOnClickListener {
            savePreferences()
        }
        showLoading()

        binding.checkbox1.setOnCheckedChangeListener { _, _ ->
            checkButtonEnabled()
        }
        binding.checkbox2.setOnCheckedChangeListener { _, _ ->
            checkButtonEnabled()
        }
        binding.checkbox3.setOnCheckedChangeListener { _, _ ->
            checkButtonEnabled()
        }
    }

    private fun checkButtonEnabled() {
        binding.saveButton.isEnabled =
            binding.checkbox1.isChecked || binding.checkbox2.isChecked || binding.checkbox3.isChecked
    }

    private fun savePreferences() {
        selectedTypes.clear()

        if (binding.checkbox1.isChecked) {
            selectedTypes.add(PlaceType("2", "Museum"))
        }
        if (binding.checkbox2.isChecked) {
            selectedTypes.add(PlaceType("2", "Candi"))
        }
        if (binding.checkbox3.isChecked) {
            selectedTypes.add(PlaceType("3", "Gunung"))
        }

        preferenceViewModel.updateRecommendationStatus(true)
        sendToAPI()
        sendToMainActivity()
    }

    private fun sendToAPI() {
        val selectedIds = selectedTypes.map { it.id }
        Log.d("PreferenceActivity", "SelectedIds: $selectedIds")
        preferenceViewModel.postUserRecommendation(selectedIds, token, userId)
    }

    private fun sendToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putParcelableArrayListExtra("selectedTypes", ArrayList(selectedTypes))
        startActivity(intent)
    }

    private fun showLoading() {
        preferenceViewModel.loading.observe(this) {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }
    }
}