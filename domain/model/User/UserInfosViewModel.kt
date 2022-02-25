package com.mobilegame.robozzle.domain.model.User

import android.app.Application
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mobilegame.robozzle.data.store.DataStoreService
import com.mobilegame.robozzle.domain.Player.LevelWin
import com.mobilegame.robozzle.domain.model.data.room.LevelWins.LevelWinRoomViewModel
import com.mobilegame.robozzle.domain.model.data.room.level.LevelRoomViewModel
import com.mobilegame.robozzle.domain.model.data.store.UserDataStoreViewModel
import com.mobilegame.robozzle.domain.model.level.LevelOverView
import com.mobilegame.robozzle.presentation.ui.Navigator
import com.mobilegame.robozzle.presentation.ui.Screen.NavigationDestination
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch

class UserInfosScreenViewModel(application: Application): AndroidViewModel(application) {

    val name = UserDataStoreViewModel(getApplication()).getName()
    val levelWinList = LevelWinRoomViewModel(getApplication()).getAllLevelWins()
    val levelWinList1 = LevelWinRoomViewModel(getApplication()).getAllLevelWins().filterIndexed {index, levelWin -> index % 2 == 0}
    val levelWinList2 = LevelWinRoomViewModel(getApplication()).getAllLevelWins().filterIndexed {index, levelWin -> index % 2 == 1}
    val levelList: List<LevelOverView> = LevelRoomViewModel(getApplication()).getLevelOverViewInList(levelWinList)

    init {
        //launch levelwin list from room
        //get levelwin list from server
        //compare local and server data
            //update server if needed
    }

    fun logingOut() {
        //clear user datastore
        UserDataStoreViewModel(getApplication()).clearUser()
        //clear room level win
        LevelWinRoomViewModel(getApplication()).deleteAllLevelWinRoom()
    }
}