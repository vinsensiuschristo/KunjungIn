package com.bangkit.kunjungin.data.remote.response

import com.google.gson.annotations.SerializedName

class AddUserRecommendationResponse (
    @SerializedName("error") val error: Boolean,
    @SerializedName("message") val message: String,
)