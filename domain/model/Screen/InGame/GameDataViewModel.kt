package com.mobilegame.robozzle.domain.model.Screen.InGame

import android.content.Context
import android.util.Log
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.unit.Density
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilegame.robozzle.Extensions.clone
import com.mobilegame.robozzle.Extensions.copy
import com.mobilegame.robozzle.Extensions.replaceAt
import com.mobilegame.robozzle.Extensions.toPlayerInGame
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.verbalLog
import com.mobilegame.robozzle.domain.InGame.*
import com.mobilegame.robozzle.domain.InGame.res.BACKWARD
import com.mobilegame.robozzle.domain.InGame.res.FORWARD
import com.mobilegame.robozzle.domain.InGame.res.UNKNOWN
import com.mobilegame.robozzle.domain.RobuzzleLevel.FunctionInstructions
import com.mobilegame.robozzle.domain.RobuzzleLevel.Position
import com.mobilegame.robozzle.domain.RobuzzleLevel.RobuzzleLevel
import com.mobilegame.robozzle.domain.WinDetails.WinDetails
import com.mobilegame.robozzle.domain.model.Screen.mainScreen.PopupViewModel
import com.mobilegame.robozzle.domain.model.data.general.RankVM
import com.mobilegame.robozzle.domain.model.gesture.dragAndDrop.DragAndDropState
import com.mobilegame.robozzle.domain.model.level.Level
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class GameDataViewModel(val level: Level, dens: Density, layoutCoordinates: LayoutCoordinates): ViewModel() {
    val data = InGameData(level, dens, layoutCoordinates)
    val popup = PopupViewModel()
    val dragAndDrop = DragAndDropState()

    lateinit var breadcrumb: Breadcrumb

    val animationLogicVM = AnimationLogicViewModel(this)
    var animationJob: Job? = null

    lateinit var selectedCase: Position
    fun setSelectedFunctionCase(row: Int, column: Int) {
        selectedCase = Position(row, column)
    }

    init { updateBreadcrumb() }

    fun updateBreadcrumb() { breadcrumb = BreadcrumbViewModel(level, instructionsRows.value).bd }
    fun startPlayerAnimation() {
        animationJob = animationLogicVM.start(breadcrumb)
    }

    /*
    Faire un objet qui regroupe toute les infos sur l animation en cours ?
     */

    private var playerInitial: PlayerInGame = level.playerInitial.toPlayerInGame()


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


//    private val _animationIsPlaying = MutableLiveData(false)
//    val animationIsPlaying: MutableLiveData<Boolean> = _animationIsPlaying
//    fun AnimationIsPlayingChangeStatus() {
//        _animationIsPlaying.value = !_animationIsPlaying.value!!
//    }
//    private val _animationIsOnPause = MutableLiveData(false)
//    val animationIsOnPause: MutableLiveData<Boolean> = _animationIsOnPause
//    fun AnimationIsOnPauseChangeStatus() {
//        _animationIsOnPause.value = !_animationIsOnPause.value!!
//    }


//    private val _animationGoBack = MutableLiveData(false)
//    val animationGoBack: MutableLiveData<Boolean> = _animationGoBack
//    fun AnimationGoingBackChangeStatus() { _animationGoBack.value = !_animationGoBack.value!! }
//    private val _animationGoNext = MutableLiveData(false)
//    val animationGoNext: MutableLiveData<Boolean> = _animationGoNext
//    fun AnimationGoingNextChangeStatus() { _animationGoNext.value = !_animationGoNext.value!! }


    fun ResetAnimation(lvl: RobuzzleLevel) {
        Log.v("Reset", "animation")
        _map.value = lvl.map.toMutableList().copy()

//        _animationIsPlaying.value = false
//        _animationIsOnPause.value = false
//        _animationGoBack.value = false
        _actionToRead.value = 0
        _win.value = UNKNOWN
        ChangePlayerAnimatedStatus(lvl.playerInitial)
        SetAnimatedStarMap(lvl.starsList)
        SetActionTo(0)

        updateBreadcrumb()
    }

    private val _instructionsRows = MutableStateFlow(level.funInstructionsList)
    val instructionsRows: StateFlow<List<FunctionInstructions>> = _instructionsRows.asStateFlow()
//    fun setInstructionsRows(list: List<FunctionInstructions>): List<FunctionInstructions> {_instructionsRows.value = list}
    fun getInstructionsRows(): List<FunctionInstructions> = instructionsRows.value

    fun replaceInstruction(pos: Position, case: FunctionInstructions) {
        _instructionsRows.value[pos.line].colors =
            _instructionsRows.value[pos.line].colors.replaceAt(pos.column, case.colors.first())
        _instructionsRows.value[pos.line].instructions =
            _instructionsRows.value[pos.line].instructions.replaceAt(pos.column, case.instructions.first())
    }

//    fun switch(position1: Position, c1: Char, position2: Position, c2: Char) {
//        setSelectedFunctionCase()
//    }
}
