package com.bangkit.kunjungin.data.remote.response

import com.google.gson.annotations.SerializedName

data class GetPlaceDetailsResponse(

	@field:SerializedName("result")
	val result: Result? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class Result(

	@field:SerializedName("types")
	val types: String? = null,

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("latitude")
	val latitude: String? = null,

	@field:SerializedName("rating")
	val rating: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("photo_reference")
	val photoReference: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("opening_hours")
	val openingHours: String? = null,

	@field:SerializedName("phone_number")
	val phoneNumber: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("longitude")
	val longitude: String? = null,

	@field:SerializedName("city_id")
	val cityId: Int? = null,

	@field:SerializedName("user_rating")
	val userRating: Int? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
