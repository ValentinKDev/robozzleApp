package com.mobilegame.robozzle.domain.model.Screen.Tuto

import android.content.Context
import androidx.lifecycle.ViewModel
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.model.data.store.TutoDataStoreViewModel
import com.mobilegame.robozzle.presentation.ui.Screen.Tuto.TutoObj

class TutoViewModel(context: Context): ViewModel() {
    private val dataStoreVM = TutoDataStoreViewModel(context)

//    var tuto: Tuto = Tuto.End
//    var tuto: Tuto = Tuto.findTutoByStep(dataStoreVM.getTutoStep()) ?: Tuto.ClickOnProfile
//    var tuto: Tuto = Tuto.ClickOnProfile
    var tuto: Tuto = Tuto.ClickOnFirstInstructionCase

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
//    fun isLevelsScreenByDiffTutoActivated(): Boolean = levelDifficulty == 1 && tuto.matchStep(Tuto.ClickOnTutoLevel)
    fun isLevelsScreenByDiffTutoActivated(): Boolean = levelDifficulty == 1 && ( tuto.step in Tuto.ClickOnTutoLevel.step until Tuto.End.step )
    fun updateLevelDifficultyTo(diff: Int) { levelDifficulty = diff }

//    fun isTuto
    fun getTutoObj(): TutoObj {
        infoLog("TutoViewModel::getTutoObj", "tuto = ${tuto.step} ${tuto.description}")
        return when {
            tuto.matchStep(Tuto.ClickOnFirstInstructionCase) -> getTutoLayoutTop()
            tuto.matchStep(Tuto.ClickOnFirstInstructionFromMenu) -> getTutoLayoutTop()
            else -> TutoObj.create()
        }
    }
    private fun getTutoLayoutTop(): TutoObj {
        return TutoObj.create(
            topPadd = 0.1F,
            startPadd = 0.1F,
            endPadding = 0.1F,
            bottomPadd = 0.75F,
        )
    }
    private fun getTutoLayoutClickInstructionMenuCase(): TutoObj {
        return TutoObj.create(
            topPadd = 0.1F,
            startPadd = 0.1F,
            endPadding = 0.1F,
            bottomPadd = 0.75F,
        )
    }
}
