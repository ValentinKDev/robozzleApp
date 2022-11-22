package com.mobilegame.robozzle.domain.model.Screen.Tuto

import android.content.Context
import androidx.lifecycle.ViewModel
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.domain.model.data.store.TutoDataStoreViewModel
import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.MainScreenLayout
import com.mobilegame.robozzle.presentation.ui.Screen.Screens

class TutoViewModel(context: Context): ViewModel() {
    private val dataStoreVM = TutoDataStoreViewModel(context)

    var tuto: Tuto? = Tuto.findTutoByStep(dataStoreVM.getTutoStep()) ?: Tuto.findTutoByStep(1)
//    var tuto: Tuto? = Tuto.findTutoByStep(1) ?: Tuto.findTutoByStep(1)

    private fun saveToDataStore() {
        tuto?.let { dataStoreVM.saveTutoStep(it.step + 1) }
    }

    private fun incrementTuto() {
        tuto?.let {
            tuto = Tuto.findTutoByStep(it.step + 1) ?: kotlin.run {
                errorLog("TutoVM:incrementTuto", "ERROR can not finding step ${it.step + 1}")
                null
            }
        }
    }

    fun nextStep() {
        saveToDataStore()
        incrementTuto()
    }

    fun isMainScreenTutoActivated(): Boolean {
        return tuto?.let { it.step == 1} ?: true
    }

    fun isMainScreenButtonClickEnable(): Boolean = !isMainScreenTutoActivated()

}
