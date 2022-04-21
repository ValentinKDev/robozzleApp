package com.mobilegame.robozzle.domain.model.Screen.userInfo

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilegame.robozzle.domain.model.Screen.utils.RankingIconViewModel
import com.mobilegame.robozzle.domain.model.data.room.LevelWins.LevelWinRoomViewModel
import com.mobilegame.robozzle.domain.model.data.room.level.LevelRoomViewModel
import com.mobilegame.robozzle.domain.model.data.store.UserDataStoreViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserInfoScreenLogic(val application: Application): ViewModel() {

    val name = UserDataStoreViewModel(application).getName()
    val rankingIconVM = RankingIconViewModel()
//    private val levelWinRoomVM = LevelWinRoomViewModel(getApplication())
    private val levelWinRoomVM = LevelWinRoomViewModel(application)

    val levelWinList = levelWinRoomVM.getAllLevelWins()
    val maps =  LevelRoomViewModel(application).getLevelOverViewInList(levelWinList).map { it.map }

    private val _gridVisible = MutableStateFlow<Boolean>(false)
    val gridVisible: StateFlow<Boolean> = _gridVisible.asStateFlow()
    fun setGridVisible(value: Boolean) {_gridVisible.value = value}

    init {
        //launch levelwin list from room
        //get levelwin list from server
        //compare local and server data
        //update server if needed
    }

    fun logingOut() {
        //clear user datastore
        UserDataStoreViewModel(application).clearUser()
        //clear room level win
        LevelWinRoomViewModel(application).deleteAllLevelWinRoom()
    }

    fun setVisibilityAtLaunch() {
        viewModelScope.launch {
//            setHeaderVisible(true)
//            delay(200)
            setGridVisible(true)
        }
    }
}