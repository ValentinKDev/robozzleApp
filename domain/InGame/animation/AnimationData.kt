package com.mobilegame.robozzle.domain.InGame.animation

import com.mobilegame.robozzle.utils.Extensions.clone
import com.mobilegame.robozzle.utils.Extensions.replaceAt
import com.mobilegame.robozzle.utils.Extensions.replaceInMatrice
import com.mobilegame.robozzle.utils.Extensions.toPlayerInGame
import com.mobilegame.robozzle.analyse.errorLog
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.runBlocking

class AnimationData(level: Level, private val bd: Breadcrumb = Breadcrumb) {

    private val _playerAnimationState = MutableStateFlow<PlayerAnimationState>(PlayerAnimationState.NotStarted)
    val playerAnimationState: StateFlow<PlayerAnimationState> = _playerAnimationState.asStateFlow()
    fun getPlayerAnimationState(): PlayerAnimationState = playerAnimationState.value
//    fun get(): PlayerAnimationState = runBlocking { playerAnimationState }
    fun isGoingBackward(): Boolean = getPlayerAnimationState() == PlayerAnimationState.GoBack && actionToRead.value > 0
    fun isGoingForward(): Boolean = getPlayerAnimationState() == PlayerAnimationState.GoNext && actionToRead.value < maxAction - 1
    fun isPlayeing(): Boolean = playerAnimationState.value == PlayerAnimationState.IsPlaying
    fun isOnPause(): Boolean = playerAnimationState.value == PlayerAnimationState.OnPause
    fun setPlayerAnimationState(newState: PlayerAnimationState) {_playerAnimationState.value = newState}

    val maxAction = bd.actionsCount
    private val _actionToRead = MutableStateFlow(0)
    val actionToRead: StateFlow<Int> = _actionToRead.asStateFlow()
    fun getActionToRead(): Int = actionToRead.value
    fun setActionTo(action: Int) {_actionToRead.value = action}
    fun actionInBounds(): Boolean? = if (actionToRead.value < maxAction) true else null
    fun actionOutOfBounds(): Boolean = actionToRead.value >= maxAction
    fun triggerExpandBreadcrumb() = getActionToRead() == maxAction && bd.win not true
    fun incrementActionToRead() {
        _actionToRead.value += 1
        updateActionList()
    }
    fun decrementActionToRead() {
        _actionToRead.value -= 1
        updateActionList()
    }
    private val _actionList = MutableStateFlow(bd.actionsList)
    val actionList: StateFlow<List<FunctionInstruction>> = _actionList.asStateFlow()
    private fun updateActionList() {
        _actionList.value = bd.actionsList.subList(fromIndex = getActionToRead(), toIndex = bd.actionsList.lastIndex)
    }

    var actionsList: List<FunctionInstruction> = emptyList()

    private val _playerAnimated = MutableStateFlow<PlayerInGame>(level.playerInitial.toPlayerInGame())
    val playerAnimated: StateFlow<PlayerInGame> = _playerAnimated.asStateFlow()
    suspend fun ChangePlayerAnimatedStatus(newStatus: PlayerInGame) {
//        _playerAnimated.value = newStatus
//        _playerAnimated.
        _playerAnimated.emit(newStatus)
//        _playerAnimated.postValue(newStatus)
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
    fun SetAnimatedStarMap(starsList: MutableList<Position>) { _animatedStarsMaped.value = starsList.clone() }
}