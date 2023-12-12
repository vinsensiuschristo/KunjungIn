package com.bangkit.kunjungin.data.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("error") val error: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("loginResult") val loginResult: LoginResult? = null,
)

data class LoginResult(
    @SerializedName("userId") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("token") val token: String,
)