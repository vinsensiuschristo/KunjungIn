package com.bangkit.kunjungin.data.local.pref

data class UserModel(
    val email: String,
    val token: String,
    val isLogin: Boolean,
)