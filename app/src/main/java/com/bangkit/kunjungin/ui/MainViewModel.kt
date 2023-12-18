package com.bangkit.kunjungin.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.kunjungin.data.local.pref.UserModel
import com.bangkit.kunjungin.data.remote.response.NearbyPlacesResponse
import com.bangkit.kunjungin.data.repository.DestinationRepository
import com.bangkit.kunjungin.utils.Event
import kotlinx.coroutines.launch

class MainViewModel(private val repository: DestinationRepository) : ViewModel() {

    val toast: LiveData<Event<String>> = repository.toast
    val loading: LiveData<Boolean> = repository.loading
    val nearbyPlacesResponse: LiveData<NearbyPlacesResponse> = repository.nearbyPlacesResponse
    fun getNearbyPlaces(placeName: String, latitude: String, longitude: String, cityId: Int, token: String) {
        viewModelScope.launch {
            repository.getNearbyPlaces(placeName, latitude, longitude, cityId, token)
        }
    }

    fun getSession(): LiveData<UserModel> {
        return repository.getSession()
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}