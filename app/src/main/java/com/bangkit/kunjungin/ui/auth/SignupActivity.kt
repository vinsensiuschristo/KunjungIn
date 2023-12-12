package com.bangkit.kunjungin.ui.auth

import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.kunjungin.R
import com.bangkit.kunjungin.data.local.pref.City
import com.bangkit.kunjungin.databinding.ActivitySignupBinding
import com.bangkit.kunjungin.utils.ViewModelFactory

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private val signupViewModel by viewModels<SignupViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private val city: List<City> = listOf(
        City(1, "Jakarta"),
        City(2, "Surabaya"),
        City(3, "Bandung"),
        City(4, "Semarang"),
        City(5, "Yogyakarta"),
        City(6, "Malang"),
        City(7, "Solo"),
        City(8, "Bogor"),
        City(9, "Bekasi"),
        City(10, "Tangerang"),
        City(11, "Cilacap"),
        City(12, "Probolinggo"),
        City(13, "Purwokerto"),
        City(14, "Pekalongan"),
        City(15, "Kab. Banyuwangi"),
        City(16, "Kota Yogyakarta"),
        City(17, "Kabupaten Bantul"),
        City(18, "Kabupaten Sleman"),
        City(19, "Kabupaten Kulon Progo"),
        City(20, "Kabupaten Gunung Kidul"),
        City(21, "Kota Semarang"),
        City(22, "Kota Surakarta"),
        City(23, "Kota Salatiga"),
        City(24, "Kota Pekalongan"),
        City(25, "Kota Tegal"),
        City(26, "Kota Magelang"),
        City(27, "Kota Purwokerto"),
        City(28, "Kabupaten Banjarnegara"),
        City(29, "Kota Wonosobo"),
        City(30, "Kebumen"),
        City(31, "Kota Purworejo"),
        City(32, "Kota Wonogiri"),
        City(33, "Kota Klaten")
    )

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

        val autoCompleteTextView: AutoCompleteTextView = binding.addressAutoComplete
        val adapter = ArrayAdapter(this@SignupActivity, android.R.layout.simple_dropdown_item_1line, city.map { it.name })
        autoCompleteTextView.setAdapter(adapter)

        setupAction()
    }

    private fun setupAction() {
        binding.apply {
            signupButton.setOnClickListener {
                val usernameIsEmpty = username.text!!.isEmpty()
                val emailIsEmpty = email.text!!.isEmpty()
                val addressIsEmpty = address.text!!.isEmpty()
                val passwordIsEmpty = password.text!!.isEmpty()
                val selectedCityIsEmpty = addressAutoComplete.text.isEmpty()

                if (selectedCityIsEmpty) {
                    addressAutoComplete.error = binding.root.context.getString(R.string.required_field)
                }
                if (usernameIsEmpty) {
                    username.error = binding.root.context.getString(R.string.required_field)
                }
                if (emailIsEmpty) {
                    email.error = binding.root.context.getString(R.string.required_field)
                }
                if (addressIsEmpty) {
                    address.error = binding.root.context.getString(R.string.required_field)
                }
                if (passwordIsEmpty) {
                    password.error = binding.root.context.getString(R.string.required_field)
                }
                if (!usernameIsEmpty && !emailIsEmpty && !addressIsEmpty && !passwordIsEmpty && !selectedCityIsEmpty) {
                    postData()
                    showLoading()
                    showToast()
                    navigateToLogin()
                }
            }
        }
    }

    private fun postData() {
        binding.apply {
            val selectedCity = city.find { it.name == addressAutoComplete.text.toString() }
            val cityId = selectedCity?.id ?: 0
            signupViewModel.postSignup(
                username.text.toString(),
                email.text.toString(),
                address.text.toString(),
                password.text.toString(),
                cityId
            )
        }
    }

    private fun navigateToLogin() {
        signupViewModel.signupResponse.observe(this) { response ->
            if (!response.error) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
    }

    private fun showLoading() {
        signupViewModel.loading.observe(this) {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }
    }

    private fun showToast() {
        signupViewModel.toast.observe(this) {
            it.getContentIfNotHandled()?.let { toast ->
                Toast.makeText(this, toast, Toast.LENGTH_SHORT).show()
            }
        }
    }
}