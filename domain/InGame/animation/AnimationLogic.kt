package com.mobilegame.robozzle.domain.InGame

import android.content.Context
import android.graphics.Point
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilegame.robozzle.Extensions.*
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.analyse.verbalLog
import com.mobilegame.robozzle.domain.InGame.animation.AnimationData
import com.mobilegame.robozzle.domain.InGame.res.*
import com.mobilegame.robozzle.domain.InGame.res.FORWARD
import com.mobilegame.robozzle.domain.RobuzzleLevel.Position
import com.mobilegame.robozzle.domain.WinDetails.WinDetails
import com.mobilegame.robozzle.domain.model.Screen.InGame.GameDataViewModel
import com.mobilegame.robozzle.domain.model.data.general.RankVM
import com.mobilegame.robozzle.domain.res.FALSE
import com.mobilegame.robozzle.domain.res.TRUE
import com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.ScreenParts.secondPart.DisplayActionRowCase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AnimationLogicViewModel(private var mainVM: GameDataViewModel): ViewModel() {
    private var actionAdded = 5

    private var breadcrumb = mainVM.breadcrumb
    private var stars: Stars = Stars(toRemove = breadcrumb.starsRemovalMap.copy())
    private var colorSwitches = ColorsMaps(toRemove  = breadcrumb.colorChangeMap.copy())
    private var starsRemovedMap = mutableMapOf<Int, Point>()
    private var actionIndexEnd = mainVM.breadcrumb.actionsCount
//    private var actionToRead = 0
    private var addAction = initialPreloadActionsNumber

    val data: AnimationData = AnimationData(mainVM.level.map.toMutableList(), mainVM.level.playerInitial.toPlayerInGame(), breadcrumb)

    fun initialize(bd: Breadcrumb) {
        breadcrumb = bd
        stars = Stars(toRemove = breadcrumb.starsRemovalMap.copy())
        colorSwitches = ColorsMaps(toRemove  = breadcrumb.colorChangeMap.copy())
        actionIndexEnd = mainVM.breadcrumb.actionsCount
    }

    fun start(bd: Breadcrumb): Job = runBlocking {
        viewModelScope.launch(Dispatchers.IO) {
            initialize(bd)
            infoLog("stars at", "${breadcrumb.actionsTriggerStarRemoveList}")
            infoLog("stars Map", "${breadcrumb.starsRemovalMap}")
            infoLog("win", "${breadcrumb.win}")
            Log.v(Thread.currentThread().name,"Start - actionIndexEnd $actionIndexEnd")
//            while (actionToRead < actionIndexEnd) {

            while (data.actionInBounds() == true) {
                infoLog("action to read ${data.getActionToRead()}" , "->")
                UpdateMoveLogic(FORWARD)
//                if (actionToRead < actionIndexEnd) DeleteThis(Dispatchers.Main) { mainVM.SetActionTo(actionToRead) }
                data.actionInBounds()?.let {
                    HandleAnimationOnPauseLogic()

                    //todo: potential issue on the breadCrumb calcul time to sync with the animation on longue actionList, might add a status about the calcul to get back in the animation logic
//                    if (actionToRead.triggerExpancBreadCrumb()) {RecalculateBreadCrumb()}
                    if (data.triggerExpandBreadcrumb()) {RecalculateBreadCrumb()}
                    AnimationDelay()

//                    actionToRead++
                    data.incrementActionToRead()
                }
            }
            Log.v(Thread.currentThread().name,"Loop actions END at actionIndexEnd $actionIndexEnd")
            EndGame()
        }
    }


    private suspend fun HandleAnimationOnPauseLogic() {
        while ( data.getPlayerAnimationState() == PlayerAnimationState.OnPause ) {
            infoLog("Step ${data.getActionToRead()}", " ->")
            if ( data.triggerExpandBreadcrumb() ) { RecalculateBreadCrumb() }
            delay(50)
            when {
                data.isGoingForward() -> StepByStep(FORWARD)
                data.isGoingBackward() -> StepByStep(BACKWARD)
            }
            if (data.actionOutOfBounds()) break
        }
    }

    private suspend fun RecalculateBreadCrumb() {
        Log.e("breadcrumb", "Recalculate here")

        viewModelScope.launch(Dispatchers.IO) {
            actionAdded += 5
            breadcrumb = BreadcrumbViewModel(mainVM.level, mainVM.getInstructionsRows(), actionAdded).getBreadCrumb()
        }
        actionIndexEnd = breadcrumb.playerStateList.size

        //todo : use the same Expand() logic to handle colorSwitch after recalculation ?

        stars.toRemove = breadcrumb.starsRemovalMap.copy()
        colorSwitches.Expand(breadcrumb.colorChangeMap)
        stars.Expand(breadcrumb.starsRemovalMap)

//        DeleteThis(Dispatchers.Main) { mainVM.triggerRecompostionToTrue() }

        verbalLog("after ", "recalculation")
        infoLog("star.toRemove", "${stars.toRemove}")
        infoLog("star.removed", "${stars.removed}")
        stars.toRemove.forEach { infoLog("", "${it.key}") }
        infoLog("star", "removed")
        stars.removed.forEach { infoLog("", "${it.key}") }
    }

    private fun StepByStep(direction: Int) {
        UpdateMoveLogic(direction)
        if (direction == BACKWARD) data.decrementActionToRead()
        if (direction == FORWARD) data.incrementActionToRead()

//        DeleteThis(Dispatchers.Main) {
        when (direction) {
            FORWARD -> { data.setPlayerAnimationState(PlayerAnimationState.GoNext) }
            BACKWARD -> { data.setPlayerAnimationState(PlayerAnimationState.GoBack) }
            else -> { errorLog("ERROR", "AnimationLogic::StepbyStep [Wrong direction]") }
        }
    }


    private fun UpdateMoveLogic(direction: Int) {
        when {
            //todo lambda for this when expression
            data.getActionToRead().trigerStar(direction) -> {
                when (direction) {
                    FORWARD -> { stars.FromToRemoveMapToRemovedMap(data.getActionToRead()) }
                    BACKWARD -> {
                        verbalLog("triger", "star")
                        stars.FromRemovedMapToToRemoveMap(data.getActionToRead())

                        infoLog("star", "toRemove"); stars.toRemove.forEach { infoLog("", "${it.key}") }
                        infoLog("star", "removed"); stars.removed.forEach { infoLog("", "${it.key}") }
                    }
                    else -> { errorLog("Update Move Logic triger Star", "Error")}
                }
                UpdateStarsUI(direction)
            }
            data.getActionToRead().trigerColorChange(direction) -> {
                when (direction) {
                    FORWARD -> { colorSwitches.FromToRemoveMapToRemovedMap(data.getActionToRead()) }
                    BACKWARD -> { colorSwitches.FromRemovedMapToToRemoveMap(data.getActionToRead()) }
                    else -> { errorLog("Update Move Logic triger Color Change", "Error")}
                }
                UpdateMapCaseColorsUI(direction)
            }
        }
        UpdatePlayerUI()
    }

    private fun UpdateStarsUI(direction: Int) {
        infoLog("UpddateStarsUI()", "Start()")
        val starPos: Position? = try {
            when (direction) {
                FORWARD -> { stars.removed.getValue(data.getActionToRead()) }
                BACKWARD -> { stars.toRemove.getValue(data.getActionToRead()) }
                else -> { errorLog("Update Stars UI()", "error on direction parameter"); null }
            }
        } catch (e: NoSuchElementException) {
            errorLog("Update Stars UI()", "No such Element Exception Catched")
            null
        }
        starPos?.let {
            when (direction) {
                FORWARD ->  data.DelAnimatedStarMap(it)
                BACKWARD -> data.AddAnimatedStarMap(it)
                else -> errorLog("ERROR", "AnimationLogic::UpdateStarsUI")
            }
        }
    }

    private fun UpdateMapCaseColorsUI(direction: Int) {
        val colorSwitch: ColorSwitch? = try {
            when (direction) {
                FORWARD -> { colorSwitches.removed.getValue(data.getActionToRead()) }
                BACKWARD -> { colorSwitches.toRemove.getValue(data.getActionToRead()) }
                else -> { errorLog("Update Stars UI()", "error on direction parameter"); null }
            }
        } catch (e: NoSuchElementException) {
            errorLog("Update Map Case Colors UI()", "No such ElementException Catched")
            errorLog("toRemove", "${colorSwitches.toRemove}")
            errorLog("Removed", "${colorSwitches.removed}")
            errorLog("key", "${data.getActionToRead()}")
            null
        }
        colorSwitch?.let {
           data.ChangeCaseColorMap(colorSwitch, direction)
//            DeleteThis(Dispatchers.Main) { mainVM.ChangeCaseColorMap(colorSwitch, direction) }
        }
    }

    private fun UpdatePlayerUI() {
        data.ChangePlayerAnimatedStatus(breadcrumb.playerStateList[data.getActionToRead()])
//        DeleteThis(Dispatchers.Main) { mainVM.ChangePlayerAnimatedStatus(breadcrumb.playerStateList[actionToRead]) }
    }

    private suspend fun EndGame() {
        setWinTo(if (breadcrumb.win) TRUE else FALSE)
//        when {
//            breadcrumb.IsWin() -> ProcessResult(TRUE)
//            breadcrumb.IsLost() -> ProcessResult(FALSE)
//      todo: isJustNotFinished ???
//            else -> {
//                Log.e("not suposed to be finished", "--???--")
//            }
//        }
//        ProcessResult( if ( breadcrumb.win ) TRUE else FALSE)
    }

    private fun ProcessResult(result: Int) {
            setWinTo(result)
//            AddWin(
//                value = result,
//                context
//            )
//        }
        Log.e("END animation", "--${result}--")
    }

    private val _win = MutableStateFlow<Int>(UNKNOWN)
    val win: StateFlow<Int> = _win
    fun setWinTo(value: Int) {_win.value = value}

    fun AddWin( context: Context ) {
        val rankVM = RankVM(context)
        val winDetails = WinDetails(
            instructionsNumber = breadcrumb.funInstructionsList.countInstructions(),
            actionsNumber = breadcrumb.actionsCount,
            solutionFound = breadcrumb.funInstructionsList.toList()
        )
        rankVM.registerANewWin(
            levelId = mainVM.level.id,
            levelName = mainVM.level.name,
            levelDifficulty = mainVM.level.difficulty,
            winDetails = winDetails
        )
    }


    private suspend fun AnimationDelay() {
        if (mainVM.mapLayoutPressed.value) delay(50)
        else delay(200)
    }

    private fun Int.trigerColorChange(direction: Int): Boolean {
        return when (direction) {
            FORWARD -> { colorSwitches.toRemove.containsKey(data.getActionToRead()) }
            BACKWARD -> { colorSwitches.removed.containsKey(data.getActionToRead()) }
            else -> { errorLog("Triger Color Change", "Error"); false }
        }
    }

    private fun Int.trigerStar(direction: Int): Boolean {
       return when(direction) {
           FORWARD -> {stars.toRemove.containsKey(data.getActionToRead())}
           BACKWARD -> {stars.removed.containsKey(data.getActionToRead())}
           else -> { errorLog("Triger Star", "Error"); false }
       }
    }

//    private fun Int.triggerExpancBreadCrumb(): Boolean = this == actionIndexEnd - 2 && breadcrumb.NotFinished()
//    private fun IsGoingBackward(): Boolean = getPlayerAnimationState() == PlayerAnimationState.GoBack && data.getActionToRead() > 0
//    private fun IsGoingForward(): Boolean = getPlayerAnimationState() == PlayerAnimationState.GoNext && data.getActionToRead() < actionIndexEnd - 1
//    private fun IsGoingBackward(): Boolean = screenVM.animationGoBack.value == true && actionToRead > 0
//    private fun IsGoingForward(): Boolean = screenVM.animationGoNext.value == true && actionToRead < actionIndexEnd - 1

//    fun IsLost(): Boolean {return (lost >= TRUE)}
//    fun IsWin(): Boolean {return (win >= TRUE)}
//    fun IsLostAt(action: Int): Boolean {return (IsLost() && lost == action)}
//    fun IsWinAt(action: Int): Boolean {return (IsWin() && win == action)}
    /*
    diviser le timer en une multitude de timers qui loop pour intercaler un handlerEvent
     */
}
