package com.mobilegame.robozzle.domain.model.Screen.LevelsScreenByDiff

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.AndroidViewModel
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.model.Screen.Navigation.NavViewModel
import com.mobilegame.robozzle.domain.model.Screen.Tuto.Tuto
import com.mobilegame.robozzle.domain.model.Screen.Tuto.Tuto.Companion.isLevelsScreenByDifficultyOn
import com.mobilegame.robozzle.domain.model.Screen.Tuto.TutoViewModel
import com.mobilegame.robozzle.domain.model.Screen.Tuto.matchStep
import com.mobilegame.robozzle.domain.model.Screen.utils.LazyListStateViewModel
import com.mobilegame.robozzle.domain.model.Screen.utils.RankingIconViewModel
import com.mobilegame.robozzle.domain.model.data.general.LevelVM
import com.mobilegame.robozzle.domain.model.data.room.Config.ConfigRoomViewModel
import com.mobilegame.robozzle.domain.model.level.LevelOverView
import com.mobilegame.robozzle.presentation.ui.Navigation.Navigator
import com.mobilegame.robozzle.presentation.ui.Screen.LevelsScreenByDifficulty.LevelsScreenByDifficultyLayout
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import com.mobilegame.robozzle.utils.Extensions.Is
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LevelsScreenByDifficultyViewModel(application: Application): AndroidViewModel(application) {
    private var levelSelectedId: Int? = null


    val tutoVM = TutoViewModel(getApplication())
    val ui = LevelsScreenByDifficultyLayout.create(getApplication())
    private val levelVM = LevelVM(getApplication())
    private val configRoom = ConfigRoomViewModel(getApplication())
    private val displayLevelWin = configRoom.getDisplayLevelWinInListState()
    lateinit var lazyListVM: LazyListStateViewModel
    val rankingIconVM: RankingIconViewModel = RankingIconViewModel().create(35)

    val mapViewParamList: MutableList<Pair<Int, MapViewParam>> = mutableListOf()

    private val _levelOverviewList = MutableStateFlow(mutableListOf<LevelOverView>())
    val levelOverviewList = _levelOverviewList.asStateFlow()
    var levelsNumber = 0F

    fun init(difficulty: Int) {
        tutoVM.updateLevelDifficultyTo(difficulty)
        lazyListVM = LazyListStateViewModel(getApplication(), difficulty)
        setVisibilityAndLoadLevelList()
        loadLevelListById(difficulty)
        loadMapViewParams()
        if (tutoVM.tuto.value.matchStep(Tuto.ClickOnRankingIconSecond)) tutoVM.nextTuto()
    }

    fun loadLevelListById(levelDifficulty: Int) {
        infoLog("LevelsScreensByDifficultyVM:loadLevelListById", "start")
        _levelOverviewList.value = levelVM.getAllLevelOverViewFromDifficulty(levelDifficulty, displayLevelWin) .toMutableList()
//        if (tutoVM.isLevelsScreenByDiffTutoActivated()) {
//            val list = mutableListOf<LevelOverView>()
//            _levelOverviewList.value =
//        }
        levelsNumber = _levelOverviewList.value.size.toFloat() - 5F
    }

    fun loadMapViewParams() {
        infoLog("LevelsScreensByDifficultyVM::loadMapViewParams", "start")
//        viewModelScope.launch(Dispatchers.IO) {
            levelOverviewList.value.forEach {
                mapViewParamList.add(element = Pair(it.id, MapViewParam(it.map, 80)))
            }
//        }
    }

    private val _progress = MutableStateFlow<Float>(0.01F)
    val progress: StateFlow<Float> = _progress.asStateFlow()
    fun updateProgress(scrollState: LazyListState) {
        val progression = scrollState.firstVisibleItemIndex.toFloat() / levelsNumber
        _progress.value = if (progression == 0F) 0.01F else progression
    }

    private val _visibleProgressBar = MutableStateFlow(MutableTransitionState(false))
    val visibleProgressBar = _visibleProgressBar.asStateFlow()
    fun setVisibleProgressBarAs(state: Boolean) { _visibleProgressBar.value.targetState = state}
    fun visibleProgressBarAnimationEnd() = !_visibleProgressBar.value.targetState && !_visibleProgressBar.value.currentState


    private val _visibleHeaderState = MutableStateFlow(MutableTransitionState(true))
    val visibleHeaderState = _visibleHeaderState.asStateFlow()
    fun setVisibleHeaderTargetStateAs(state: Boolean) {_visibleHeaderState.value.targetState = state}
    fun headerAnimationEnd(): Boolean = !_visibleHeaderState.value.targetState && !_visibleHeaderState.value.currentState

    private val _visibleListState = MutableStateFlow(MutableTransitionState(false))
    val visibleListState = _visibleListState.asStateFlow()
    fun setVisibleListTargetStateAs(state: Boolean) {_visibleListState.value.targetState = state}
    fun listAnimationEnd(): Boolean = !_visibleListState.value.targetState && !_visibleListState.value.currentState

    fun headerAndListAnimationEnd(): Boolean = headerAnimationEnd() && listAnimationEnd()

    private val _goToLevelPlayState = MutableStateFlow<Boolean>(false)
    val goToLevelPlayState: StateFlow<Boolean> = _goToLevelPlayState.asStateFlow()
    fun setGotoPlayScreenStateAs(state: Boolean) {_goToLevelPlayState.value = state}
    fun goToLevelPlayState(): Boolean = _goToLevelPlayState.value

    private var _goToRanksLevelState: Boolean = false
    fun setGoToRanksLevelScreenAs(state: Boolean) { _goToRanksLevelState = state }
    fun goToRanksLevelState(): Boolean = _goToRanksLevelState

    private val _returnToMainMenuState = MutableStateFlow<Boolean>(false)
    val returnToMainMenuState: StateFlow<Boolean> = _returnToMainMenuState.asStateFlow()
    fun setRetToMainMenuState(state: Boolean) { _returnToMainMenuState.value = state }
    fun goToMainMenuState(): Boolean = _returnToMainMenuState.value

    fun setVisibilityAndLoadLevelList() {
        setVisibleHeaderTargetStateAs(true)
        setVisibleListTargetStateAs(true)
    }
    fun startExitAnimationAndPressBack() = runBlocking(Dispatchers.IO) {
        setVisibleListTargetStateAs(false)
        setRetToMainMenuState(true)
    }
    private fun goingMainMenuAnimationEnd() =
        headerAnimationEnd() && listAnimationEnd() && goToMainMenuState()
    fun goingMainMenuListener(navigator: Navigator, fromScreen: Screens, levelsDifficulty: Int) {
        if (goingMainMenuAnimationEnd()) navigateToMainMenu(navigator,fromScreen, levelsDifficulty)
        if (listAnimationEnd()) setVisibleHeaderTargetStateAs(false)
    }
    private fun navigateToMainMenu(navigator: Navigator, fromScreen: Screens, levelsDifficulty: Int) {
        NavViewModel(navigator).navigateToMainMenu(fromScreen = Screens.findScreen(keyToFind = levelsDifficulty).route)
    }

    fun startExitAnimationAndPressLevel(levelId: Int) = runBlocking(Dispatchers.IO) {
        levelSelectedId = levelId
        setVisibleListTargetStateAs(false)
        setVisibleHeaderTargetStateAs(false)
        setGotoPlayScreenStateAs(true)
    }

    fun startExitAnimationAndPressRankingLevel(levelId: Int) = runBlocking(Dispatchers.IO) {
        Log.d("LevelScreenByDiffVM::startExitAnimationAndPressRankingLevel", "start")
        levelSelectedId = levelId
        setVisibleListTargetStateAs(false)
        setVisibleHeaderTargetStateAs(false)
        _goToRanksLevelState = true
    }
    fun goingRankingLevelListener(navigator: Navigator, levelId: Int) {
        if (rankingIconVM.isAnimationFinished() && _goToRanksLevelState.Is(false)) startExitAnimationAndPressRankingLevel(levelId)
        if (tutoVM.tuto.value.matchStep(Tuto.ClickOnRankingIconFirst)) tutoVM.nextTuto()
        if (_goToRanksLevelState && headerAndListAnimationEnd())
            NavViewModel(navigator).navigateToRanksLevel(rankingIconVM.levelSelected.value.toString())
    }

    private fun goingPlayScreenAnimationEnd() =
        headerAnimationEnd() && listAnimationEnd() && goToLevelPlayState()
    fun goingPlayScreenListener(navigator: Navigator, ctxt: Context) {
        levelSelectedId?.let { _levelId ->
            if (goingPlayScreenAnimationEnd()) {
//                if (tutoVM.isMainScreenTutoActivated() && tutoVM.tuto.matchStep(Tuto.ClickOnTutoLevel)) tutoVM.nextStep()
                if (tutoVM.isMainScreenTutoActivated() && tutoVM.getTuto().matchStep(Tuto.ClickOnTutoLevel)) tutoVM.nextTuto()
                navigateToLevel(navigator, _levelId, ctxt)
            }
        }
    }
    fun navigateToLevel(navigator: Navigator, levelId: Int, ctxt: Context) {
        //todo : why using context to store the level actually ???
        NavViewModel(navigator).navigateToScreenPlay(levelId = levelId, context = ctxt)
    }

    fun getEnterTransitionForList(fromScreen: Screens): EnterTransition = runBlocking {
        when (fromScreen) {
            Screens.Playing -> slideInHorizontally() + fadeIn()
            Screens.MainMenu -> slideInVertically(animationSpec = tween(600))
//            Screens.MainMenu -> expandIn(animationSpec = tween(600))
            else -> fadeIn()
        }
    }
    fun getExitTransitionForList(): ExitTransition = runBlocking {
        when  {
            goToLevelPlayState() ->
                slideOutHorizontally() + fadeOut()
            goToMainMenuState() ->
                shrinkVertically(
//                    Alignment.Top,
                    animationSpec = tween(200)
                ) + fadeOut(
                    animationSpec = tween(150)
                )
            goToRanksLevelState() -> {
                slideOutVertically(
                    targetOffsetY = { +250 },
                    animationSpec = tween(600)
                ) + fadeOut(
                    targetAlpha = 0F,
                    animationSpec = tween(600)
                )
            }
            else -> fadeOut()
        }
    }

    fun getExitTransitionForHeader(): ExitTransition = runBlocking {
//        errorLog("LevelsScreenByDiffVM::getExitTransitionForHeader", "Screens -> LevelPlay ${goToLevelPlayState()} MainMenu ${goToMainMenuState()} RankingLevel ${goToRanksLevelState()}")
        when {
            goToLevelPlayState() ->
                slideOutHorizontally() + fadeOut()
            goToMainMenuState() ->
                slideOutVertically(targetOffsetY = { +250 }) + fadeOut(targetAlpha = 0F)
            goToRanksLevelState() -> {
                slideOutVertically(
                    targetOffsetY = { +250 },
                    animationSpec = tween(600)
                ) + fadeOut(
                    targetAlpha = 0F,
                    animationSpec = tween(600)
                )
            }
            else -> fadeOut()
        }
    }

}