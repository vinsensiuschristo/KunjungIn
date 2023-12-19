package com.bangkit.kunjungin.data.local.pref

data class UserModel(
    val email: String,
    val token: String,
    val name: String,
    val userId: Int,
    val cityId: Int,
    val recommendationStatus: Boolean,
    val isLogin: Boolean,
)