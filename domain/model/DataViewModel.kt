package com.mobilegame.robozzle.domain.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilegame.robozzle.domain.KeyProvider
import com.mobilegame.robozzle.domain.repository.datastore.DataStoreRepository
import com.mobilegame.robozzle.domain.res.ID_KEY
import com.mobilegame.robozzle.domain.res.NAME_KEY
import com.mobilegame.robozzle.domain.res.PASSWORD_KEY
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class DataViewModel (
    private val repository: DataStoreRepository
) : ViewModel() {

    fun saveName(value: String) {
        viewModelScope.launch {
//            repository.putString(NAME_KEY, value)
            repository.putString(KeyProvider.Name.key, value)
        }
    }

    fun getName(): String? = runBlocking {
        repository.getString(KeyProvider.Name.key)
    }

    fun savePassword(value: String) {
        viewModelScope.launch {
            repository.putString(KeyProvider.Password.key, value)
        }
    }

    fun getPassword(): String? = runBlocking {
        repository.getString(KeyProvider.Password.key)
    }

    fun saveId(value: Int) {
        viewModelScope.launch {
            repository.putInt(KeyProvider.Id.key, value)
        }
    }

    fun getId(): Int? = runBlocking {
        repository.getInt(KeyProvider.Id.key)
    }
}