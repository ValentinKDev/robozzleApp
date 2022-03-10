package com.mobilegame.robozzle.domain.model.data.store

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilegame.robozzle.data.store.DataStoreService
import com.mobilegame.robozzle.domain.res.ERROR
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ArgumentsDataStoreViewModel(context: Context): ViewModel() {
    private val service = DataStoreService.createArgumentsService(context)

    fun storeLevelNumberArg(levelId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            service.putInt(KeyProvider.LevelArg.key, levelId)
        }
    }

    fun getLevelNumberArg(): Int = runBlocking(Dispatchers.IO) {
        service.getInt(KeyProvider.LevelArg.key) ?: ERROR
    }

}