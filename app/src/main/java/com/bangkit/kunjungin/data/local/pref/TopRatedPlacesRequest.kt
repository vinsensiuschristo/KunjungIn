package com.bangkit.kunjungin.data.local.pref

data class TopRatedPlacesRequest(
    val PlaceName: String,
    val types: String,
    val city_id: Int
)