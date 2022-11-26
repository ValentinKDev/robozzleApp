package com.mobilegame.robozzle.domain.model.Screen.Tuto

import android.content.Context
import androidx.lifecycle.ViewModel
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.model.data.store.TutoDataStoreViewModel
import com.mobilegame.robozzle.presentation.ui.Screen.Tuto.TutoObj
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TutoViewModel(context: Context): ViewModel() {
    private val dataStoreVM = TutoDataStoreViewModel(context)


//    private val _tuto = MutableStateFlow( value = Tuto.findTutoByStep(dataStoreVM.getTutoStep()) ?: Tuto.ClickOnProfile )
//    private val _tuto = MutableStateFlow(Tuto.findTutoByStep(dataStoreVM.getTutoStep()) ?: Tuto.ClickOnProfile )
//    private val _tuto = MutableStateFlow<Tuto>(Tuto.ClickOnFirstInstructionCase)
    private val _tuto = MutableStateFlow<Tuto>(Tuto.ClickOnThirdInstructionCase)
//    private val _tuto = MutableStateFlow<Tuto>(Tuto.End)
    val tuto: StateFlow<Tuto> = _tuto.asStateFlow()
    fun getTuto(): Tuto = tuto.value

    private fun saveToDataStore() {
//        dataStoreVM.saveTutoStep(tuto.step + 1)
        dataStoreVM.saveTutoStep(getTuto().step + 1)
    }

    private fun incrementTuto() {
//        tuto = Tuto.findTutoByStep(tuto.step + 1) ?: Tuto.End
        _tuto.value = Tuto.findTutoByStep(getTuto().step + 1) ?: Tuto.End
    }

    fun nextStep() {
        saveToDataStore()
        incrementTuto()
//        errorLog("TutoViewModel::nextStep", "now Tuto: ${tuto.description}")
        errorLog("TutoViewModel::nextStep", "now Tuto: ${getTuto().description}")
    }

    fun isMainScreenTutoActivated(): Boolean = getTuto() != Tuto.End
    fun isMainScreenButtonClickEnable(): Boolean = !isMainScreenTutoActivated()

    private var levelDifficulty = -42
    fun isLevelsScreenByDiffTutoActivated(): Boolean = levelDifficulty == 1 && ( getTuto().step in Tuto.ClickOnTutoLevel.step until Tuto.End.step )
    fun updateLevelDifficultyTo(diff: Int) { levelDifficulty = diff }

    fun isTutoClickOnFirstInstruction(): Boolean = getTuto().matchStep(Tuto.ClickOnFirstInstructionCase)
    fun isTutoClickOnFirstInstructionFromMenu(): Boolean = getTuto().matchStep(Tuto.ClickOnFirstInstructionFromMenu)
    fun isTutoClickOnSecondInstruction(): Boolean = getTuto().matchStep(Tuto.ClickOnSecondInstructionCase)
    fun isTutoClickOnSecondInstructionFromMenu(): Boolean = getTuto().matchStep(Tuto.ClickOnSecondInstructionFromMenu)
    fun isTutoClickOnPlayButton(): Boolean = getTuto().matchStep(Tuto.ClickOnPlayFirstTime) || getTuto().matchStep(Tuto.ClickOnPlaySecondTime)
    fun isTutoClickOnResetButton(): Boolean = getTuto().matchStep(Tuto.ClickOnRestFirstTime) || getTuto().matchStep(Tuto.ClickOnResetSecondTime)
//    fun isTutoClickOnResetButton(): Boolean = getTuto().matchStep(Tuto.ClickOnRestFirstTime)
    fun isTutoClickOnThirdInstruction(): Boolean = getTuto().matchStep(Tuto.ClickOnThirdInstructionCase)
    fun isTutoClickOnRepeatingFirstLineGray(): Boolean = getTuto().matchStep(Tuto.ClickOnRepeatingFirstLineGray)
//    fun isTutoClickOnForwardButton(): Boolean = getTuto().matchStep(Tuto.ClickOnMovingForward1)

    fun getTutoObj(): TutoObj {
    infoLog("TutoViewModel::getTutoObj", "tuto = ${getTuto().step} ${getTuto().description}")
        return when {
            getTuto().matchStep(Tuto.ClickOnFirstInstructionCase) -> getTutoLayoutTop()
            getTuto().matchStep(Tuto.ClickOnFirstInstructionFromMenu) -> getTutoLayoutTop()
            getTuto().matchStep(Tuto.ClickOnSecondInstructionCase) -> getTutoLayoutTop()
            getTuto().matchStep(Tuto.ClickOnSecondInstructionFromMenu) -> getTutoLayoutTop()
            getTuto().matchStep(Tuto.ClickOnPlayFirstTime) -> getTutoButtonLayout()
            getTuto().matchStep(Tuto.ClickOnRestFirstTime) -> getTutoButtonLayout()
            getTuto().matchStep(Tuto.ClickOnThirdInstructionCase) -> getTutoLayoutTop()
            getTuto().matchStep(Tuto.ClickOnRepeatingFirstLineGray) -> getTutoLayoutTop()
//            getTuto().matchStep(Tuto.ClickOnMovingForward1) -> getTutoButtonLayout()
            getTuto().matchStep(Tuto.ClickOnPlaySecondTime) -> getTutoButtonLayout()
            getTuto().matchStep(Tuto.ClickOnResetSecondTime) -> getTutoButtonLayout()

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
    private fun getTutoButtonLayout(): TutoObj {
        return TutoObj.create(
            topPadd = 0.60F,
            startPadd = 0.05F,
            endPadding = 0.05F,
            bottomPadd = 0.20F,
        )
    }
}
