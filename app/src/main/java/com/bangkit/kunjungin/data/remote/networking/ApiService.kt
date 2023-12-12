package com.bangkit.kunjungin.data.remote.networking

import com.bangkit.kunjungin.data.local.pref.DataLogin
import com.bangkit.kunjungin.data.local.pref.DataSignup
import com.bangkit.kunjungin.data.remote.response.LoginResponse
import com.bangkit.kunjungin.data.remote.response.SignupResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("users/register")
    fun postSignup(
        @Body request: DataSignup
    ): Call<SignupResponse>

    @POST("users/login")
    fun postLogin(
        @Body request: DataLogin
    ): Call<LoginResponse>
}