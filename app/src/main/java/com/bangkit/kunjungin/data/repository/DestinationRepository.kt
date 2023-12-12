package com.bangkit.kunjungin.data.repository

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.bangkit.kunjungin.data.local.pref.DataLogin
import com.bangkit.kunjungin.data.local.pref.DataSignup
import com.bangkit.kunjungin.data.local.pref.UserModel
import com.bangkit.kunjungin.data.local.pref.UserPreferences
import com.bangkit.kunjungin.data.remote.networking.ApiService
import com.bangkit.kunjungin.data.remote.response.LoginResponse
import com.bangkit.kunjungin.data.remote.response.SignupResponse
import com.bangkit.kunjungin.utils.Event
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DestinationRepository private constructor(
    private val userPreferences: UserPreferences,
    private val apiService: ApiService,
) {
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _toast = MutableLiveData<Event<String>>()
    val toast: LiveData<Event<String>> = _toast

    private val _signupResponse = MutableLiveData<SignupResponse>()
    val signupResponse: LiveData<SignupResponse> = _signupResponse

    private val _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse: LiveData<LoginResponse> = _loginResponse


    suspend fun saveSession(user: UserModel) {
        userPreferences.saveSession(user)
    }

    fun getSession(): LiveData<UserModel> {
        return userPreferences.getSession().asLiveData()
    }

    suspend fun login() {
        userPreferences.login()
    }

    suspend fun logout() {
        userPreferences.logout()
    }

    fun postSignup(name: String, email: String,  address: String, password: String, city: Int) {
        val signupRequest = DataSignup(email, name, password, address, city)
        _loading.value = true
        val client = apiService.postSignup(signupRequest)
        client.enqueue(object : Callback<SignupResponse> {
            override fun onResponse(
                call: Call<SignupResponse>,
                response: Response<SignupResponse>,
            ) {
                if (response.isSuccessful && response.body() != null) {
                    _toast.value = Event(response.body()?.message.toString())
                    _signupResponse.value = response.body()
                } else {
                    when (response.code()) {
                        400 -> {
                            // Bad request error
                            val errorBody = response.errorBody()?.string()
                            try {
                                val errorResponse = JSONObject(errorBody)
                                val errorMessage = errorResponse.optString("message")
                                _toast.value = Event(errorMessage)
                            } catch (e: Exception) {
                                _toast.value = Event("Bad Request: Failed to parse error message")
                            }
                        }
                        409 -> {
                            // Account already exists error
                            _toast.value = Event("Account already exists. Please use a different email or try logging in.")
                        }
                        else -> {
                            // Other error
                            _toast.value = Event(response.message().toString())
                        }
                    }
                }
                _loading.value = false
            }

            override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                _loading.value = false
                _toast.value = Event(t.message.toString())
                Log.e(ContentValues.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun postLogin(email: String, password: String) {
        val loginRequest = DataLogin(email, password)
        _loading.value = true
        val client = apiService.postLogin(loginRequest)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    _toast.value = Event(response.body()?.message.toString())
                    _loginResponse.value = response.body()
                } else {
                    _toast.value = when (response.code()) {
                        401 -> {
                            // Invalid password error
                            Event("Invalid password. Please check your password and try again.")
                        }
                        404 -> {
                            // Account not found error
                            Event("Account not found. Please register an account.")
                        }
                        else -> {
                            // Other error
                            Event(response.message().toString())
                        }
                    }
                }
                _loading.value = false
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _loading.value = false
                _toast.value = Event(t.message.toString())
                Log.e(ContentValues.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }


    companion object {
        @Volatile
        private var instance: DestinationRepository? = null
        fun getInstance(
            userPreference: UserPreferences,
            apiService: ApiService,
        ): DestinationRepository =
            instance ?: synchronized(this) {
                instance ?: DestinationRepository(userPreference, apiService)
            }.also { instance = it }
    }
}