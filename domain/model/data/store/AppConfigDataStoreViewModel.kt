package com.mobilegame.robozzle.domain.model.data.store

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilegame.robozzle.data.store.DataStoreService
import kotlinx.coroutines.*

class AppConfigDataStoreViewModel (
    context: Context
): ViewModel() {
    val service = DataStoreService.createAppData(context)

    fun getVersion(): String? = runBlocking(Dispatchers.IO) {
        service.getString(KeyProvider.Version.key)
    }

    fun setVersion(version: String) {
        viewModelScope.launch(Dispatchers.IO) {
            service.putString(KeyProvider.Version.key, version)
        }
    }

}
