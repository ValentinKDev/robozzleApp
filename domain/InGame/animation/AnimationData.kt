package com.mobilegame.robozzle.domain.InGame.animation

import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.analyse.logClick
import com.mobilegame.robozzle.analyse.logInit
import com.mobilegame.robozzle.domain.InGame.Breadcrumb
import com.mobilegame.robozzle.domain.InGame.ColorSwitch
import com.mobilegame.robozzle.domain.InGame.PlayerAnimationState
import com.mobilegame.robozzle.domain.InGame.PlayerInGame
import com.mobilegame.robozzle.domain.InGame.res.BACKWARD
import com.mobilegame.robozzle.domain.InGame.res.FORWARD
import com.mobilegame.robozzle.domain.RobuzzleLevel.FunctionInstruction
import com.mobilegame.robozzle.domain.RobuzzleLevel.Position
import com.mobilegame.robozzle.domain.model.gesture.dragAndDrop.not
import com.mobilegame.robozzle.domain.model.level.Level
import com.mobilegame.robozzle.utils.Extensions.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.runBlocking

class AnimationData(level: Level, private val bd: Breadcrumb = Breadcrumb) {
    init {
        logInit?.let { errorLog("Animation Data", "init") }
    }

    private val _playerAnimationState = MutableStateFlow<PlayerAnimationState>(PlayerAnimationState.NotStarted)
    val playerAnimationState: StateFlow<PlayerAnimationState> = _playerAnimationState.asStateFlow()
    fun getPlayerAnimationState(): PlayerAnimationState = playerAnimationState.value
    fun isGoingBackward(): Boolean = getPlayerAnimationState() == PlayerAnimationState.GoBack && actionToRead.value > 0
    fun isGoingForward(): Boolean = getPlayerAnimationState() == PlayerAnimationState.GoNext && actionToRead.value < maxAction - 1
    fun isPlaying(): Boolean = playerAnimationState.value == PlayerAnimationState.IsPlaying
    fun isOnPause(): Boolean = playerAnimationState.value == PlayerAnimationState.OnPause
    suspend fun setPlayerAnimationState(newState: PlayerAnimationState) {_playerAnimationState.emit(newState)}

    val maxAction = bd.actionsCount
    private val _actionToRead = MutableStateFlow(0)
    val actionToRead: StateFlow<Int> = _actionToRead.asStateFlow()
    fun getActionToRead(): Int = actionToRead.value
    fun setActionTo(action: Int) {_actionToRead.value = action}
    fun actionInBounds(): Boolean? = if (actionToRead.value < maxAction) true else null
    fun actionOutOfBounds(): Boolean = actionToRead.value >= maxAction
    fun triggerExpandBreadcrumb() = getActionToRead() == maxAction && bd.win not true
    suspend fun incrementActionToRead() {
        _actionToRead.emit(getActionToRead() + 1)
        updateActionList()
    }
    suspend fun decrementActionToRead() {
        _actionToRead.emit(getActionToRead() - 1)
        updateActionList()
    }
    private val _actionList = MutableStateFlow(bd.actionsList)
    val actionList: StateFlow<List<FunctionInstruction>> = _actionList.asStateFlow()
    private suspend fun updateActionList() {
        _actionList.emit( bd.actionsList.subList(fromIndex = getActionToRead(), toIndex = bd.actionsList.lastIndex) )
    }

    var actionsList: List<FunctionInstruction> = emptyList()

    private val _playerAnimated = MutableStateFlow<PlayerInGame>(level.playerInitial.clone().toPlayerInGame())
    val playerAnimated: StateFlow<PlayerInGame> = _playerAnimated.asStateFlow()
    suspend fun ChangePlayerAnimatedStatus(newStatus: PlayerInGame) {
        _playerAnimated.emit(newStatus)
    }

    private val _map = MutableStateFlow<MutableList<String>>(level.map.toMutableList())
    val map: StateFlow<MutableList<String>> = _map.asStateFlow()
    fun setNewMap(newMap: MutableList<String>) {_map.value = newMap}
    fun ChangeCaseColorMap(colorSwitch: ColorSwitch, direction: Int) {
        when (direction) {
//            FORWARD -> { _map.value!![colorSwitch.pos.line] = _map.value!![colorSwitch.pos.line].replaceAt(colorSwitch.pos.column, colorSwitch.newColor) }
            FORWARD -> { setNewMap(map.value.replaceInMatrice(colorSwitch.newColor, colorSwitch.pos)) }
            BACKWARD -> { _map.value!![colorSwitch.pos.line] = _map.value!![colorSwitch.pos.line].replaceAt(colorSwitch.pos.column, colorSwitch.oldColor) }
            else -> { errorLog("ERROR", "GameDataViewModel::ChangeCaseColorMap [Wrong direction]") }
        }
    }

    private val _animatedStarsMaped = MutableStateFlow<MutableList<Position>>(level.starsList.toMutableList())
    val animatedStarsMaped: StateFlow<MutableList<Position>> = _animatedStarsMaped
    fun AddAnimatedStarMap(position: Position) { _animatedStarsMaped.value.add(position) }
    fun DelAnimatedStarMap(position: Position) { _animatedStarsMaped.value.remove(position) }
    fun SetAnimatedStarMap(starsList: MutableList<Position>) { _animatedStarsMaped.value = starsList.copy() }

}