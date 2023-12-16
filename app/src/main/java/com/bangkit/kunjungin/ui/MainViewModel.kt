package com.bangkit.kunjungin.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.kunjungin.data.local.pref.UserModel
import com.bangkit.kunjungin.data.repository.DestinationRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: DestinationRepository) : ViewModel() {

    fun getSession(): LiveData<UserModel> {
        return repository.getSession()
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}