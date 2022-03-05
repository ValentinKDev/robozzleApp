package com.mobilegame.robozzle.domain.model.Screen.InGame

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.mobilegame.robozzle.Extensions.clone
import com.mobilegame.robozzle.Extensions.copy
import com.mobilegame.robozzle.Extensions.replaceAt
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.domain.InGame.ColorSwitch
import com.mobilegame.robozzle.domain.InGame.Direction
import com.mobilegame.robozzle.domain.InGame.PlayerInGame
import com.mobilegame.robozzle.domain.InGame.res.BACKWARD
import com.mobilegame.robozzle.domain.InGame.res.FORWARD
import com.mobilegame.robozzle.domain.InGame.res.UNKNOWN
import com.mobilegame.robozzle.domain.RobuzzleLevel.Position
import com.mobilegame.robozzle.domain.RobuzzleLevel.RobuzzleLevel
import com.mobilegame.robozzle.domain.WinDetails.WinDetails
import com.mobilegame.robozzle.domain.model.Screen.mainScreen.PopupViewModel
import com.mobilegame.robozzle.domain.model.data.general.RankVM
import com.mobilegame.robozzle.domain.model.gesture.dragAndDrop.DragAndDropState
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


//class GameDataViewModel(lvl: RobuzzleLevel): ViewModel() {
//class GameDataViewModel(context: Context): ViewModel() {
//class GameDataViewModel(): ViewModel() {
class GameDataViewModel(application: Application): AndroidViewModel(application) {
//val context: Context = getApplication<Application>()
//    val context: Context = context
    val data = InGameData()
    val popup = PopupViewModel()
    val dragAndDrop = DragAndDropState()
    /*
    Faire un objet qui regroupe toute les infos sur l animation en cours ?
     */
//    val originalStarsList: MutableList<Position> = lvl.starsList.clone()
    var originalStarsList: MutableList<Position> = mutableListOf()

    var lvlName: String? = null
    var lvlId: Int = -42
    var lvlDifficulty: Int = -42

    private var playerInital: PlayerInGame = PlayerInGame(Position(0,0))

    private val _map = MutableStateFlow<MutableList<String>>(mutableListOf())
    val map: StateFlow<MutableList<String>> = _map

    fun ChangeCaseColorMap(colorSwitch: ColorSwitch, direction: Int) {
        when (direction) {
            FORWARD -> { _map.value!![colorSwitch.pos.line] = _map.value!![colorSwitch.pos.line].replaceAt(colorSwitch.pos.column, colorSwitch.newColor) }
            BACKWARD -> { _map.value!![colorSwitch.pos.line] = _map.value!![colorSwitch.pos.line].replaceAt(colorSwitch.pos.column, colorSwitch.oldColor) }
            else -> { errorLog("ERROR", "GameDataViewModel::ChangeCaseColorMap [Wrong direction]") }
        }
    }
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
//    var screenConfig = ScreenConfig(context, emptyLevel)

    fun init(lvl: RobuzzleLevel) {
        errorLog("Init", "GameDataViewModel")

        lvlId = lvl.id
        lvlDifficulty = lvl.difficulty
        lvlName = lvl.name
        originalStarsList = lvl.starsList.clone()
        playerInital = lvl.playerInitial
//        lvl.breadcrumb.CreateBreadcrumb()
        ResetAnimation(lvl)
        //todo: init data: InGameData() here
    }

    private val _triggerRecompostion = MutableStateFlow<Boolean>(false)
    val triggerRecompostion: StateFlow<Boolean> = _triggerRecompostion

    fun triggerRecompostionToFalse() {_triggerRecompostion.value = false}
    fun triggerRecompostionToTrue() {_triggerRecompostion.value = true}


    private val _animatedStarsMaped = MutableStateFlow<MutableList<Position>>(mutableListOf())
    val animatedStarsMaped: StateFlow<MutableList<Position>> = _animatedStarsMaped

    fun AddAnimatedStarMap(position: Position) { _animatedStarsMaped.value.add(position) }
    fun DelAnimatedStarMap(position: Position) { _animatedStarsMaped.value.remove(position) }
    fun SetAnimatedStarMap(starsList: MutableList<Position>) { _animatedStarsMaped.value = starsList.clone() }

    private val _win = MutableStateFlow<Int>(UNKNOWN)
    val win: StateFlow<Int> = _win

