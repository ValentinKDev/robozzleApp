package com.mobilegame.robozzle.domain.InGame

import android.graphics.Point
import android.util.Log
import com.mobilegame.robozzle.Extensions.copy
import com.mobilegame.robozzle.Extensions.countInstruction
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.analyse.verbalLog
import com.mobilegame.robozzle.domain.InGame.res.*
import com.mobilegame.robozzle.domain.InGame.res.FORWARD
import com.mobilegame.robozzle.domain.RobuzzleLevel.Position
import com.mobilegame.robozzle.domain.WinDetails.WinDetails
import com.mobilegame.robozzle.domain.model.Screen.InGame.GameDataViewModel
import com.mobilegame.robozzle.domain.res.FALSE
import com.mobilegame.robozzle.domain.res.TRUE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext

class AnimationLogic(val breadcrumb: Breadcrumb, var gameDataVM: GameDataViewModel) {
    private var stars: Stars = Stars(toRemove = breadcrumb.starsRemovalMap.copy())
//    private val stars: Stars = Stars(toRemove = ))
    private var colorSwitches = ColorsMaps(toRemove  = breadcrumb.colorChangeMap.copy())
    private var starsRemovedMap = mutableMapOf<Int, Point>()
    private val mainScope = MainScope()
    private val maintainOnPause = false
    private var actionIndexEnd = breadcrumb.playerStateList.size

    private var actionToRead = 0

    private val _recalcultationFinished = MutableStateFlow<Boolean>(false)
    val recalculationFinised = _recalcultationFinished
    fun SetRecalculationStatus(status: Boolean) {_recalcultationFinished.value = status}
    fun IsRecalculating(): Boolean = _recalcultationFinished.value == true

    suspend fun Start() {
        infoLog("stars at", "${breadcrumb.actionsTriggerStarRemoveList}")
        infoLog("stars Map", "${breadcrumb.starsRemovalMap}")
        infoLog("win", "${breadcrumb.win}")

        Log.v(Thread.currentThread().name,"Start - actionIndexEnd $actionIndexEnd")
        while (actionToRead < actionIndexEnd) {
            infoLog("action to read $actionToRead" , "->")
            UpdateMoveLogic(FORWARD)
            if (actionToRead < actionIndexEnd) withContext(Dispatchers.Main) { gameDataVM.SetActionTo(actionToRead) }
            HandleAnimationOnPauseLogic()

            //todo: potential issue on the breadCrumb calcul time to sync with the animation on longue actionList, might add a status about the calcul to get back in the animation logic
            if (actionToRead.triggerExpancBreadCrumb()) {RecalculateBreadCrumb()}
            AnimationDelay()
            actionToRead++
        }
        Log.v(Thread.currentThread().name,"Loop actions END at actionIndexEnd $actionIndexEnd")
        EndGame()
    }


    private suspend fun HandleAnimationOnPauseLogic() {
        while (gameDataVM.animationIsOnPause.value == true) {
            infoLog("Step ${actionToRead}", " ->")
            delay(50)
            when {
                actionToRead.triggerExpancBreadCrumb() -> RecalculateBreadCrumb()
                IsGoingForward() -> StepByStep(FORWARD)
                IsGoingBackward() -> StepByStep(BACKWARD)
            }
            if (breadcrumb.IsWinAt(actionToRead) || breadcrumb.IsLostAt(actionToRead)) break
        }
    }

    private suspend fun RecalculateBreadCrumb() {
        Log.e("NewGuidelineTriggered", "Recalculate here")
        val addAction = 20
        withContext(Dispatchers.Default){
            breadcrumb.CreateNewBeadcrumb(breadcrumb.preloadActions + addAction, breadcrumb.funInstructionsList)
        }
        actionIndexEnd = breadcrumb.playerStateList.size


        //todo : use the same Expand() logic to handle colorSwitch after recalculation ?
        stars.toRemove = breadcrumb.starsRemovalMap.copy()
        colorSwitches.Expand(breadcrumb.colorChangeMap)
        stars.Expand(breadcrumb.starsRemovalMap)
        withContext(Dispatchers.Main) { gameDataVM.triggerRecompostionToTrue() }
        verbalLog("after ", "recalculation")
        infoLog("star.toRemove", "${stars.toRemove}")
        infoLog("star.removed", "${stars.removed}")
//        infoLog("colorswitch.toRemove", "${colorSwitches.toRemove}")
//        infoLog("colorswitch.toRemove", "${colorSwitches.toRemove}")
        infoLog("star", "toRemove")
        stars.toRemove.forEach { infoLog("", "${it.key}") }
        infoLog("star", "removed")
//        infoLog("colorswitch.removed", "${colorSwitches.removed}")
        stars.removed.forEach { infoLog("", "${it.key}") }
    }

    private suspend fun StepByStep(direction: Int) {
        UpdateMoveLogic(direction)
        if (direction == BACKWARD) actionToRead--
        if (direction == FORWARD) actionToRead++

        withContext(Dispatchers.Main) {
            when (direction) {
                FORWARD -> { gameDataVM.AnimationGoingNextChangeStatus() }
                BACKWARD -> { gameDataVM.AnimationGoingBackChangeStatus() }
                else -> { errorLog("ERROR", "AnimationLogic::StepbyStep [Wrong direction]") }
            }
            gameDataVM.SetActionTo(actionToRead)
        }
    }


