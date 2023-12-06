package com.bangkit.kunjungin.ui.auth

import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.kunjungin.R
import com.bangkit.kunjungin.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val signupTextView: TextView = binding.toLogin
        val spannableString = SpannableString(getString(R.string.to_login))
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(view: View) {
                val intent = Intent(this@SignupActivity, LoginActivity::class.java)
                startActivity(intent)
            }
        }
        spannableString.setSpan(
            clickableSpan, 18, spannableString.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        signupTextView.text = spannableString
        signupTextView.movementMethod = LinkMovementMethod.getInstance()
    }
}