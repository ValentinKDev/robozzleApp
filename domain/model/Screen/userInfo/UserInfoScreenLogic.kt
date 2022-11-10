package com.mobilegame.robozzle.domain.model.Screen.userInfo

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilegame.robozzle.domain.Player.LevelWin
import com.mobilegame.robozzle.domain.model.Screen.Navigation.NavViewModel
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

    private val levelWinRoomVM = LevelWinRoomViewModel(application)
    private val levelRoomVM = LevelRoomViewModel(application)

    private val allLevelWinList: List<LevelWin> = levelWinRoomVM.getAllLevelWins()
    private val allLevelWinIdList: List<Int> = levelWinRoomVM.getAllWinIdsInList()
    val allLevelWinSize: Int = allLevelWinIdList.size

    private val _levelWinList = MutableStateFlow<List<LevelWin>>(allLevelWinList)
    val levelWinList: StateFlow<List<LevelWin>> = _levelWinList.asStateFlow()
    fun upDateLevelWinListTo(list: List<LevelWin>) {
        _levelWinList.value = list
    }

    val maps =  LevelRoomViewModel(application).getLevelOverViewInList(allLevelWinList).map { it.map }

    private val _gridVisible = MutableStateFlow<Boolean>(false)
    val gridVisible: StateFlow<Boolean> = _gridVisible.asStateFlow()
    fun setGridVisible(value: Boolean) {_gridVisible.value = value}

    init {
        //launch levelwin list from room
        //get levelwin list from server
        //compare local and server data
        //update server if needed
    }

    fun logingOut(navigator: Navigator) {
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
            ListSorterType.All -> upDateLevelWinListTo(allLevelWinList)
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
            upDateLevelWinListTo( allLevelWinList.filter { levelByDiff.contains(it.lvl_id) } )
        }
    }
}