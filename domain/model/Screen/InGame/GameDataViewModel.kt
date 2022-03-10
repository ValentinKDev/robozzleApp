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
import com.mobilegame.robozzle.utils.Extensions.replaceAt
import com.mobilegame.robozzle.utils.Extensions.toPlayerInGame
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.analyse.verbalLog
import com.mobilegame.robozzle.domain.InGame.*
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



//class GameDataViewModelFactory(private val level: Level, val context: Context): ViewModelProvider.NewInstanceFactory() {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T = GameDataViewModel(level, context) as T
//}

class GameDataViewModel(application: Application): AndroidViewModel(application) {
//class GameDataViewModel( val level: Level, context: Context): ViewModel() {
//class GameDataViewModel( context: Context): ViewModel() {
//    val level = myleveltest
//    lateinit var level: Level
//val level = LevelVM(getApplication()).getLevelArgument()
//    val level = LevelVM(context).getLevelArgument()
//    val data = InGameData(level, context)


//    val lvl: Level? = LevelRoomViewModel(context).getLevel(ArgumentsDataStoreViewModel(context).getLevelNumberArg())
//    val lvl: Level? = LevelRoomViewModel(getApplication()).getLevel(ArgumentsDataStoreViewModel(getApplication()).getLevelNumberArg())
//var level: Level = lvl ?: myleveltest
    var level: Level = myleveltest
    //    lateinit var data: InGameData
    val data = InGameData(level, getApplication() )
//val data = InGameData(level, context)
    val popup = PopupViewModel()
    val dragAndDrop = DragAndDropState()

    var breadcrumb = BreadcrumbViewModel(level, level.funInstructionsList).getBreadCrumb()
//    lateinit var breadcrumb: Breadcrumb

    var animationLogicVM = AnimationLogicViewModel(level)
//    lateinit var animationLogicVM: AnimationLogicViewModel
    var animationJob: Job? = null

//    lateinit var selectedCase: Position
    var selectedCase = Position.Zero
    fun setSelectedFunctionCase(row: Int, column: Int) {
        selectedCase = Position(row, column)
    }

    private val _instructionsRows = MutableStateFlow(level.funInstructionsList)
//    private val _instructionsRows = MutableStateFlow<List<FunctionInstructions>>(emptyList())
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
        animationLogicVM = AnimationLogicViewModel(level)
    }
    fun updateBreadcrumb() {
        verbalLog("GameDataViewModel", ":updateBreadcrumb")
        breadcrumb = BreadcrumbViewModel(level, instructionsRows.value).getBreadCrumb()
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

    fun ResetAnimation() {
        Log.v("Reset", "animation")
        updateBreadcrumb()
        updateAnimationLogic()
    }

    fun clickPlayPauseButtonHandler() = runBlocking(Dispatchers.Default) {
        when (animationLogicVM.data.playerAnimationState.value) {
            PlayerAnimationState.IsPlaying -> {
                animationLogicVM.data.setPlayerAnimationState(PlayerAnimationState.OnPause)
            }
            PlayerAnimationState.OnPause -> {
                animationLogicVM.data.setPlayerAnimationState(PlayerAnimationState.IsPlaying)
                startPlayerAnimation()
            }
            else -> { errorLog("ERROR", "GameButton playerAnimationState is neither IsPlaying or OnPause") }
        }
    }
    fun clickNextButtonHandler() = runBlocking(Dispatchers.Default) {
        if (animationLogicVM.data.isOnPause()) {
            animationLogicVM.data.setPlayerAnimationState(PlayerAnimationState.GoNext)
        }
        else {
            infoLog("next", "else")
        }
    }
    fun clickBackButtonHandler() = runBlocking(Dispatchers.Default) {
        animationLogicVM.data.setPlayerAnimationState(PlayerAnimationState.GoNext)
//    fun AnimationGoingBackChangeStatus() { _animationGoBack.value = !_animationGoBack.value!! }
    }

    init {
        errorLog("init", "GameDataViewModel")
        infoLog("instructionsRows", "${getInstructionsRows()}")
        setInstructionsRows(level.funInstructionsList)
        infoLog("instructionsRows", "${getInstructionsRows()}")
        updateBreadcrumb()
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
