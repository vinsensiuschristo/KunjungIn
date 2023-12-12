package com.bangkit.kunjungin.di

import android.content.Context
import com.bangkit.kunjungin.data.local.pref.UserPreferences
import com.bangkit.kunjungin.data.local.pref.dataStore
import com.bangkit.kunjungin.data.remote.networking.ApiConfig
import com.bangkit.kunjungin.data.repository.DestinationRepository

object Injection {
    fun provideRepository(context: Context): DestinationRepository {
        val pref = UserPreferences.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService()
        return DestinationRepository.getInstance(pref, apiService)
    }
}