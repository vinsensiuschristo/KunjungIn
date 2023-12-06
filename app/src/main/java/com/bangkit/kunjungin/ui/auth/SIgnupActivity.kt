package com.bangkit.kunjungin.ui.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.kunjungin.databinding.ActivitySignupBinding

class SIgnupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}