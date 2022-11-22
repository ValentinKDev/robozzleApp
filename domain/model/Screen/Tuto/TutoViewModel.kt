package com.mobilegame.robozzle.domain.model.Screen.Tuto

import android.content.Context
import androidx.lifecycle.ViewModel
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.domain.model.data.store.TutoDataStoreViewModel
import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.MainScreenLayout
import com.mobilegame.robozzle.presentation.ui.Screen.Screens

class TutoViewModel(context: Context): ViewModel() {
    private val dataStoreVM = TutoDataStoreViewModel(context)

    var tuto: Tuto = Tuto.findTutoByStep(dataStoreVM.getTutoStep()) ?: Tuto.ClickOnProfile

    private fun saveToDataStore() {
        dataStoreVM.saveTutoStep(tuto.step + 1)
    }

    private fun incrementTuto() {
        tuto = Tuto.findTutoByStep(tuto.step + 1) ?: Tuto.End
    }

    fun nextStep() {
        saveToDataStore()
        incrementTuto()
    }

    fun isMainScreenTutoActivated(): Boolean = tuto != Tuto.End
    fun isMainScreenButtonClickEnable(): Boolean = !isMainScreenTutoActivated()

}