    private suspend fun UpdateMoveLogic(direction: Int) {
        when {
            //todo lambda for this when expression
            actionToRead.trigerStar(direction) -> {
                when (direction) {
                    FORWARD -> { stars.FromToRemoveMapToRemovedMap(actionToRead) }
                    BACKWARD -> {
                        verbalLog("triger", "star")
                        stars.FromRemovedMapToToRemoveMap(actionToRead)

                        infoLog("star", "toRemove"); stars.toRemove.forEach { infoLog("", "${it.key}") }
                        infoLog("star", "removed"); stars.removed.forEach { infoLog("", "${it.key}") }
//                        infoLog("star.toRemove", "${stars.toRemove}")
//                        infoLog("star.removed", "${stars.removed}")
                    }
                    else -> { errorLog("Update Move Logic triger Star", "Error")}
                }
                UpdateStarsUI(direction)
            }
            actionToRead.trigerColorChange(direction) -> {
                when (direction) {
                    FORWARD -> { colorSwitches.FromToRemoveMapToRemovedMap(actionToRead) }
                    BACKWARD -> { colorSwitches.FromRemovedMapToToRemoveMap(actionToRead) }
                    else -> { errorLog("Update Move Logic triger Color Change", "Error")}
                }
                UpdateMapCaseColorsUI(direction)
            }
        }
        UpdatePlayerUI()
    }

    private suspend fun UpdateStarsUI(direction: Int) {
        infoLog("UpddateStarsUI()", "Start()")
        val starPos: Position? = try {
            when (direction) {
                FORWARD -> { stars.removed.getValue(actionToRead) }
                BACKWARD -> { stars.toRemove.getValue(actionToRead) }
                else -> { errorLog("Update Stars UI()", "error on direction parameter"); null }
            }
        } catch (e: NoSuchElementException) {
            errorLog("Update Stars UI()", "No such Element Exception Catched")
            null
        }
        starPos?.let { withContext(Dispatchers.Main) {
                when (direction) {
                    FORWARD ->  gameDataVM.DelAnimatedStarMap(starPos)
                    BACKWARD -> gameDataVM.AddAnimatedStarMap(starPos)
                }
            }
        }
    }

    private suspend fun UpdateMapCaseColorsUI(direction: Int) {
        val colorSwitch: ColorSwitch? = try {
            when (direction) {
                FORWARD -> { colorSwitches.removed.getValue(actionToRead) }
                BACKWARD -> { colorSwitches.toRemove.getValue(actionToRead) }
                else -> { errorLog("Update Stars UI()", "error on direction parameter"); null }
            }
        } catch (e: NoSuchElementException) {
            errorLog("Update Map Case Colors UI()", "No such ElementException Catched")
            errorLog("toRemove", "${colorSwitches.toRemove}")
            errorLog("Removed", "${colorSwitches.removed}")
            errorLog("key", "${actionToRead}")
            null
        }
        colorSwitch?.let { withContext(Dispatchers.Main) { gameDataVM.ChangeCaseColorMap(colorSwitch, direction) }}
    }

    private suspend fun UpdatePlayerUI() {
        withContext(Dispatchers.Main) { gameDataVM.ChangePlayerAnimatedStatus(breadcrumb.playerStateList[actionToRead]) }
    }

    private suspend fun EndGame() {
        when {
            breadcrumb.IsWin() -> ProcessResult(TRUE)
            breadcrumb.IsLost() -> ProcessResult(FALSE)
//      todo: isJustNotFinished ???
            else -> {
                Log.e("not suposed to be finished", "--???--")
            }
        }
    }

    private suspend fun ProcessResult(result: Int) {
        withContext(Dispatchers.Main) {
            gameDataVM.SetWinTo(
                value = result,
                winDetails = WinDetails(
                    instructionsNumber = breadcrumb.funInstructionsList.countInstruction(),
                    actionsNumber = breadcrumb.actionsCount,
                    solutionFound = breadcrumb.funInstructionsList.toList()
                )
            )
        }
        Log.e("END animation", "--${result}--")
    }


    private suspend fun AnimationDelay() {
        if (gameDataVM.mapLayoutPressed.value) delay(50)
        else delay(200)
    }

//    private fun Int.trigerColorChange(): Boolean = colorSwitches.toRemoveMap.containsKey(actionToRead)
    private fun Int.trigerColorChange(direction: Int): Boolean {
        return when (direction) {
            FORWARD -> { colorSwitches.toRemove.containsKey(actionToRead) }
            BACKWARD -> { colorSwitches.removed.containsKey(actionToRead) }
            else -> { errorLog("Triger Color Change", "Error"); false }
        }
    }

    private fun Int.trigerStar(direction: Int): Boolean {
       return when(direction) {
           FORWARD -> {stars.toRemove.containsKey(actionToRead)}
           BACKWARD -> {stars.removed.containsKey(actionToRead)}
           else -> { errorLog("Triger Star", "Error"); false }
       }
    }

    private fun Int.triggerExpancBreadCrumb(): Boolean = this == actionIndexEnd - 2 && breadcrumb.NotFinished()
    private fun IsGoingBackward(): Boolean = gameDataVM.animationGoBack.value == true && actionToRead > 0
    private fun IsGoingForward(): Boolean = gameDataVM.animationGoNext.value == true && actionToRead < actionIndexEnd - 1
    /*
    diviser le timer en une multitude de timers qui loop pour intercaler un handlerEvent
     */
}
