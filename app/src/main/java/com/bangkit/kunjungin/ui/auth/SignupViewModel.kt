package com.bangkit.kunjungin.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.kunjungin.data.remote.response.SignupResponse
import com.bangkit.kunjungin.data.repository.DestinationRepository
import com.bangkit.kunjungin.utils.Event
import kotlinx.coroutines.launch

class SignupViewModel(private val repository: DestinationRepository) : ViewModel() {
    val toast: LiveData<Event<String>> = repository.toast
    val loading: LiveData<Boolean> = repository.loading
    val signupResponse: LiveData<SignupResponse> = repository.signupResponse

    fun postSignup(name: String, email: String, address: String, password: String, city: Int) {
        viewModelScope.launch {
            repository.postSignup(name, email, address, password, city)
        }
    }
}