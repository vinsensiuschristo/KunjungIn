package com.bangkit.kunjungin.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bangkit.kunjungin.data.local.pref.UserModel
import com.bangkit.kunjungin.data.repository.DestinationRepository

class MainViewModel(private val repository: DestinationRepository) : ViewModel() {

    fun getSession(): LiveData<UserModel> {
        return repository.getSession()
    }
}