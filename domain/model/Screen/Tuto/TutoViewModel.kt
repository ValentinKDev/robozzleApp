package com.mobilegame.robozzle.domain.model.Screen.Tuto

import android.content.Context
import androidx.lifecycle.ViewModel
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.analyse.verbalLog
import com.mobilegame.robozzle.domain.model.data.store.TutoDataStoreViewModel
import com.mobilegame.robozzle.presentation.ui.Screen.Tuto.TutoObj
import io.ktor.util.date.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TutoViewModel(context: Context): ViewModel() {
    private val dataStoreVM = TutoDataStoreViewModel(context)


//    private val _tuto = MutableStateFlow( value = Tuto.findTutoByStep(dataStoreVM.getTutoStep()) ?: Tuto.ClickOnProfile )
//    private val _tuto = MutableStateFlow(Tuto.findTutoByStep(dataStoreVM.getTutoStep()) ?: Tuto.ClickOnProfile )

//    private val _tuto = MutableStateFlow<Tuto>(Tuto.ClickOnFirstInstructionCase)
    private val _tuto = MutableStateFlow<Tuto>(Tuto.ClickDragAndDropThirdCase)
//    private val _tuto = MutableStateFlow<Tuto>(Tuto.End)
    val tuto: StateFlow<Tuto> = _tuto.asStateFlow()
    fun getTuto(): Tuto = tuto.value
    fun setTutoTo(tuto: Tuto) {
        _tuto.value = tuto
        saveToDataStore()
        updateTutoLayout()
        verbalLog("TutoViewModel::setTutoTo", "now Tuto: ${getTuto().description}")
    }

    private fun saveToDataStore() {
//        dataStoreVM.saveTutoStep(tuto.step + 1)
        dataStoreVM.saveTutoStep(getTuto().step + 1)
    }

    private fun incrementTuto() {
//        tuto = Tuto.findTutoByStep(tuto.step + 1) ?: Tuto.End
        _tuto.value = Tuto.findTutoByStep(getTuto().step + 1) ?: Tuto.End
    }


    fun nextTuto() {
        saveToDataStore()
        incrementTuto()
        resetTimer()
        updateTutoLayout()

//        errorLog("TutoViewModel::nextStep", "now Tuto: ${tuto.description}")
        errorLog("TutoViewModel::nextStep", "now Tuto: ${getTuto().description}")
    }

    val dragTime = 2000
    fun dragTimeIsOver(): Boolean = timer > dragTime
    var timerStart: Long = 0
    var timer: Long = 0
    fun upDateTimer() {
        timer = getTimeMillis() - timerStart
        verbalLog("TutoViewModel::updateTimer", "timer = ${timer}")
    }
    fun resetTimer() { timer = 0 }
    fun handleDragStart() {
        if (timerStart.toInt() == 0) {
            verbalLog("TutoViewModel::handleDragStart", "timerStart = ${timer}")
            timerStart = getTimeMillis()
        }
    }
    fun handleDragStop() {
        upDateTimer()
        if (dragTimeIsOver()) {
            nextTuto()
            timer = 0
        }
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
    fun isTutoClickOnPlayButton(): Boolean = getTuto().matchStep(Tuto.ClickOnPlayFirstTime) || getTuto().matchStep(Tuto.ClickOnPlaySecondTime) || getTuto().matchStep(Tuto.ClickOnPlayThirdTime)
    fun isTutoClickOnResetButton(): Boolean = getTuto().matchStep(Tuto.ClickOnRestFirstTime) || getTuto().matchStep(Tuto.ClickOnResetSecondTime)
//    fun isTutoClickOnResetButton(): Boolean = getTuto().matchStep(Tuto.ClickOnRestFirstTime)
    fun isTutoClickOnThirdInstruction(): Boolean = getTuto().matchStep(Tuto.ClickOnThirdInstructionCase) || getTuto().matchStep(Tuto.ClickOnThirdInstructionCaseAgain)
    fun isTutoClickOnRepeatingFirstLineGray(): Boolean = getTuto().matchStep(Tuto.ClickOnRepeatingFirstLineGray)
    fun isTutoClickOnStopMark(): Boolean = getTuto().matchStep(Tuto.ClickOnMapCaseFirst)
    fun isTutoDragActionBar(): Boolean = getTuto().matchStep(Tuto.ClickAndDragActionBar)
    fun isTutoDragAndDropInstruction(): Boolean = getTuto().matchStep(Tuto.ClickDragAndDropThirdCase)
//    fun isTutoClickOnTheThirdInstructionAgain()
//    fun isTutoClickOnForwardButton(): Boolean = getTuto().matchStep(Tuto.ClickOnMovingForward1)

    private val _tutoLayout = MutableStateFlow(TutoObj.create())
    val tutoLayout: StateFlow<TutoObj> = _tutoLayout.asStateFlow()
    private fun updateTutoLayout() { _tutoLayout.value = getTutoObj() }
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
            getTuto().matchStep(Tuto.ClickOnMapCaseFirst) -> getTutoButtonLayout()
            getTuto().matchStep(Tuto.ClickOnPlayThirdTime) -> getTutoButtonLayout()
            getTuto().matchStep(Tuto.ClickAndDragActionBar) -> getTutoButtonLayout()
            getTuto().matchStep(Tuto.ClickOnThirdInstructionCaseAgain) -> getTutoLayoutTop()

            else -> TutoObj.create()
        }
    }
//    private fun getTutoMapLayout(): TutoObj {
//        return TutoObj.create(
//            topPadd = 0.1F,
//            startPadd = 0.1F,
//            endPadding = 0.1F,
//            bottomPadd = 0.75F,
//        )
//    }

    private fun getTutoLayoutTop(): TutoObj {
        return TutoObj.create(
            topPadd = 0.08F,
            startPadd = 0.1F,
            endPadding = 0.1F,
            bottomPadd = 0.78F,
        )
    }
    private fun getTutoButtonLayout(): TutoObj {
        return TutoObj.create(
            topPadd = 0.65F,
            startPadd = 0.05F,
            endPadding = 0.05F,
            bottomPadd = 0.20F,
        )
    }
}
