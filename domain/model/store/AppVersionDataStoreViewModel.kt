package com.mobilegame.robozzle.domain.model.store

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilegame.robozzle.data.store.DataStoreService
import com.mobilegame.robozzle.domain.res.NOTOKEN
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class AppVersionDataStoreViewModel (
    private val service: DataStoreService
) : ViewModel() {
    fun saveVersion(version: String) {
        viewModelScope.launch {
            service.putString(KeyProvider.Version.key, version)
        }
    }
    fun getVersion(version: String): String? = runBlocking {
        service.getString(KeyProvider.Version.key)
    }
}
