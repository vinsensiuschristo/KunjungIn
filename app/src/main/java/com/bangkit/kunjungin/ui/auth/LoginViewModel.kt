package com.bangkit.kunjungin.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.kunjungin.data.local.pref.UserModel
import com.bangkit.kunjungin.data.remote.response.LoginResponse
import com.bangkit.kunjungin.data.repository.DestinationRepository
import com.bangkit.kunjungin.utils.Event
import kotlinx.coroutines.launch


class LoginViewModel(private val repository: DestinationRepository) : ViewModel() {
    val toast: LiveData<Event<String>> = repository.toast
    val loading: LiveData<Boolean> = repository.loading
    val loginResponse: LiveData<LoginResponse> = repository.loginResponse

    fun postLogin(email: String, password: String) {
        viewModelScope.launch {
            repository.postLogin(email, password)
        }
    }

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }

    fun login() {
        viewModelScope.launch {
            repository.login()
        }
    }
}