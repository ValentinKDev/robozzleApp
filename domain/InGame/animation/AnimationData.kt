package com.mobilegame.robozzle.domain.InGame.animation

import androidx.compose.runtime.saveable.listSaver
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.analyse.logInit
import com.mobilegame.robozzle.domain.InGame.*
import com.mobilegame.robozzle.domain.InGame.res.BACKWARD
import com.mobilegame.robozzle.domain.InGame.res.FORWARD
import com.mobilegame.robozzle.domain.InGame.res.UNKNOWN
import com.mobilegame.robozzle.domain.RobuzzleLevel.FunctionInstruction
import com.mobilegame.robozzle.domain.RobuzzleLevel.FunctionInstructions
import com.mobilegame.robozzle.domain.RobuzzleLevel.Position
import com.mobilegame.robozzle.domain.model.gesture.dragAndDrop.not
import com.mobilegame.robozzle.domain.model.level.Level
import com.mobilegame.robozzle.domain.res.TRUE
import com.mobilegame.robozzle.utils.Extensions.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.runBlocking

class AnimationData(
    private val level: Level,
    private var bd: Breadcrumb = Breadcrumb(),
) {
    init {
        logInit?.let { errorLog("Animation Data", "init") }
    }

    var maxAction = bd.lastActionNumber
    var maxIndex = bd.lastActionNumber - 1
    private val _actionToRead = MutableStateFlow(0)
//    private val _actionToRead = MutableStateFlow(1)
    val actionToRead: StateFlow<Int> = _actionToRead.asStateFlow()
    fun getActionToRead(): Int = actionToRead.value
    fun setActionTo(action: Int) {_actionToRead.value = action}
    fun actionInBounds(): Boolean? = if (actionToRead.value < maxAction) true else null
    fun actionOutOfBounds(): Boolean = actionToRead.value >= maxAction
    suspend fun incrementActionToRead() {
        if (actionEnd() Is false) {
            _actionToRead.emit(getActionToRead() + 1)
            updateActionList()
        }
    }
    suspend fun decrementActionToRead() {
//        if (getActionToRead() >= 1) {
            _actionToRead.emit(getActionToRead() - 1)
            updateActionList()
//        }
    }
    fun actionEnd(): Boolean {
        return if (bd.win != UNKNOWN && bd.lost != UNKNOWN) false
        else getActionToRead() == bd.win || getActionToRead() == bd.lost
    }

    private val _pair = MutableStateFlow<Boolean>(true)
    val pair: StateFlow<Boolean> = _pair.asStateFlow()
    private suspend fun upDatePair() {
        _pair.emit(getActionToRead() % 2 == 0)
    }

    private val _actionRowList = MutableStateFlow(bd.actionsList.subListIfPossible(0, maxNumberActionToDisplay))
    val actionRowList: StateFlow<List<FunctionInstruction>> = _actionRowList.asStateFlow()
    private suspend fun updateActionList() {
        val actionToRead = getActionToRead()
        upDatePair()
        _actionRowList.emit(
            bd.actionsList.subListIfPossible(actionToRead, actionToRead + maxNumberActionToDisplay)
        )
    }

    private val _playerAnimationState = MutableStateFlow<PlayerAnimationState>( PlayerAnimationState.NotStarted)
    val playerAnimationState: StateFlow<PlayerAnimationState> = _playerAnimationState.asStateFlow()
    fun getPlayerAnimationState(): PlayerAnimationState = playerAnimationState.value
    fun isGoingBackward(): Boolean = getPlayerAnimationState() == PlayerAnimationState.GoBack && actionToRead.value > 0
    fun isGoingForward(): Boolean = getPlayerAnimationState() == PlayerAnimationState.GoNext && actionToRead.value < maxAction - 1
    fun isPlaying(): Boolean = playerAnimationState.value == PlayerAnimationState.IsPlaying
    fun isOnPause(): Boolean = playerAnimationState.value == PlayerAnimationState.OnPause
    suspend fun setPlayerAnimationState(newState: PlayerAnimationState) {_playerAnimationState.emit(newState)}


//    private val _playerAnimated = MutableStateFlow<PlayerInGame>( savedData?.getPlayer() ?: level.playerInitial.clone().toPlayerInGame())
    private val _playerAnimated = MutableStateFlow<PlayerInGame>( level.playerInitial.clone().toPlayerInGame())
    val playerAnimated: StateFlow<PlayerInGame> = _playerAnimated.asStateFlow()
    suspend fun ChangePlayerAnimatedStatus(newStatus: PlayerInGame) {
        _playerAnimated.emit(newStatus)
    }
    fun getPlayer(): PlayerInGame = playerAnimated.value

    private val _map = MutableStateFlow<MutableList<String>>(level.map.toMutableList())
    val map: StateFlow<MutableList<String>> = _map.asStateFlow()
    fun setNewMap(newMap: MutableList<String>) {_map.value = newMap}
//    fun ChangeCaseColorMap(colorSwitch: ColorSwitch, direction: Int) {
    fun ChangeCaseColorMap(colorSwitch: ColorSwitch, stream: AnimationStream) {
//    when (direction) {
        when (stream) {
//            FORWARD -> { setNewMap(map.value.replaceInMatrice(colorSwitch.newColor, colorSwitch.pos)) }
//            BACKWARD -> { _map.value!![colorSwitch.pos.line] = _map.value!![colorSwitch.pos.line].replaceAt(colorSwitch.pos.column, colorSwitch.oldColor) }
            AnimationStream.Forward -> { setNewMap(map.value.replaceInMatrice(colorSwitch.newColor, colorSwitch.pos)) }
            AnimationStream.Backward -> { _map.value!![colorSwitch.pos.line] = _map.value!![colorSwitch.pos.line].replaceAt(colorSwitch.pos.column, colorSwitch.oldColor) }
//            else -> { errorLog("ERROR", "GameDataViewModel::ChangeCaseColorMap [Wrong direction]") }
        }
    }

    private val _mapLayoutPressed = MutableStateFlow<Boolean>(false)
    val mapLayoutPressed: StateFlow<Boolean> = _mapLayoutPressed
    fun mapLayoutPressedToTrue() {_mapLayoutPressed.value = true}
    fun mapLayoutPressedToFalse() {_mapLayoutPressed.value = false}
    fun mapLayoutIsPresed(): Boolean = mapLayoutPressed.value

    private val _animationDelay = MutableStateFlow<Long>(200)
    val animationDelay: StateFlow<Long> = _animationDelay.asStateFlow()
    suspend fun setAnimationDelayShort() { _animationDelay.emit(100) }
    suspend fun setAnimationDelayLong() { _animationDelay.emit(400) }
    fun getAnimationDelay(): Long = animationDelay.value

//    private val _animatedStarsMaped = MutableStateFlow<MutableList<Position>>(savedData?.getStarsToDisplay() ?: level.starsList.toMutableList())
    private val _animatedStarsMaped = MutableStateFlow<MutableList<Position>>(level.starsList.toMutableList())
    val animatedStarsMaped: StateFlow<MutableList<Position>> = _animatedStarsMaped
    fun AddAnimatedStarMap(position: Position) { _animatedStarsMaped.value.add(position) }
    fun DelAnimatedStarMap(position: Position) { _animatedStarsMaped.value.remove(position) }
    fun SetAnimatedStarMap(starsList: MutableList<Position>) { _animatedStarsMaped.value = starsList.copy() }
    fun getStarsToDisplay(): MutableList<Position> = animatedStarsMaped.value

    fun updateExpandedBreadCrumb(newBd: Breadcrumb) {
        bd = newBd
        maxAction = bd.lastActionNumber
        maxIndex = bd.lastActionNumber - 1
    }

    private val _winPop = MutableStateFlow<Boolean>(false)
    val winPop: StateFlow<Boolean> = _winPop.asStateFlow()
    suspend fun setWinPopTo(value: Boolean) {
        _winPop.emit(value)
    }

    suspend fun updateBd(newBd: Breadcrumb) {
        bd = newBd
        reset()
    }

    suspend fun reset() {
        var maxAction = bd.lastActionNumber
        var maxIndex = bd.lastActionNumber - 1
        _actionToRead.emit(0)
        _actionRowList.emit(bd.actionsList.subListIfPossible(0, maxNumberActionToDisplay))
        _playerAnimationState.emit(PlayerAnimationState.NotStarted)
        _playerAnimated.emit( level.playerInitial.clone().toPlayerInGame())
        _map.emit(level.map.toMutableList())
        _mapLayoutPressed.emit(false)
        _animatedStarsMaped.emit(level.starsList.toMutableList())
        _winPop.emit(false)

    }
}