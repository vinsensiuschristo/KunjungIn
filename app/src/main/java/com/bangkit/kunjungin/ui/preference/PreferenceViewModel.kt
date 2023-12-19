package com.bangkit.kunjungin.ui.preference

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.kunjungin.data.remote.response.AddUserRecommendationResponse
import com.bangkit.kunjungin.data.repository.DestinationRepository
import com.bangkit.kunjungin.utils.Event
import kotlinx.coroutines.launch

class PreferenceViewModel(private val repository: DestinationRepository) : ViewModel() {
    val loading: LiveData<Boolean> = repository.loading

    fun updateRecommendationStatus(recommendationStatus: Boolean) {
        viewModelScope.launch {
            repository.updateRecommendationStatus(recommendationStatus)
        }
    }

    fun postUserRecommendation(placeType: List<String>, token: String, userId: Int) {
        viewModelScope.launch {
            repository.postUserRecommendation(placeType, token, userId)
        }
    }
}