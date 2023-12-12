package com.bangkit.kunjungin.data.remote.response

import com.google.gson.annotations.SerializedName

data class SignupResponse(
    @SerializedName("status") val error: Boolean,
    @SerializedName("message") val message: String,
)