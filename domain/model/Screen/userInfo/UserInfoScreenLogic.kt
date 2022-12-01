package com.mobilegame.robozzle.domain.model.Screen.userInfo

import android.app.Application
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilegame.robozzle.domain.Player.LevelWin
import com.mobilegame.robozzle.domain.model.Screen.Navigation.NavViewModel
import com.mobilegame.robozzle.domain.model.Screen.utils.LazyListStateViewModel
import com.mobilegame.robozzle.domain.model.Screen.utils.RankingIconViewModel
import com.mobilegame.robozzle.domain.model.data.general.TokenVM
import com.mobilegame.robozzle.domain.model.data.room.LevelWins.LevelWinRoomViewModel
import com.mobilegame.robozzle.domain.model.data.room.level.LevelRoomViewModel
import com.mobilegame.robozzle.domain.model.data.store.UserDataStoreViewModel
import com.mobilegame.robozzle.presentation.ui.Navigation.Navigator
import com.mobilegame.robozzle.presentation.ui.Screen.Profil.userInfoScreen.ListSorterType
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserInfoScreenLogic(val application: Application): ViewModel() {

    val name = UserDataStoreViewModel(application).getName()
    val rankingIconVM = RankingIconViewModel().create(35)
    lateinit var lazyListVM: LazyListStateViewModel

    private val levelWinRoomVM = LevelWinRoomViewModel(application)
    private val levelRoomVM = LevelRoomViewModel(application)

    private val allLevelWinList: List<LevelWin> = levelWinRoomVM.getAllLevelWins()
    private val allLevelWinIdList: List<Int> = levelWinRoomVM.getAllWinIdsInList()
//    val allLevelWinSize: Int = allLevelWinIdList.size

    private val _levelWinList = MutableStateFlow<List<LevelWin>>(allLevelWinList)
    val levelWinList: StateFlow<List<LevelWin>> = _levelWinList.asStateFlow()
    fun upDateLevelWinListTo(list: List<LevelWin>) {
        _levelWinList.value = list
    }

    val allLevelOverViewList =  LevelRoomViewModel(application).getLevelOverViewInList(allLevelWinList)
    val allMaps =  LevelRoomViewModel(application).getLevelOverViewInList(allLevelWinList).map { it.map }
    private val _mapList = MutableStateFlow<List<List<String>>>(allMaps)
    val mapList: StateFlow<List<List<String>>> = _mapList.asStateFlow()
    fun updateMapListTo(list: List<List<String>>) {
        _mapList.value = list
    }

    val allLevelsNumber: Int = allLevelWinList.size
//    private val _levelNumber = MutableStateFlow<Float>(allLevelWinList.size.toFloat())
    var numberLevelDisplay: Float = (allLevelsNumber.toFloat() / 2) - 4
//    fun updateLevelNumber() {levelNumber = allLevelWinList.size.toFloat()}
    fun updateNumberLevel(size: Int) {numberLevelDisplay = (size.toFloat() / 2) - 4}


    private val _gridVisible = MutableStateFlow<Boolean>(false)
    val gridVisible: StateFlow<Boolean> = _gridVisible.asStateFlow()
    fun setGridVisible(value: Boolean) {_gridVisible.value = value}

    init {
        lazyListVM = LazyListStateViewModel(application, Screens.UserInfo.key)
    }

    fun loggingOut(navigator: Navigator) {
        LevelRoomViewModel(application).clearAllSolutionsSaved()
        TokenVM(application).deleteToken()
        UserDataStoreViewModel(application).clearUser()
        LevelWinRoomViewModel(application).deleteAllLevelWinRoom()
        NavViewModel(navigator).navigateToMainMenu(Screens.Profile.route)
    }

    fun setVisibilityAtLaunch() {
        viewModelScope.launch {
//            setHeaderVisible(true)
//            delay(200)
            setGridVisible(true)
        }
    }

    fun handleClickListSorter(type: ListSorterType) {
        when (type) {
            ListSorterType.All -> {
                upDateLevelWinListTo(allLevelWinList)
                updateMapListTo(allMaps)
                updateNumberLevel(allLevelWinList.size)
            }
            ListSorterType.Diff1 -> sortByDiff(1)
            ListSorterType.Diff2 -> sortByDiff(2)
            ListSorterType.Diff3 -> sortByDiff(3)
            ListSorterType.Diff4 -> sortByDiff(4)
            ListSorterType.Diff5 -> sortByDiff(5)
        }
    }
    fun sortByDiff(difficulty: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val levelByDiff = levelRoomVM.getIdByDifficulty(difficulty)
            val newList = allLevelWinList.filter { levelByDiff.contains(it.lvl_id) }
            upDateLevelWinListTo(newList)
//            upDateLevelWinListTo( allLevelWinList.filter { levelByDiff.contains(it.lvl_id) } )
            val newMaps = allLevelOverViewList.filter { levelByDiff.contains(it.id) }.map { it.map }
            updateMapListTo(newMaps)
            updateNumberLevel(newList.size)
        }
    }

    fun backHandler(navigator: Navigator) {
        NavViewModel(navigator).navigateToMainMenu(fromScreen = Screens.UserInfo.route)
    }

    private val _visibleProgressBar = MutableStateFlow(MutableTransitionState(false))
    val visibleProgressBar = _visibleProgressBar.asStateFlow()
    fun setVisibleProgressBarAs(state: Boolean) { _visibleProgressBar.value.targetState = state}
    fun visibleProgressBarAnimationEnd() = !_visibleProgressBar.value.targetState && !_visibleProgressBar.value.currentState

    private val _progress = MutableStateFlow<Float>(0.01F)
    val progress: StateFlow<Float> = _progress.asStateFlow()
    fun updateProgress(scrollState: LazyListState) {
        val progression = scrollState.firstVisibleItemIndex.toFloat() / numberLevelDisplay
        _progress.value = if (progression == 0F) 0.01F else progression
    }
}