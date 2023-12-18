package com.bangkit.kunjungin.data.remote.response

import com.google.gson.annotations.SerializedName

data class NearbyPlacesResponse(

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("result")
    val result: List<NearbyPlaceRecommendation>
)

data class NearbyPlaceRecommendation(

    @field:SerializedName("distance")
    val distance: Double,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("photo_reference")
    val photoReference: String,

    @field:SerializedName("rating")
    val rating: Double,

    @field:SerializedName("types")
    val types: String
)