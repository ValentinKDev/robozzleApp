package com.mobilegame.robozzle.domain.model.store

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.data.store.DataStoreService
import com.mobilegame.robozzle.domain.res.NOTOKEN
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

//class AppVersionDataStoreViewModel (
//    context: Context
//) : ViewModel() {
//    private val service: DataStoreService = DataStoreService.createAppData(context)
//
//    fun saveVersion(version: String) {
//        viewModelScope.launch {
//            service.putString(KeyProvider.Version.key, version)
//        }
//    }
//    fun getVersion(): String? = runBlocking {
//        service.getString(KeyProvider.Version.key)
//    }
//}
