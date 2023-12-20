package com.bangkit.kunjungin.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.kunjungin.data.local.pref.UserModel
import com.bangkit.kunjungin.data.remote.response.GetPlaceDetailsResponse
import com.bangkit.kunjungin.data.repository.DestinationRepository
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: DestinationRepository) : ViewModel() {

    val loading: LiveData<Boolean> = repository.loading
    val getPlaceDetailsResponse: LiveData<GetPlaceDetailsResponse> = repository.getPlaceDetailsResponse

    fun getPlaceDetails(placeId: Int, token: String) {
        viewModelScope.launch {
            repository.getPlaceDetails(placeId, token)
        }
    }

    fun getSession(): LiveData<UserModel> {
        return repository.getSession()
    }
}