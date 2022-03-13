package com.mobilegame.robozzle.domain.model.Screen.InGame

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.unit.Density
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mobilegame.robozzle.analyse.*
import com.mobilegame.robozzle.utils.Extensions.replaceAt
import com.mobilegame.robozzle.utils.Extensions.toPlayerInGame
import com.mobilegame.robozzle.domain.InGame.*
import com.mobilegame.robozzle.domain.InGame.animation.AnimationData
import com.mobilegame.robozzle.domain.RobuzzleLevel.FunctionInstruction
import com.mobilegame.robozzle.domain.RobuzzleLevel.FunctionInstructions
import com.mobilegame.robozzle.domain.RobuzzleLevel.Position
import com.mobilegame.robozzle.domain.RobuzzleLevel.RobuzzleLevel
import com.mobilegame.robozzle.domain.model.Screen.mainScreen.PopupViewModel
import com.mobilegame.robozzle.domain.model.data.general.LevelVM
import com.mobilegame.robozzle.domain.model.data.room.level.LevelRoomViewModel
import com.mobilegame.robozzle.domain.model.data.store.ArgumentsDataStoreViewModel
import com.mobilegame.robozzle.domain.model.gesture.dragAndDrop.DragAndDropState
import com.mobilegame.robozzle.domain.model.level.Level
import com.mobilegame.robozzle.presentation.ui.myleveltest
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GameDataViewModel(application: Application): AndroidViewModel(application) {
    var level: Level = LevelVM(getApplication()).getLevelArgument()
//    var level: Level = myleveltest

    val data = InGameData(level, getApplication() )
    private val bdVM = BreadcrumbViewModel(level, level.funInstructionsList)
    var breadcrumb = bdVM.getBreadCrumb()
    var animData = AnimationData(level, breadcrumb)
    var animationLogicVM = AnimationLogicViewModel(level, animData, this)

    val popup = PopupViewModel()
    val dragAndDrop = DragAndDropState()
    var animationJob: Job? = null

    var selectedCase = Position.Zero
    fun setSelectedFunctionCase(row: Int, column: Int) {
        selectedCase = Position(row, column)
    }

    private val _instructionsRows = MutableStateFlow(level.funInstructionsList)
    val instructionsRows: StateFlow<List<FunctionInstructions>> = _instructionsRows.asStateFlow()
    fun setInstructionsRows(list: List<FunctionInstructions>) {_instructionsRows.value = list}
    fun getInstructionsRows(): List<FunctionInstructions> = instructionsRows.value
    fun replaceInstruction(pos: Position, case: FunctionInstruction) {
        _instructionsRows.value[pos.line].colors =
            _instructionsRows.value[pos.line].colors.replaceAt(pos.column, case.color)
        _instructionsRows.value[pos.line].instructions =
            _instructionsRows.value[pos.line].instructions.replaceAt(pos.column, case.instruction)
    }

    fun startAnimation() {
        animationLogicVM.start(breadcrumb)
    }
    fun updateAnimationLogic() {
//        animationLogicVM = AnimationLogicViewModel(this)
        animationLogicVM = AnimationLogicViewModel(level, animData, this)
    }
//    fun updateBreadcrumb(addAction: Int = 0) {
    fun updateData() {
        animData.updateBreadCrumb(breadcrumb)
//        animData = data
    }
    fun updateBreadcrumbAndData(bd: Breadcrumb) {
        verbalLog("GameDataViewModel", ":updateBreadcrumb")
//        breadcrumb = BreadcrumbViewModel(level, instructionsRows.value, addAction).getBreadCrumb()
        breadcrumb = bd
        animData.updateBreadCrumb(breadcrumb)
    }

    fun startPlayerAnimation() {
        animationJob = animationLogicVM.start(breadcrumb)
    }

    /*
    Faire un objet qui regroupe toute les infos sur l animation en cours ?
     */

    private var playerInitial: PlayerInGame = level.playerInitial.toPlayerInGame()
//    private var playerInitial: PlayerInGame = PlayerInGame.Unknown

    private val _mapLayoutPressed = MutableStateFlow<Boolean>(false)
    val mapLayoutPressed: StateFlow<Boolean> = _mapLayoutPressed

    fun mapLayoutPressedToTrue() {_mapLayoutPressed.value = true}
    fun mapLayoutPressedToFalse() {_mapLayoutPressed.value = false}

    var emptyLevel = RobuzzleLevel(
        name = "",
        id = -42,
        difficulty = -42,
        map = emptyList(),
        instructionsMenu = mutableListOf(),
        funInstructionsList = mutableListOf(),
        playerInitial = PlayerInGame(Position(0,0), Direction(0,0)),
        starsList = mutableListOf(),
    )

    private val _triggerRecompostion = MutableStateFlow<Boolean>(false)
    val triggerRecompostion: StateFlow<Boolean> = _triggerRecompostion

    fun triggerRecompostionToFalse() {_triggerRecompostion.value = false}
    fun triggerRecompostionToTrue() {_triggerRecompostion.value = true}


    //todo : try to use State instead of LiveData, it might ensure this display is triggered when you click
    private val _displayInstructionsMenu = MutableLiveData(false)
    val displayInstructionsMenu: MutableLiveData<Boolean> = _displayInstructionsMenu
    fun ChangeInstructionMenuState() {
        Log.v("DisplayMenu", "ChangeState ${_displayInstructionsMenu.value} to ${!_displayInstructionsMenu.value!!}")
        _displayInstructionsMenu.value =! _displayInstructionsMenu.value!!
    }

    fun clickResetButtonHandler() = runBlocking(Dispatchers.Default) {
        logClick?.let { errorLog("click reset vm handler", "start ${animData.getPlayerAnimationState().key}") }
//        animData.setPlayerAnimationState(PlayerAnimationState.NotStarted)
        animData = AnimationData(level, breadcrumb)
        logClick?.let { errorLog("click reset vm handler", "end ${animData.getPlayerAnimationState().key}") }
    }

    fun clickPlayPauseButtonHandler() = runBlocking(Dispatchers.Default) {
        logClick?.let { errorLog("click play pause vm handler", "start ${animData.getPlayerAnimationState().key}") }
        when (animData.getPlayerAnimationState()) {
            PlayerAnimationState.IsPlaying -> {
                animData.setPlayerAnimationState(PlayerAnimationState.OnPause)
            }
            PlayerAnimationState.OnPause -> {
                animData.setPlayerAnimationState(PlayerAnimationState.IsPlaying)
//                startPlayerAnimation()
            }
            PlayerAnimationState.NotStarted -> {
                animData.setPlayerAnimationState(PlayerAnimationState.IsPlaying)
                startPlayerAnimation()
            }
            else -> { logClick?.let { errorLog("ERROR", "GameButton playerAnimationState is neither IsPlaying or OnPause") } }
        }
        logClick?.let { errorLog("click play pause vm handler", "end ${animData.getPlayerAnimationState().key}") }
    }

    fun clickNextButtonHandler() = runBlocking(Dispatchers.Default) {
        logClick?.let { errorLog("click next vm handler", "end ${animData.getPlayerAnimationState().key}") }
        if (animData.isOnPause()) {
            animData.setPlayerAnimationState(PlayerAnimationState.GoNext)
        }
        else {
            infoLog("next", "else")
        }
        logClick?.let { errorLog("click next vm handler", "end ${animData.getPlayerAnimationState().key}") }
    }
    fun clickBackButtonHandler() = runBlocking(Dispatchers.Default) {
        logClick?.let { errorLog("click back vm handler", "start ${animData.getPlayerAnimationState().key}") }
        animData.setPlayerAnimationState(PlayerAnimationState.GoNext)
        logClick?.let { errorLog("click back vm handler", "end ${animData.getPlayerAnimationState().key}") }
//    fun AnimationGoingBackChangeStatus() { _animationGoBack.value = !_animationGoBack.value!! }
    }

    init {
        logInit?.let { errorLog("init", "GameDataViewModel") }
//        setInstructionsRows(level.funInstructionsList)
//        breadcrumb = BreadcrumbViewModel(level, instructionsRows.value).getBreadCrumb()
    }
//    fun init(lvl: Level) {
//        level = lvl
//
//        data = InGameData(lvl, getApplication())
//        breadcrumb = BreadcrumbViewModel(lvl, lvl.funInstructionsList).getBreadCrumb()
//        animationLogicVM = AnimationLogicViewModel(level)
//        playerInitial = lvl.playerInitial.toPlayerInGame()
//        setInstructionsRows(lvl.funInstructionsList)
//        updateBreadcrumb()
//
//    }
}
