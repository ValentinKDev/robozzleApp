package com.mobilegame.robozzle.domain.InGame.animation

import androidx.lifecycle.MutableLiveData
import com.mobilegame.robozzle.Extensions.clone
import com.mobilegame.robozzle.Extensions.replaceAt
import com.mobilegame.robozzle.Extensions.replaceInMatrice
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.domain.InGame.Breadcrumb
import com.mobilegame.robozzle.domain.InGame.ColorSwitch
import com.mobilegame.robozzle.domain.InGame.PlayerAnimationState
import com.mobilegame.robozzle.domain.InGame.PlayerInGame
import com.mobilegame.robozzle.domain.InGame.res.BACKWARD
import com.mobilegame.robozzle.domain.InGame.res.FORWARD
import com.mobilegame.robozzle.domain.RobuzzleLevel.Position
import com.mobilegame.robozzle.domain.model.gesture.dragAndDrop.not
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AnimationData(initialMap: MutableList<String>, initialPlayer: PlayerInGame, private val bd: Breadcrumb) {

    private val _playerAnimationState = MutableStateFlow<PlayerAnimationState>(PlayerAnimationState.NotStarted)
    val playerAnimationState: StateFlow<PlayerAnimationState> = _playerAnimationState.asStateFlow()
    fun getPlayerAnimationState(): PlayerAnimationState = playerAnimationState.value
    fun isGoingBackward(): Boolean = getPlayerAnimationState() == PlayerAnimationState.GoBack && actionToRead.value > 0
    fun isGoingForward(): Boolean = getPlayerAnimationState() == PlayerAnimationState.GoNext && actionToRead.value < maxAction - 1
    fun setPlayerAnimationState(newState: PlayerAnimationState) {_playerAnimationState.value = newState}

    val maxAction = bd.actionsCount
    private val _actionToRead = MutableStateFlow(0)
    val actionToRead: StateFlow<Int> = _actionToRead.asStateFlow()
    fun getActionToRead(): Int = actionToRead.value
    fun setActionTo(action: Int) {_actionToRead.value = action}
    fun actionInBounds(): Boolean? = if (actionToRead.value < maxAction) true else null
    fun actionOutOfBounds(): Boolean = actionToRead.value >= maxAction
    fun triggerExpandBreadcrumb() = getActionToRead() == maxAction && bd.win not true
    fun incrementActionToRead() { _actionToRead.value += 1 }
    fun decrementActionToRead() { _actionToRead.value -= 1 }
    private fun Int.triggerStar(direction: Int): Boolean {
        return when(direction) {
            FORWARD -> {stars.toRemove.containsKey(actionToRead)}
            BACKWARD -> {stars.removed.containsKey(actionToRead)}
            else -> { errorLog("Triger Star", "Error"); false }
        }
    }

    private val _playerAnimated = MutableLiveData(initialPlayer)
    val playerAnimated: MutableLiveData<PlayerInGame> = _playerAnimated
    fun ChangePlayerAnimatedStatus(newStatus: PlayerInGame) {
        _playerAnimated.postValue(newStatus)
    }

    private val _map = MutableStateFlow<MutableList<String>>(initialMap)
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

    private val _animatedStarsMaped = MutableStateFlow<MutableList<Position>>(mutableListOf())
    val animatedStarsMaped: StateFlow<MutableList<Position>> = _animatedStarsMaped
    fun AddAnimatedStarMap(position: Position) { _animatedStarsMaped.value.add(position) }
    fun DelAnimatedStarMap(position: Position) { _animatedStarsMaped.value.remove(position) }
    fun SetAnimatedStarMap(starsList: MutableList<Position>) { _animatedStarsMaped.value = starsList.clone() }
}

//sealed class ActionToReadState(key: String) {
//    object IndexOutOfBound: ActionToReadState("index_out_of_bound")
//    object Trig: ActionToReadState("index_out_of_bound")
//}

//infix fun AnimationData