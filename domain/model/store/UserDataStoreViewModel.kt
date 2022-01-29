package com.mobilegame.robozzle.domain.model.store

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilegame.robozzle.data.store.DataStoreService
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class UserDataStoreViewModel (
    private val service: DataStoreService
) : ViewModel() {

    fun saveName(value: String) {
        viewModelScope.launch {
            service.putString(KeyProvider.Name.key, value)
        }
    }

    fun getName(): String? = runBlocking {
        service.getString(KeyProvider.Name.key)
    }

    fun savePassword(value: String) {
        viewModelScope.launch {
            service.putString(KeyProvider.Password.key, value)
        }
    }

    fun getPassword(): String? = runBlocking {
        service.getString(KeyProvider.Password.key)
    }

    fun saveId(value: Int) {
        viewModelScope.launch {
            service.putInt(KeyProvider.Id.key, value)
        }
    }

    fun getId(): Int? = runBlocking {
        service.getInt(KeyProvider.Id.key)
    }
}