package com.mobilegame.robozzle.domain.model.Screen.InGame

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.mobilegame.robozzle.analyse.*
import com.mobilegame.robozzle.utils.Extensions.replaceAt
import com.mobilegame.robozzle.domain.InGame.*
import com.mobilegame.robozzle.domain.InGame.animation.AnimationData
import com.mobilegame.robozzle.domain.InGame.res.BACKWARD
import com.mobilegame.robozzle.domain.InGame.res.FORWARD
import com.mobilegame.robozzle.domain.InGame.res.UNKNOWN
import com.mobilegame.robozzle.domain.RobuzzleLevel.FunctionInstruction
import com.mobilegame.robozzle.domain.RobuzzleLevel.FunctionInstructions
import com.mobilegame.robozzle.domain.RobuzzleLevel.Position
import com.mobilegame.robozzle.domain.model.Screen.mainScreen.PopupViewModel
import com.mobilegame.robozzle.domain.model.data.general.LevelVM
import com.mobilegame.robozzle.domain.model.gesture.dragAndDrop.DragAndDropState
import com.mobilegame.robozzle.domain.model.level.Level
import com.mobilegame.robozzle.presentation.ui.utils.MapCleaner
import com.mobilegame.robozzle.utils.Extensions.clone
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GameDataViewModel(application: Application): AndroidViewModel(application) {
    val cleaner = MapCleaner()
    var level: Level = LevelVM(getApplication()).getLevelArgument()
//    var level: Level = myleveltest

    val data = InGameData(level, getApplication() )
    private val bdVM = BreadcrumbViewModel(level, level.funInstructionsList)
//    private val bdVM = BreadcrumbViewModel(level, )
    var breadcrumb = bdVM.getBreadCrumb()
    var animData = AnimationData(level, breadcrumb)
    var animationLogicVM = AnimationLogicViewModel(level,this)

    val popup = PopupViewModel()
    val dragAndDrop = DragAndDropState()
    private var animationJob: Job? = null

    var selectedCase = Position.Zero
    fun setSelectedFunctionCase(row: Int, column: Int) {
        selectedCase = Position(row, column)
    }

    val instructionsRows: MutableList<FunctionInstructions> = level.funInstructionsList.toMutableList()
    fun replaceInstruction(pos: Position, case: FunctionInstruction) = runBlocking() {
        infoLog("replace", "$case $pos ")
        val function: FunctionInstructions = instructionsRows[pos.line]
        function.colors =
            function.colors.replaceAt(pos.column, case.color)
        function.instructions =
            function.instructions.replaceAt(pos.column, case.instruction)
        instructionsRows[pos.line] = function
        updateBreadcrumbInstructions()
        _triggerRecompositionInstructionRows.value = triggerRecompositionInstructionsRows.value?.plus(1)
    }

    private fun updateBreadcrumbInstructions() {
        val newBd = BreadcrumbViewModel(level, instructionsRows).getBreadCrumb()
        breadcrumb = newBd
        animData = AnimationData(level, newBd)
    }

    fun updateBreadcrumbAndData(bd: Breadcrumb) {
        verbalLog("GameDataViewModel", ":updateBreadcrumbAndData")
        breadcrumb = bd
        animData.updateBreadCrumb(breadcrumb)
    }

    fun startPlayerAnimation() {
        verbalLog("startPlayerAnimation", "job is active ${animationJob?.isActive}")
        verbalLog("startPlayerAnimation", "job is canceled ${animationJob?.isCancelled}")
        animationJob = animationLogicVM.start(breadcrumb, animData)
    }

    private val _triggerRecompositionInstructionRows = MutableLiveData<Int>(0)
    val triggerRecompositionInstructionsRows: MutableLiveData<Int> = _triggerRecompositionInstructionRows

    fun mapLayoutIsPressed() = runBlocking(Dispatchers.Default) {
        animData.setAnimationDelayShort()
    }
    fun mapLayoutIsReleased() = runBlocking(Dispatchers.Default) {
        animData.setAnimationDelayLong()
    }

    //todo : try to use State instead of LiveData, it might ensure this display is triggered when you click
    private val _displayInstructionsMenu = MutableStateFlow(false)
    val displayInstructionsMenu: StateFlow<Boolean> = _displayInstructionsMenu.asStateFlow()
    fun ChangeInstructionMenuState() {
        Log.v("DisplayMenu", "ChangeState ${_displayInstructionsMenu.value} to ${!_displayInstructionsMenu.value!!}")
        _displayInstructionsMenu.value =! _displayInstructionsMenu.value!!
    }

    fun clickResetButtonHandler() = runBlocking(Dispatchers.Default) {
        logClick?.let { verbalLog("click reset vm handler", "start ${animData.getPlayerAnimationState().key}") }
        animData.setPlayerAnimationState(PlayerAnimationState.NotStarted)
        animationJob?.cancel()
        animData.reset()
        logClick?.let {
            errorLog("click reset vm handler", "end ${animData.getPlayerAnimationState().key}")
            infoLog("animData", "${animData.getActionToRead()}")
        }
    }

    fun clickPlayPauseButtonHandler() = runBlocking(Dispatchers.Default) {
        logClick?.let { verbalLog("click play pause vm handler", "start ${animData.getPlayerAnimationState().key}") }
        when (animData.getPlayerAnimationState()) {
            PlayerAnimationState.IsPlaying -> {
                animationJob?.cancel()
                animData.setPlayerAnimationState(PlayerAnimationState.OnPause)
            }
            PlayerAnimationState.OnPause -> {
                animData.setPlayerAnimationState(PlayerAnimationState.IsPlaying)
                startPlayerAnimation()
            }
            PlayerAnimationState.NotStarted -> {
                animData.setPlayerAnimationState(PlayerAnimationState.IsPlaying)
                startPlayerAnimation()
            }
            else -> { logClick?.let { errorLog("ERROR", "GameButton playerAnimationState is neither IsPlaying or OnPause") } }
        }
        logClick?.let { errorLog("click play pause vm handler", "end ${animData.getPlayerAnimationState().key}") }
    }

    private val _tempAction = MutableStateFlow<Int>(UNKNOWN)
    val tempAction: StateFlow<Int> = _tempAction.asStateFlow()
    fun setTempAction(value: Int) { _tempAction.value = value }

    fun clickNextButtonHandler() = runBlocking(Dispatchers.Default) {
        logClick?.let { verbalLog("click next vm handler", "start ${animData.getPlayerAnimationState().key}") }
        if (animData.isOnPause()) { animationLogicVM.stepByStep(FORWARD) }
        else { infoLog("next", "else anim is not on pause ${animData.playerAnimationState.value.key}") }
        logClick?.let { errorLog("click next vm handler", "end ${animData.getPlayerAnimationState().key}") }
    }
    fun clickBackButtonHandler() = runBlocking(Dispatchers.Default) {
        logClick?.let { verbalLog("click back vm handler", "start ${animData.getPlayerAnimationState().key}") }
        animationLogicVM.stepByStep(BACKWARD)
//        animData.setPlayerAnimationState(PlayerAnimationState.GoNext)
        logClick?.let { errorLog("click back vm handler", "end ${animData.getPlayerAnimationState().key}") }
    }

    init { logInit?.let { errorLog("init", "GameDataViewModel") } }
}
