package com.bangkit.kunjungin.data.local.pref

data class NearbyPlacesRequest(
    val PlaceName: String,
    val latitude: String,
    val longitude: String,
    val city_id: Int
)