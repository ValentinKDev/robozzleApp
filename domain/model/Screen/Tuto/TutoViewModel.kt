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


//        private val _tuto = MutableStateFlow<Tuto>(Tuto.End)
    private val _tuto = MutableStateFlow( value = Tuto.findTutoByStep(dataStoreVM.getTutoStep()) ?: Tuto.ClickOnProfile )
//    private val _tuto = MutableStateFlow<Tuto>(Tuto.ClickOnProfile)
//    private val _tuto = MutableStateFlow<Tuto>(Tuto.OneLastTime)
//    private val _tuto = MutableStateFlow(Tuto.findTutoByStep(dataStoreVM.getTutoStep()) ?: Tuto.ClickOnProfile )

//    private val _tuto = MutableStateFlow<Tuto>(Tuto.ClickOnFirstInstructionCase)
//    private val _tuto = MutableStateFlow<Tuto>(Tuto.ClickOnRankingIcon)
    val tuto: StateFlow<Tuto> = _tuto.asStateFlow()
    fun getTuto(): Tuto = tuto.value
    fun setTutoTo(tuto: Tuto) {
        _tuto.value = tuto
        saveToDataStore()
        updateTutoLayout()
        verbalLog("TutoViewModel::setTutoTo", "now Tuto: ${getTuto().description}")
    }

    init {
        infoLog("TutoViewModel::init", "Tuto -> ${getTuto().step} : ${getTuto().description}")
    }

    private fun saveToDataStore() {
        dataStoreVM.saveTutoStep(getTuto().step + 1)
    }

    private fun incrementTuto() {
        _tuto.value = Tuto.findTutoByStep(getTuto().step + 1) ?: Tuto.End
    }


    fun nextTuto() {
        saveToDataStore()
        incrementTuto()
        resetTimer()
        updateTutoLayout()

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


    fun isMainScreenTutoActivated(): Boolean {
        return getTuto() != Tuto.End
    }
    fun isMainScreenButtonClickEnable(): Boolean = !isMainScreenTutoActivated()

    private var levelDifficulty = -42
    fun updateLevelDifficultyTo(diff: Int) { levelDifficulty = diff }

    fun isTutoClickOnFirstInstruction(): Boolean = getTuto().matchStep(Tuto.ClickOnFirstInstructionCase)
    fun isTutoClickOnFirstInstructionFromMenu(): Boolean = getTuto().matchStep(Tuto.ClickOnFirstInstructionFromMenu)
    fun isTutoClickOnSecondInstruction(): Boolean = getTuto().matchStep(Tuto.ClickOnSecondInstructionCase)
    fun isTutoClickOnSecondInstructionFromMenu(): Boolean = getTuto().matchStep(Tuto.ClickOnSecondInstructionFromMenu)
    fun isTutoClickOnPlayButton(): Boolean = getTuto().matchStep(Tuto.ClickOnPlayFirstTime) || getTuto().matchStep(Tuto.ClickOnPlaySecondTime) || getTuto().matchStep(Tuto.ClickOnPlayThirdTime) || getTuto().matchStep(Tuto.ClickOnPlayFourthTime) || getTuto().matchStep(Tuto.LetsGo) || getTuto().matchStep(Tuto.OneLastTime)
    fun isTutoClickOnResetButton(): Boolean = getTuto().matchStep(Tuto.ClickOnRestFirstTime) || getTuto().matchStep(Tuto.ClickOnResetSecondTime) || getTuto().matchStep(Tuto.ClickOnResetThirdTime)
    fun isTutoClickOnThirdInstruction(): Boolean = getTuto().matchStep(Tuto.ClickOnThirdInstructionCase) || getTuto().matchStep(Tuto.ClickOnThirdInstructionCaseAgain)
    fun isTutoClickOnRepeatingFirstLineGray(): Boolean = getTuto().matchStep(Tuto.ClickOnRepeatingFirstLineGray)
    fun isTutoClickOnStopMark(): Boolean = getTuto().matchStep(Tuto.ClickOnMapCaseFirst)
    fun isTutoDragActionBar(): Boolean = getTuto().matchStep(Tuto.ClickAndDragActionBar)
    fun isTutoDragAndDropInstruction(): Boolean = getTuto().matchStep(Tuto.ClickDragAndDropThirdCase)
    fun isTutoClickOnFirstCaseSecondLine(): Boolean = getTuto().matchStep(Tuto.ClickOnFirstInstructionCaseSecondLine)
    fun isTutoClickTurnRighFRomMenu(): Boolean = getTuto().matchStep(Tuto.ClickOnTurnRight)
    fun isTutoCallSecondLine(): Boolean = getTuto().matchStep(Tuto.ClickOnCallSecondLine)
    fun isTutoDragInstructionToTrash(): Boolean = getTuto().matchStep(Tuto.DragInstructionToTrash)
    fun isTutoClickOnFirstInstructionSecondLineAgain(): Boolean = getTuto().matchStep(Tuto.ClickOnFirstInstructionCaseSecondLineAgain)
    fun isTutoSelectTurnLeft(): Boolean = getTuto().matchStep(Tuto.SelectTurnLeft)
    fun isTutoSelectMovingForward(): Boolean = getTuto().matchStep(Tuto.SelectMovingForward)
    fun isTutoClickOnSecondInstructionSecondLine(): Boolean = getTuto().matchStep(Tuto.ClickOnSecondInstructionCaseSecondLine)

    private val _tutoLayout = MutableStateFlow(TutoObj.create())
    val tutoLayout: StateFlow<TutoObj> = _tutoLayout.asStateFlow()
    private fun updateTutoLayout() { _tutoLayout.value = getTutoObj() }
    fun getTutoObj(): TutoObj {
    infoLog("TutoViewModel::getTutoObj", "tuto = ${getTuto().step} ${getTuto().description}")
        return when {
            getTuto().matchStep(Tuto.ClickOnFirstInstructionCase) -> getTutoLayoutTopScreen()
            getTuto().matchStep(Tuto.ClickOnFirstInstructionFromMenu) -> getTutoLayoutTopScreen()
            getTuto().matchStep(Tuto.ClickOnSecondInstructionCase) -> getTutoLayoutTopScreen()
            getTuto().matchStep(Tuto.ClickOnSecondInstructionFromMenu) -> getTutoLayoutTopScreen()
            getTuto().matchStep(Tuto.ClickOnPlayFirstTime) -> getTutoOnTopButtonLayout()
            getTuto().matchStep(Tuto.ClickOnRestFirstTime) -> getTutoOnTopButtonLayout()
            getTuto().matchStep(Tuto.ClickOnThirdInstructionCase) -> getTutoLayoutTopScreen()
            getTuto().matchStep(Tuto.ClickOnRepeatingFirstLineGray) -> getTutoLayoutTopScreen()
            getTuto().matchStep(Tuto.ClickOnPlaySecondTime) -> getTutoOnTopButtonLayout()
            getTuto().matchStep(Tuto.ClickOnResetSecondTime) -> getTutoOnTopButtonLayout()
            getTuto().matchStep(Tuto.ClickOnMapCaseFirst) -> getTutoOnTopButtonLayout()
            getTuto().matchStep(Tuto.ClickOnPlayThirdTime) -> getTutoOnTopButtonLayout()
            getTuto().matchStep(Tuto.ClickAndDragActionBar) -> getTutoOnTopButtonLayout()
            getTuto().matchStep(Tuto.ClickOnFirstInstructionCaseSecondLine) -> getTutoLayoutTopScreen()
            getTuto().matchStep(Tuto.ClickOnTurnRight) -> getTutoLayoutTopScreen()
            getTuto().matchStep(Tuto.ClickOnCallSecondLine) -> getTutoLayoutTopScreen()
            getTuto().matchStep(Tuto.ClickOnPlayFourthTime) -> getTutoLayoutBetweenLine2AndButtons()
            getTuto().matchStep(Tuto.DragInstructionToTrash) -> TutoObj.create()
            getTuto().matchStep(Tuto.ClickOnResetThirdTime) -> getTutoLayoutBetweenLine2AndButtons()
            getTuto().matchStep(Tuto.ClickOnFirstInstructionCaseSecondLineAgain) -> getTutoLayoutTopScreen()
            getTuto().matchStep(Tuto.SelectMovingForward) -> getTutoLayoutTopScreen()
            getTuto().matchStep(Tuto.SelectTurnLeft) -> getTutoLayoutTopScreen()
            getTuto().matchStep(Tuto.LetsGo) -> getTutoLayoutBetweenLine2AndButtons()
            getTuto().matchStep(Tuto.ClickOnSecondInstructionCaseSecondLine) -> getTutoLayoutBetweenLine2AndButtons()
            getTuto().matchStep(Tuto.ClickOnThirdInstructionCaseAgain) -> getTutoLayoutTopScreen()
            getTuto().matchStep(Tuto.OneLastTime) -> getTutoLayoutBetweenLine2AndButtons()

            else -> TutoObj.create()
        }
    }

    private fun getTutoLayoutBetweenLine2AndButtons(): TutoObj {
        return TutoObj.create(
            topPadd = 0.80F,
            startPadd = 0.05F,
            endPadding = 0.05F,
            bottomPadd = 0.10F,
        )
    }

    private fun getTutoLayoutTopScreen(): TutoObj {
        return TutoObj.create(
            topPadd = 0.08F,
            startPadd = 0.1F,
            endPadding = 0.1F,
            bottomPadd = 0.78F,
        )
    }
    private fun getTutoOnTopButtonLayout(): TutoObj {
        return TutoObj.create(
            topPadd = 0.65F,
            startPadd = 0.05F,
            endPadding = 0.05F,
            bottomPadd = 0.20F,
        )
    }
}
