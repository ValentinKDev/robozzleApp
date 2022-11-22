package com.mobilegame.robozzle.domain.model.data.store

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilegame.robozzle.analyse.verbalLog
import com.mobilegame.robozzle.data.store.DataStoreService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class TutoDataStoreViewModel(context: Context): ViewModel() {
    val service = DataStoreService.createTutoStateService(context)

    fun saveTutoStep(step: Int) {
        verbalLog("TutoDataStoreVM:saveTutoStep","save tuto step $step")
        viewModelScope.launch(Dispatchers.IO) {
            service.putInt(KeyProvider.TutoStep.key, step)
        }
    }

    fun getTutoStep(): Int = runBlocking(Dispatchers.IO) {
        verbalLog("TutoDataStoreVM:getTutoStep","get tuto step ${service.getInt(KeyProvider.TutoStep.key)}")
        service.getInt(KeyProvider.TutoStep.key) ?: 0
    }
}