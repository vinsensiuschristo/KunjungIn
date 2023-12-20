package com.bangkit.kunjungin.data.remote.networking

import com.bangkit.kunjungin.data.local.pref.AddUserRecommendationRequest
import com.bangkit.kunjungin.data.local.pref.DataLogin
import com.bangkit.kunjungin.data.local.pref.DataSignup
import com.bangkit.kunjungin.data.local.pref.NearbyPlacesRequest
import com.bangkit.kunjungin.data.local.pref.TopRatedPlacesRequest
import com.bangkit.kunjungin.data.remote.response.AddUserRecommendationResponse
import com.bangkit.kunjungin.data.remote.response.GetPlaceDetailsResponse
import com.bangkit.kunjungin.data.remote.response.LoginResponse
import com.bangkit.kunjungin.data.remote.response.NearbyPlacesResponse
import com.bangkit.kunjungin.data.remote.response.SignupResponse
import com.bangkit.kunjungin.data.remote.response.TopRatedPlacesResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("users/register")
    fun postSignup(
        @Body request: DataSignup
    ): Call<SignupResponse>

    @POST("users/login")
    fun postLogin(
        @Body request: DataLogin
    ): Call<LoginResponse>

    @POST("placeRecommendation/places/nearby")
    fun getNearbyPlaces(
        @Header("Cookie") token: String,
        @Body request: NearbyPlacesRequest
    ): Call<NearbyPlacesResponse>

    @POST("placeRecommendation/places/rating")
    fun getTopRatedPlaces(
        @Header("Cookie") token: String,
        @Body request: TopRatedPlacesRequest
    ): Call<TopRatedPlacesResponse>

    @GET("placeRecommendation/places/{id}")
    fun getPlaceDetails(
        @Header("Cookie") token: String,
        @Path("id") userId: Int,
    ): Call<GetPlaceDetailsResponse>

    @POST("userRecommendation/{id}/recommendation")
    fun addUserRecommendation(
        @Header("Cookie") token: String,
        @Path("id") userId: Int,
        @Body request: AddUserRecommendationRequest
    ): Call<AddUserRecommendationResponse>
}