    fun SetWinTo(value: Int, winDetails: WinDetails) {
        if (value == com.mobilegame.robozzle.domain.res.TRUE ) {
            lvlName?.let { RankVM(getApplication()).registerANewWin(lvlId, it, lvlDifficulty, winDetails) }
        }
        _win.value = value
    }

    //todo : try to use State instead of LiveData, it might ensure this display is triggered when you click
    private val _displayInstructionsMenu = MutableLiveData(false)
    val displayInstructionsMenu: MutableLiveData<Boolean> = _displayInstructionsMenu
    fun ChangeInstructionMenuState() {
        Log.v("DisplayMenu", "ChangeState ${_displayInstructionsMenu.value} to ${!_displayInstructionsMenu.value!!}")
        _displayInstructionsMenu.value =! _displayInstructionsMenu.value!!
    }
    fun DisplayInstructionsMenuToTrue() {
        Log.v("DisplayMenu", "call toTrue")
        _displayInstructionsMenu.value = true
        Log.v("DisplayMenu", "${_displayInstructionsMenu.value}")
    }
    fun DisplayInstructionsMenuToFalse() {
        Log.v("DisplayMenu", "call toFalse")
        _displayInstructionsMenu.value = false }

    private val _animationIsPlaying = MutableLiveData(false)
    val animationIsPlaying: MutableLiveData<Boolean> = _animationIsPlaying
    fun AnimationIsPlayingChangeStatus() {
        _animationIsPlaying.value = !_animationIsPlaying.value!!
    }
    private val _animationIsOnPause = MutableLiveData(false)
    val animationIsOnPause: MutableLiveData<Boolean> = _animationIsOnPause
    fun AnimationIsOnPauseChangeStatus() {
        _animationIsOnPause.value = !_animationIsOnPause.value!!
    }

    private val _actionToRead = MutableLiveData(0)
    val actionToRead: MutableLiveData<Int> = _actionToRead
    fun SetActionTo(action: Int) {_actionToRead.value = action}
//    private val _playerAnimated = MutableLiveData(lvl.playerInital)
    private val _playerAnimated = MutableLiveData(playerInital)
    val playerAnimated: MutableLiveData<PlayerInGame> = _playerAnimated
//    val playerAnimated: MutableLiveData<PlayerInGame?> = _playerAnimated
    fun ChangePlayerAnimatedStatus(newStatus: PlayerInGame) {
//    verbalLog("change player animated status", "change")
        _playerAnimated.postValue(newStatus)
    }

    private val _animationGoBack = MutableLiveData(false)
    val animationGoBack: MutableLiveData<Boolean> = _animationGoBack
    fun AnimationGoingBackChangeStatus() { _animationGoBack.value = !_animationGoBack.value!! }
    private val _animationGoNext = MutableLiveData(false)
    val animationGoNext: MutableLiveData<Boolean> = _animationGoNext
    fun AnimationGoingNextChangeStatus() { _animationGoNext.value = !_animationGoNext.value!! }

//    private val _animatedStarsMaped
//        get() = MutableState<MutableList<Point>>(mutableStateOf())

    //    private val _animatedStarsMaped = MutableState<MutableList<Point>>(mutableListOf())
//    val animatedStarsMaped = _animatedStarsMaped
//    fun AddAnimatedStarMaped(starsMap: MutableMap<Int, Point>, key: Int){
//        animatedStarsMaped.value.add(starsMap.getValue(key))
//    }


    var gameLogicJob: Job? = null

    fun ResetAnimation(lvl: RobuzzleLevel) {
        _map.value = lvl.map.toMutableList().copy()
        Log.v("Reset", "")
        gameLogicJob?.cancel()
        _animationIsPlaying.value = false
        _animationIsOnPause.value = false
        _animationGoBack.value = false
        _actionToRead.value = 0
        _win.value = UNKNOWN
        ChangePlayerAnimatedStatus(lvl.playerInitial)
//        SetAnimatedStars(originalStarsList.clone())
        SetAnimatedStarMap(originalStarsList)
//        _animatedStarsMaped.value = originalStarsList.clone()
        SetActionTo(0)
//        lvl.guideline.win = -1
//        lvl.guideline.lost = -1
//        lvl.guideline.ResetVars(0)
//        lvl.guideline.CreateNewGuideline(0,lvl.funInstructionsList)
        lvl.breadcrumb.win = -1
        lvl.breadcrumb.lost = -1
        lvl.breadcrumb.ResetVars(0)
        lvl.breadcrumb.CreateNewBeadcrumb(0,lvl.funInstructionsList)
    }
}
