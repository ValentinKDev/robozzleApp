package com.mobilegame.robozzle.domain.model.store

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilegame.robozzle.data.store.DataStoreService
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class TokenDataStoreViewModel (
    private val service: DataStoreService
) : ViewModel() {
    fun saveToken(value: String) {
        viewModelScope.launch {
            service.putString(KeyProvider.Name.key, value)
        }
    }
    fun getToken(): String? = runBlocking {
        service.getString(KeyProvider.Name.key)
    }
}
