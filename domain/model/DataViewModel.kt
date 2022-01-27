package com.mobilegame.robozzle.domain.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
            repository.putString(NAME_KEY, value)
        }
    }

    fun getName(): String? = runBlocking {
        repository.getString(NAME_KEY)
    }

    fun savePassword(value: String) {
        viewModelScope.launch {
            repository.putString(PASSWORD_KEY, value)
        }
    }

    fun getPassword(): String? = runBlocking {
        repository.getString(PASSWORD_KEY)
    }

    fun saveId(value: Int) {
        viewModelScope.launch {
            repository.putInt(ID_KEY, value)
        }
    }

    fun getId(): Int? = runBlocking {
        repository.getInt(ID_KEY)
    }
}