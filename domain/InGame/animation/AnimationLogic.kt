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
import com.mobilegame.robozzle.domain.InGame.animation.AnimationData
import com.mobilegame.robozzle.domain.InGame.res.*
import com.mobilegame.robozzle.domain.RobuzzleLevel.Position
import com.mobilegame.robozzle.domain.WinDetails.WinDetails
import com.mobilegame.robozzle.domain.model.Screen.InGame.GameDataViewModel
import com.mobilegame.robozzle.domain.model.data.general.RankVM
import com.mobilegame.robozzle.domain.model.level.Level
import com.mobilegame.robozzle.domain.res.TRUE
import kotlinx.coroutines.*

const val maxNumberActionToDisplay = 10
//class AnimationLogicViewModel(private var mainVM: GameDataViewModel): ViewModel() {
//class AnimationLogicViewModel(private var breadcrumb: Breadcrumb, private val level: Level): ViewModel() {
class AnimationLogicViewModel(
    private val level: Level,
//    private var data: AnimationData,
    private val VM: GameDataViewModel,
//    private val bdVM: BreadcrumbViewModel
): ViewModel() {
    private var actionAdded = 0

    lateinit var breadcrumb: Breadcrumb

    lateinit var stars: Stars
    lateinit var colorSwitches: ColorsMaps
//    var stops: MutableList<Position> = mutableListOf()
    var actionIndexEnd: Int = UNKNOWN
    private var starsRemovedMap = mutableMapOf<Int, Point>()
    private var addAction = initialPreloadActionsNumber

    private lateinit var data: AnimationData

    init {
        errorLog("init", "animation logic")
//        infoLog("playerInGame position lvl", "${level.playerInitial}")
//        infoLog("playerInGame position anim.data", "${data.playerAnimated.value.pos}")
//        infoLog("action to read", "${data.getActionToRead()}")
    }

    fun initialize(bd: Breadcrumb, animData: AnimationData) {
        data = animData
        breadcrumb = bd
//        animLogicData = AnimationLogicData(bd)
        actionIndexEnd = breadcrumb.lastActionNumber
        stars = Stars(toRemove = breadcrumb.starsRemovalMap.copy())
        colorSwitches = ColorsMaps(toRemove  = breadcrumb.colorChangeMap.copy())
    }

    fun start(bd: Breadcrumb, animData: AnimationData): Job = runBlocking(Dispatchers.IO) {
        viewModelScope.async(Dispatchers.IO) {
            initialize(bd, animData)
            infoLog("stars at", "${breadcrumb.actionsTriggerStarRemoveList}")
            infoLog("stars Map", "${breadcrumb.starsRemovalMap}")
            infoLog("win", "${breadcrumb.win}")
            Log.v(Thread.currentThread().name,"Start - actionIndexEnd ${breadcrumb.lastActionNumber}")
            while (data.actionInBounds() == true) {
                infoLog("action to read ${data.getActionToRead()}" , "->")
                UpdateMoveLogic(AnimationStream.Forward)
                data.actionInBounds()?.let {
                    //todo: potential issue on the breadCrumb calcul time to sync with the animation on longue actionList, might add a status about the calcul to get back in the animation logic
                    checkBreadcrumbRecalculation()
                    data.incrementActionToRead()
                    delay(data.getAnimationDelay())
                }
                if (data.isPlayerOnStopMark()) {
                    data.mapCaseMakeStop(this)
                }
            }
            Log.v(Thread.currentThread().name,"-----------------------------------------------------------------------------------")
            Log.v(Thread.currentThread().name,"Loop END at actionIndexEnd ${breadcrumb.lastActionNumber}")
            ProcessResult()
        }
    }

    private fun checkBreadcrumbRecalculation() {
        if (data.getActionToRead() >= data.maxAction - maxNumberActionToDisplay
//            && (data.getActionToRead()..(data.maxAction - maxNumberActionToDisplay)) containsNot breadcrumb.win
//            && (data.getActionToRead()..(data.maxAction - maxNumberActionToDisplay)) containsNot breadcrumb.lost
            && (data.getActionToRead()..data.maxAction) containsNot breadcrumb.win
            && (data.getActionToRead()..data.maxAction) containsNot breadcrumb.lost
        ) {
            errorLog("..........", "expand Breadcrumb .............................................................")
            infoLog("action to read", "${data.getActionToRead()}")
            infoLog("data max - maxNum", "${data.maxAction - maxNumberActionToDisplay}")
            infoLog("win", "${breadcrumb.win}")
            infoLog("lost", "${breadcrumb.lost}")
            expandBreadcrumb()
        }
    }

    private fun expandBreadcrumb() = runBlocking(Dispatchers.IO) {
        Log.e("breadcrumb", "Recalculate here")
        actionAdded += 5

        val instructionRows = breadcrumb.funInstructionsList
        val newBd = BreadcrumbViewModel(level, instructionRows, actionAdded).getBreadCrumb()

        data.updateExpandedBreadCrumb(newBd)
        breadcrumb = newBd.copy()

        viewModelScope.launch(Dispatchers.Main) { VM.updateBreadcrumbAndData(newBd) }

        actionIndexEnd = breadcrumb.playerStateList.size
        stars.toRemove = breadcrumb.starsRemovalMap.copy()
        stars.expand(breadcrumb.starsRemovalMap)
        colorSwitches.expand(breadcrumb.colorChangeMap)
    }

    suspend fun stepByStep(stream: AnimationStream) {
        infoLog("step by step", "action ${data.getActionToRead()}")

        when (stream) {
            AnimationStream.Forward -> {
                checkBreadcrumbRecalculation()
                UpdateMoveLogic(stream)
                data.incrementActionToRead()
                ProcessResult()
            }
            AnimationStream.Backward -> {
                if (data.getActionToRead() == 0) {
                    data.ChangePlayerAnimatedStatus( breadcrumb.playerStateList.toList().getSafe(0) )
                } else {
                    data.decrementActionToRead()
                    checkBreadcrumbRecalculation()
                    UpdateMoveLogic(stream)
                }
            }
        }
    }

    private suspend fun UpdateMoveLogic(stream: AnimationStream) {
        infoLog("update move logic", "$stream")
        when {
            //todo lambda for this when expression
            data.getActionToRead().trigerStar(stream) -> {
                when (stream) {
                    AnimationStream.Forward -> {
                        stars.FromToRemoveMapToRemovedMap(data.getActionToRead())
                    }
                    AnimationStream.Backward -> {
                        verbalLog("triger", "star")
                        stars.FromRemovedMapToToRemoveMap(data.getActionToRead())

                        infoLog("star", "toRemove"); stars.toRemove.forEach { infoLog("", "${it.key}") }
                        infoLog("star", "removed"); stars.removed.forEach { infoLog("", "${it.key}") }
                    }
                }
                UpdateStarsUI(stream)
            }
            data.getActionToRead().trigerColorChange(stream) -> {
                when (stream) {
                    AnimationStream.Forward -> { colorSwitches.FromToRemoveMapToRemovedMap(data.getActionToRead()) }
                    AnimationStream.Backward -> { colorSwitches.FromRemovedMapToToRemoveMap(data.getActionToRead()) }
                }
                UpdateMapCaseColorsUI(stream)
            }
//            data.isPlayerOnStopMark() -> data.mapCaseMakeStop(this)
        }
        if (stream == AnimationStream.Backward && data.listOfStopsPassed.contains(data.getPlayerPosition())) {
            data.listOfStopsPassed.remove(data.getPlayerPosition())
        }
        UpdatePlayerUI()
    }

    private fun UpdateStarsUI(stream: AnimationStream) {
        infoLog("UpddateStarsUI()", "Start()")
        val starPos: Position? = try {
            when (stream) {
                AnimationStream.Forward -> { stars.removed.getValue(data.getActionToRead()) }
                AnimationStream.Backward -> { stars.toRemove.getValue(data.getActionToRead()) }
                else -> { errorLog("Update Stars UI()", "error on direction parameter"); null }
            }
        } catch (e: NoSuchElementException) {
            errorLog("Update Stars UI()", "No such Element Exception Catched")
            null
        }
        starPos?.let {
            when (stream) {
                AnimationStream.Forward ->  data.DelAnimatedStarMap(it)
                AnimationStream.Backward -> data.AddAnimatedStarMap(it)
            }
        }
    }

    private fun UpdateMapCaseColorsUI(stream: AnimationStream) {
        val colorSwitch: ColorSwitch? = try {
            when (stream) {
                AnimationStream.Forward -> { colorSwitches.removed.getValue(data.getActionToRead()) }
                AnimationStream.Backward -> { colorSwitches.toRemove.getValue(data.getActionToRead()) }
            }
        } catch (e: NoSuchElementException) {
            errorLog("Update Map Case Colors UI()", "No such ElementException Catched")
            errorLog("toRemove", "${colorSwitches.toRemove}")
            errorLog("Removed", "${colorSwitches.removed}")
            errorLog("key", "${data.getActionToRead()}")
            null
        }
        colorSwitch?.let {
           data.ChangeCaseColorMap(colorSwitch, stream)
        }
    }

    private suspend fun UpdatePlayerUI() {
        data.ChangePlayerAnimatedStatus( breadcrumb.playerStateList.toList().getSafe(data.getActionToRead() + 1) )
    }

    private suspend fun ProcessResult() {
        if (breadcrumb.win Is TRUE) {
            data.setWinPopTo(true)
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
    private fun Int.trigerColorChange(stream: AnimationStream): Boolean {
        return when (stream) {
            AnimationStream.Forward -> { colorSwitches.toRemove.containsKey(data.getActionToRead()) }
            AnimationStream.Backward -> { colorSwitches.removed.containsKey(data.getActionToRead()) }
        }
    }

    private fun Int.trigerStar(stream: AnimationStream): Boolean {
        return when(stream) {
           AnimationStream.Forward -> {stars.toRemove.containsKey(data.getActionToRead())}
           AnimationStream.Backward -> {stars.removed.containsKey(data.getActionToRead())}
       }
    }

    fun triggerExpandBreadcrumb(): Boolean = runBlocking {
        data.getActionToRead() == breadcrumb.lastActionNumber - 2 && (breadcrumb.win Is UNKNOWN) && (breadcrumb.lost Is UNKNOWN)
    }
}

enum class AnimationStream {
    Forward, Backward
}