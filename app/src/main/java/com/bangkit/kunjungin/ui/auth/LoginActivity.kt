package com.bangkit.kunjungin.ui.auth

import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.kunjungin.R
import com.bangkit.kunjungin.data.local.pref.UserModel
import com.bangkit.kunjungin.databinding.ActivityLoginBinding
import com.bangkit.kunjungin.ui.MainActivity
import com.bangkit.kunjungin.utils.ViewModelFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }

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

        setupAction()
    }

    private fun setupAction() {
        binding.apply {
            loginButton.setOnClickListener {
                val emailIsEmpty = email.text!!.isEmpty()
                val passwordIsEmpty = password.text!!.isEmpty()

                if (emailIsEmpty) {
                    email.error = binding.root.context.getString(R.string.required_field)
                }
                if (passwordIsEmpty) {
                    password.error = binding.root.context.getString(R.string.required_field)
                }
                if (!emailIsEmpty && !passwordIsEmpty) {
                    showLoading()
                    postData()
                    showToast()
                    loginViewModel.login()
                }
            }
        }
    }

    private fun postData() {
        binding.apply {
            loginViewModel.postLogin(
                email.text.toString(),
                password.text.toString()
            )
        }
        loginViewModel.loginResponse.observe(this) { response ->
            binding.apply {
                val email = email.text.toString()
                val token = response.loginResult?.token.toString()
                val name = response.loginResult?.name.toString()
                val cityId = response.loginResult?.cityId!!.toInt()
                saveSession(UserModel(email, token, name, cityId,true))
            }
        }
    }

    private fun showToast() {
        loginViewModel.toast.observe(this) {
            it.getContentIfNotHandled()?.let { toast ->
                Toast.makeText(this, toast, Toast.LENGTH_SHORT).show()
                navigateToMain()
            }
        }
    }

    private fun showLoading() {
        loginViewModel.loading.observe(this) {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }
    }

    private fun navigateToMain() {
        loginViewModel.loginResponse.observe(this) { response ->
            if (!response.error) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }

    private fun saveSession(user: UserModel) {
        loginViewModel.saveSession(user)
    }
}