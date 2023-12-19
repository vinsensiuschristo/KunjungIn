package com.bangkit.kunjungin.data.remote.response
import com.google.gson.annotations.SerializedName

data class TopRatedPlacesResponse(

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("result")
    val result: List<TopRatedPlaceRecommendation>
)

data class TopRatedPlaceRecommendation(

    @field:SerializedName("id")
    val id: Double,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("photo_reference")
    val photoReference: String,

    @field:SerializedName("rating")
    val rating: Double,

    @field:SerializedName("types")
    val types: String
)