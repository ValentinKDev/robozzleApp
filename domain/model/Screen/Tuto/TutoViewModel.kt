package com.mobilegame.robozzle.domain.model.Screen.Tuto

import android.content.Context
import androidx.lifecycle.ViewModel
import com.mobilegame.robozzle.domain.model.data.store.TutoDataStoreViewModel

class TutoViewModel(context: Context): ViewModel() {
    private val dataStoreVM = TutoDataStoreViewModel(context)

    var tuto: Tuto = Tuto.findTutoByStep(dataStoreVM.getTutoStep()) ?: Tuto.ClickOnProfile
//    var tuto: Tuto = Tuto.ClickOnProfile

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

    private var levelDifficulty = -42
    fun isLevelsScreenByDiffTutoActivated(): Boolean = levelDifficulty == 1 && tuto.matchStep(Tuto.ClickOnTutoLevel)
    fun updateLevelDifficultyTo(diff: Int) { levelDifficulty = diff }

}
