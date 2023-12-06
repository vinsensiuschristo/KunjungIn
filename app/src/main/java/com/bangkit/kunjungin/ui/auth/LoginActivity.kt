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
import com.bangkit.kunjungin.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val signupTextView: TextView = binding.toSignup
        val spannableString = SpannableString(getString(R.string.to_signup))
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(view: View) {
                val intent = Intent(this@LoginActivity, SignupActivity::class.java)
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