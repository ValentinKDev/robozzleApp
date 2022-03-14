package com.mobilegame.robozzle.domain.InGame

import android.content.Context
import android.graphics.Point
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilegame.robozzle.utils.Extensions.*
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.analyse.verbalLog
import com.mobilegame.robozzle.data.configuration.inGame.layouts.maxNumberActionToDisplay
import com.mobilegame.robozzle.domain.InGame.animation.AnimationData
import com.mobilegame.robozzle.domain.InGame.animation.AnimationLogicData
import com.mobilegame.robozzle.domain.InGame.res.*
import com.mobilegame.robozzle.domain.InGame.res.FORWARD
import com.mobilegame.robozzle.domain.RobuzzleLevel.Position
import com.mobilegame.robozzle.domain.WinDetails.WinDetails
import com.mobilegame.robozzle.domain.model.Screen.InGame.GameDataViewModel
import com.mobilegame.robozzle.domain.model.data.general.RankVM
import com.mobilegame.robozzle.domain.model.level.Level
import com.mobilegame.robozzle.domain.res.FALSE
import com.mobilegame.robozzle.domain.res.TRUE
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

//class AnimationLogicViewModel(private var mainVM: GameDataViewModel): ViewModel() {
//class AnimationLogicViewModel(private var breadcrumb: Breadcrumb, private val level: Level): ViewModel() {
class AnimationLogicViewModel(
    private val level: Level,
    private var data: AnimationData,
    private val VM: GameDataViewModel,
//    private val bdVM: BreadcrumbViewModel
): ViewModel() {
    private var actionAdded = 0

    lateinit var breadcrumb: Breadcrumb

    lateinit var stars: Stars
    lateinit var colorSwitches: ColorsMaps
    var actionIndexEnd: Int = UNKNOWN
    private var starsRemovedMap = mutableMapOf<Int, Point>()
    private var addAction = initialPreloadActionsNumber

    init {
        errorLog("init", "animation logic")
        infoLog("playerInGame position lvl", "${level.playerInitial}")
        infoLog("playerInGame position anim.data", "${data.playerAnimated.value.pos}")
        infoLog("action to read", "${data.getActionToRead()}")
    }

    fun initialize(bd: Breadcrumb) {
        breadcrumb = bd
//        animLogicData = AnimationLogicData(bd)
        actionIndexEnd = breadcrumb.lastActionNumber
        stars = Stars(toRemove = breadcrumb.starsRemovalMap.copy())
        colorSwitches = ColorsMaps(toRemove  = breadcrumb.colorChangeMap.copy())
    }

    fun start(bd: Breadcrumb): Job = runBlocking(Dispatchers.IO) {
        viewModelScope.launch(Dispatchers.IO) {
            initialize(bd)
            infoLog("stars at", "${breadcrumb.actionsTriggerStarRemoveList}")
            infoLog("stars Map", "${breadcrumb.starsRemovalMap}")
            infoLog("win", "${breadcrumb.win}")
            Log.v(Thread.currentThread().name,"Start - actionIndexEnd ${breadcrumb.lastActionNumber}")
            while (data.actionInBounds() == true) {
                infoLog("action to read ${data.getActionToRead()}" , "->")
                UpdateMoveLogic(FORWARD)
                data.actionInBounds()?.let {
                    HandleAnimationOnPauseLogic()

                    //todo: potential issue on the breadCrumb calcul time to sync with the animation on longue actionList, might add a status about the calcul to get back in the animation logic
                    checkBreadcrumbRecalculation()
//                    if (triggerExpandBreadcrumb()) {expandBreadcrumb()}
                    data.incrementActionToRead()
                    AnimationDelay()
                }
            }
            Log.v(Thread.currentThread().name,"-----------------------------------------------------------------------------------")
            Log.v(Thread.currentThread().name,"Loop END at actionIndexEnd ${breadcrumb.lastActionNumber}")
            ProcessResult()
        }
    }


    private suspend fun HandleAnimationOnPauseLogic() { while ( data.getPlayerAnimationState() == PlayerAnimationState.OnPause ) {
            infoLog("Step ${data.getActionToRead()}", " ->")
//            if ( triggerExpandBreadcrumb() ) { expandBreadcrumb() }
        checkBreadcrumbRecalculation()
            delay(50)
            when {
                data.isGoingForward() -> StepByStep(FORWARD)
                data.isGoingBackward() -> StepByStep(BACKWARD)
            }
            if (data.actionOutOfBounds()) break
        }
    }

    private fun checkBreadcrumbRecalculation() {
        if (data.getActionToRead() >= data.maxAction - maxNumberActionToDisplay) {
            expandBreadcrumb()
        }
    }

    private fun expandBreadcrumb() = runBlocking(Dispatchers.IO) {
        Log.e("breadcrumb", "Recalculate here")
        actionAdded += 5

        val instructionRows = breadcrumb.funInstructionsList
        val newBd = BreadcrumbViewModel(level, instructionRows, actionAdded).getBreadCrumb()

        data.updateBreadCrumb(newBd)
        breadcrumb = newBd.copy()

        viewModelScope.launch(Dispatchers.Main) { VM.updateBreadcrumbAndData(newBd) }

        actionIndexEnd = breadcrumb.playerStateList.size
        stars.toRemove = breadcrumb.starsRemovalMap.copy()
        stars.expand(breadcrumb.starsRemovalMap)
        colorSwitches.expand(breadcrumb.colorChangeMap)
        errorLog("action list size", "${breadcrumb.actionsList.size}")
        errorLog("action lenght ", "${breadcrumb.actions.instructions.length}")
        errorLog("action list size", "${breadcrumb.lastActionNumber}")
    }

    private suspend fun StepByStep(direction: Int) {
        UpdateMoveLogic(direction)
        if (direction == BACKWARD) data.decrementActionToRead()
        if (direction == FORWARD) data.incrementActionToRead()

        when (direction) {
            FORWARD -> { data.setPlayerAnimationState(PlayerAnimationState.GoNext) }
            BACKWARD -> { data.setPlayerAnimationState(PlayerAnimationState.GoBack) }
            else -> { errorLog("ERROR", "AnimationLogic::StepbyStep [Wrong direction]") }
        }
    }

    private suspend fun UpdateMoveLogic(direction: Int) {
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
        }
    }

    private suspend fun UpdatePlayerUI() {
        data.ChangePlayerAnimatedStatus(breadcrumb.playerStateList[data.getActionToRead() + 1])
    }

    private suspend fun ProcessResult() {
        if (breadcrumb.win Is TRUE) {
            data.setWinPopTo(true)
//            setWinTo(TRUE)
        }
        Log.e("END animation win", "--${breadcrumb.win}--")
        Log.e("END animation lost", "--${breadcrumb.lost}--")
    }

    fun AddWin( context: Context ) {
        val rankVM = RankVM(context)
        val winDetails = WinDetails(
            instructionsNumber = breadcrumb.funInstructionsList.countInstructions(),
            actionsNumber = breadcrumb.lastActionNumber,
            solutionFound = breadcrumb.funInstructionsList.toList()
        )
        rankVM.registerANewWin(
            levelId = level.id,
            levelName = level.name,
            levelDifficulty = level.difficulty,
            winDetails = winDetails
        )
    }

    private val _animationDelay = MutableStateFlow<Long>(200)
    val animationDelay: StateFlow<Long> = _animationDelay.asStateFlow()


    private suspend fun AnimationDelay() {
//        if (mainVM.mapLayoutPressed.value) delay(50)
//        else
            delay(animationDelay.value)
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

    fun triggerExpandBreadcrumb(): Boolean = runBlocking {
        data.getActionToRead() == breadcrumb.lastActionNumber - 2 && (breadcrumb.win Is UNKNOWN) && (breadcrumb.lost Is UNKNOWN)
    }
}
