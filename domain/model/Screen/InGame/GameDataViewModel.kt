package com.mobilegame.robozzle.domain.model.Screen.InGame

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.mobilegame.robozzle.analyse.*
import com.mobilegame.robozzle.utils.Extensions.replaceAt
import com.mobilegame.robozzle.domain.InGame.*
import com.mobilegame.robozzle.domain.InGame.animation.AnimationData
import com.mobilegame.robozzle.domain.RobuzzleLevel.FunctionInstruction
import com.mobilegame.robozzle.domain.RobuzzleLevel.FunctionInstructions
import com.mobilegame.robozzle.domain.RobuzzleLevel.Position
import com.mobilegame.robozzle.domain.model.Screen.Tuto.Tuto
import com.mobilegame.robozzle.domain.model.Screen.Tuto.TutoViewModel
import com.mobilegame.robozzle.domain.model.Screen.Tuto.matchStep
import com.mobilegame.robozzle.domain.model.Screen.mainScreen.PopupViewModel
import com.mobilegame.robozzle.domain.model.data.general.LevelVM
import com.mobilegame.robozzle.domain.model.gesture.dragAndDropCase.DragAndDropCaseState
import com.mobilegame.robozzle.domain.model.gesture.dragAndDropRow.DragAndDropRowState
import com.mobilegame.robozzle.domain.model.level.Level
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GameDataViewModel(application: Application): AndroidViewModel(application) {
    private val levelVM = LevelVM(getApplication())
    var level: Level = levelVM.getLevelArgument()

    val data = InGameData(level, getApplication() )

    private val bdVM = BreadcrumbViewModel(level, level.funInstructionsList)
    var breadcrumb = bdVM.getBreadCrumb()
    var animData = AnimationData(level, breadcrumb)
    var animationLogicVM = AnimationLogicViewModel(level,this)

    val popup = PopupViewModel()
    val dragAndDropRow = DragAndDropRowState()
    val dragAndDropCase = DragAndDropCaseState(data.layout.trash)
    private var animationJob: Job? = null

    val tutoVM = TutoViewModel(getApplication())
    fun isTutoLevel(): Boolean = level.id == 0

    var selectedCase = Position.Zero
    fun setSelectedFunctionCase(row: Int, column: Int) {
        verbalLog("GameDataVM:setSelectedFunctionCase", "row $row column $column")
        selectedCase = Position(row, column)
    }

    val instructionsRows: MutableList<FunctionInstructions> = level.funInstructionsList.toMutableList()
    fun upDateInstructionRows(line: Int ,newFunction: FunctionInstructions) {
        instructionsRows[line] = newFunction
    }

    fun switchInstruction(pos1: Position, instrucion1: FunctionInstruction, pos2: Position, instrucion2: FunctionInstruction) {
        replaceInstruction(pos1, instrucion2)
        replaceInstruction(pos2, instrucion1)
    }
    fun replaceInstruction(pos: Position, case: FunctionInstruction) = runBlocking() {
        infoLog("GameDataVM::replaceInstruction", "replacing $case $pos ")

        val function: FunctionInstructions = instructionsRows[pos.line]
        function.colors =
            function.colors.replaceAt(pos.column, case.color)
        if (case.instruction != 'n') {
            function.instructions =
                function.instructions.replaceAt(pos.column, case.instruction)
        }
        upDateInstructionRows(line = pos.line, newFunction = function)
        updateBreadcrumbInstructions()
        updateAnimData()

    }

    private fun updateBreadcrumbInstructions() {
        val newBd = BreadcrumbViewModel(level, instructionsRows).getBreadCrumb()
        breadcrumb = newBd
        viewModelScope.launch() { animData.updateBd(newBd) }
    }

    private fun updateAnimData() { animData.updateExpandedBreadCrumb(breadcrumb) }
    fun updateBreadcrumbAndData(bd: Breadcrumb) {
        verbalLog("GameDataVM::updateBreadcumbAndData", "start")
        breadcrumb = bd
        updateAnimData()
    }

    fun startPlayerAnimation() {
        verbalLog("GameDataVM::startPlayerAnimation", "job is active ${animationJob?.isActive}")
        verbalLog("GameDataVM::startPlayerAnimation", "job is canceled ${animationJob?.isCancelled}")
        animationJob = animationLogicVM.start(breadcrumb, animData)
    }

    fun mapLayoutIsPressed() = runBlocking(Dispatchers.Default) {
        animData.setAnimationDelayShort()
    }
    fun mapLayoutIsReleased() = runBlocking(Dispatchers.Default) {
        animData.setAnimationDelayLong()
    }

    private val _displayInstructionsMenu = MutableStateFlow(false)
    val displayInstructionsMenu: StateFlow<Boolean> = _displayInstructionsMenu.asStateFlow()
    fun ChangeInstructionMenuState() {
        Log.v("GameDataVM::ChangeInstructionMenuState", "ChangeState ${_displayInstructionsMenu.value} to ${!_displayInstructionsMenu.value!!}")
        _displayInstructionsMenu.value =! _displayInstructionsMenu.value!!
    }

    fun backNavHandler() {
        clickResetButtonHandler()
//        if (level.id == 0 && !tutoVM.getTuto().matchStep(Tuto.End)) {
//            tutoVM.setTutoTo(Tuto.ClickOnFirstInstructionCase)
//        }
//        else {
        prettyPrint("GameDataVM::backNavHandler","instructions saved", instructionsRows)
        levelVM.saveFunctionsInstructions(level = level, newFunciontInstructionList = instructionsRows)
//        }
    }
//    fun disposeHandler() {
//        prettyPrint("GameDataVM::disposeHandler","instructions saved", instructionsRows)
//        levelVM.saveFunctionsInstructions(level = level, newFunciontInstructionList = instructionsRows)
//    }

    fun clickResetButtonHandler() {
        viewModelScope.launch(Dispatchers.IO) {
            logClick?.let { verbalLog("GameDataVM::clickResetButtonHandler", "start ${animData.getPlayerAnimationState().key}") }
            animData.setPlayerAnimationState(PlayerAnimationState.NotStarted)
            animationJob?.cancel()
            animData.reset()
            logClick?.let {
                errorLog("GameDataVM::clickResetButtonHandler", "end ${animData.getPlayerAnimationState().key}")
                infoLog("GameDataVM::clickResetButtonHandler", "${animData.getActionToRead()}")
            }
        }
    }

    fun clickPlayPauseButtonHandler() = runBlocking(Dispatchers.Default) {
        logClick?.let { verbalLog("GameDataVM:clickPlayPauseButton", "start ${animData.getPlayerAnimationState().key}") }
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
        logClick?.let { errorLog("GameDataVM:clickPlayPauseButton", "end ${animData.getPlayerAnimationState().key}") }
    }

    fun clickNextButtonHandler() = runBlocking(Dispatchers.Default) {
        logClick?.let { verbalLog("GameDataVM::clickNextButton", "start ${animData.getPlayerAnimationState().key}") }
        if (animData.isOnPause()) { animationLogicVM.stepByStep(AnimationStream.Forward) }
        else if (animData.hasNotStarted()) {
            animationLogicVM.initialize(breadcrumb, animData)
            animData.setPlayerAnimationState(PlayerAnimationState.OnPause)
            animationLogicVM.stepByStep(AnimationStream.Forward)
        }
        else if (animData.isPlaying()) {
            clickPlayPauseButtonHandler()
        }
        else { infoLog("GameDataVM::clickNextButton", "else anim is not on pause ${animData.playerAnimationState.value.key}") }
        logClick?.let { errorLog("GameDataVM::clickNextButton", "end ${animData.getPlayerAnimationState().key}") }
    }
    fun clickBackButtonHandler() = runBlocking(Dispatchers.Default) {
        logClick?.let { verbalLog("GameDataVM::clickBackButtonHandler", "start ${animData.getPlayerAnimationState().key}") }
        if (animData.isPlaying()) {
            clickPlayPauseButtonHandler()
        } else {
            animationLogicVM.stepByStep(AnimationStream.Backward)
        }
        logClick?.let { errorLog("GameDataVM::clickBackButtonHandler", "end ${animData.getPlayerAnimationState().key}") }
    }
    fun isInstructionMenuAvailable(): Boolean = animData.playerAnimationState.value.isTheBreadcrumbModifiable()
    fun isDragAndDropAvailable(): Boolean = animData.playerAnimationState.value == PlayerAnimationState.OnPause || animData.playerAnimationState.value == PlayerAnimationState.NotStarted

    init {
                logInit?.let { errorLog("init", "GameDataViewModel") }
                level.map.printMap()
                level.starsList.printList()
    }
}
