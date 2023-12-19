package com.bangkit.kunjungin.data.repository

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.bangkit.kunjungin.data.local.pref.AddUserRecommendationRequest
import com.bangkit.kunjungin.data.local.pref.DataLogin
import com.bangkit.kunjungin.data.local.pref.DataSignup
import com.bangkit.kunjungin.data.local.pref.NearbyPlacesRequest
import com.bangkit.kunjungin.data.local.pref.UserModel
import com.bangkit.kunjungin.data.local.pref.UserPreferences
import com.bangkit.kunjungin.data.remote.networking.ApiService
import com.bangkit.kunjungin.data.remote.response.AddUserRecommendationResponse
import com.bangkit.kunjungin.data.remote.response.LoginResponse
import com.bangkit.kunjungin.data.remote.response.NearbyPlacesResponse
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

    private val _nearbyPlacesResponse = MutableLiveData<NearbyPlacesResponse>()
    val nearbyPlacesResponse: LiveData<NearbyPlacesResponse> = _nearbyPlacesResponse

    private val _addUserRecommendationResponse = MutableLiveData<AddUserRecommendationResponse>()
    val addUserRecommendationResponse: LiveData<AddUserRecommendationResponse> = _addUserRecommendationResponse

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

    suspend fun updateRecommendationStatus(recommendationStatus: Boolean) {
        userPreferences.updateRecommendationStatus(recommendationStatus)
    }

    fun postSignup(name: String, email: String, address: String, password: String, city: Int) {
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
                            _toast.value =
                                Event("Account already exists. Please use a different email or try logging in.")
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

    fun getNearbyPlaces(placeName: String, latitude: String, longitude: String, cityId: Int, token: String) {
        val nearbyPlacesRequest = NearbyPlacesRequest(placeName, latitude, longitude, cityId)
        _loading.value = true
        Log.d("NearbyPlaces", "Token: $token")
        val client = apiService.getNearbyPlaces("$token", nearbyPlacesRequest)
        client.enqueue(object : Callback<NearbyPlacesResponse> {
            override fun onResponse(call: Call<NearbyPlacesResponse>, response: Response<NearbyPlacesResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    _toast.value = Event(response.body()?.message.toString())
                    _nearbyPlacesResponse.value = response.body()
                    Log.d("NearbyPlaces", "API call successful")
                    Log.d("NearbyPlaces", "Response: ${response.body()}")
                } else {
                    Event(response.message().toString())
                    Log.e("NearbyPlaces", "API call unsuccessful")
                    Log.e("NearbyPlaces", "Error message: ${response.message()}")
                    Log.e("NearbyPlaces", "Error body: ${response.errorBody()?.string()}")

                }
                _loading.value = false
            }

            override fun onFailure(call: Call<NearbyPlacesResponse>, t: Throwable) {
                _loading.value = false
                _toast.value = Event(t.message.toString())
                Log.e(ContentValues.TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun postUserRecommendation(placeType: List<String>, token: String, userId: Int) {
        val postUserRecommendationRequest = AddUserRecommendationRequest(placeType)
        _loading.value = true
        Log.d("PlaceType", "PlaceType Request: $placeType")
        val client = apiService.addUserRecommendation("$token",userId, postUserRecommendationRequest)
        client.enqueue(object : Callback<AddUserRecommendationResponse> {
            override fun onResponse(call: Call<AddUserRecommendationResponse>, response: Response<AddUserRecommendationResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    _toast.value = Event(response.body()?.message.toString())
                    _addUserRecommendationResponse.value = response.body()
                    Log.d("PostAddRecommendation", "API call successful")
                    Log.d("PostAddRecommendation", "Response: ${response.body()}")
                } else {
                    Event(response.message().toString())
                    Log.e("PostAddRecommendation", "API call unsuccessful")
                    Log.e("PostAddRecommendation", "Error message: ${response.message()}")
                    Log.e("PostAddRecommendation", "Error body: ${response.errorBody()?.string()}")

                }
                _loading.value = false
            }

            override fun onFailure(call: Call<AddUserRecommendationResponse>, t: Throwable) {
                _loading.value = false
                _toast.value = Event(t.message.toString())
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