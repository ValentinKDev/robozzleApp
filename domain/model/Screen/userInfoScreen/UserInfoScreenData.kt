package com.mobilegame.robozzle.domain.model.Screen.userInfoScreen

import android.app.Application
import android.content.Context
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilegame.robozzle.domain.Player.LevelWin
import com.mobilegame.robozzle.domain.model.data.room.LevelWins.LevelWinRoomViewModel
import com.mobilegame.robozzle.domain.model.data.room.level.LevelRoomViewModel
import com.mobilegame.robozzle.domain.model.data.store.UserDataStoreViewModel
import com.mobilegame.robozzle.domain.model.level.LevelOverView
import com.mobilegame.robozzle.presentation.res.gray4
import com.mobilegame.robozzle.presentation.res.grayDark3
import com.mobilegame.robozzle.presentation.res.whiteDark4
import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.MainScreenButtonStyle
import com.mobilegame.robozzle.presentation.ui.button.UserInfosButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class UserInfoScreenData(application: Application): AndroidViewModel(application) {


    val cardNameColor = grayDark3
    val buttonListFilterColor = grayDark3
    val textColor = whiteDark4

    val name = UserDataStoreViewModel(getApplication()).getName()
    private val levelWinRoomVM = LevelWinRoomViewModel(getApplication())

//    private val _levelWinList = MutableStateFlow<MutableList<LevelWin>>(mutableListOf())
//    val levelWinList: StateFlow<MutableList<LevelWin>> = _levelWinList.asStateFlow()
    private val _levelWinList1 = MutableStateFlow<List<LevelWin>>(emptyList())
    val levelWinList1: StateFlow<List<LevelWin>> = _levelWinList1.asStateFlow()
    private val _levelWinList2 = MutableStateFlow<List<LevelWin>>(emptyList())
    val levelWinList2: StateFlow<List<LevelWin>> = _levelWinList2.asStateFlow()
    private val _levelList = MutableStateFlow<List<LevelOverView>>(emptyList())
    val levelList: StateFlow<List<LevelOverView>> = _levelList.asStateFlow()
    fun setLevelsListsAtLaunch() {
        viewModelScope.launch {
            val levelWinList = levelWinRoomVM.getAllLevelWins()
            _levelWinList1.value = levelWinList.filterIndexed { index, levelWin -> index % 2 == 0 }
            _levelWinList2.value = levelWinList.filterIndexed { index, levelWin -> index % 2 == 1 }
            _levelList.value = LevelRoomViewModel(getApplication()).getLevelOverViewInList(levelWinList)
        }
    }

//    val levelWinList = levelWinRoomVM.getAllLevelWins()
//    val levelWinList1 = levelWinRoomVM.getAllLevelWins().filterIndexed { index, levelWin -> index % 2 == 0}
//    val levelWinList2 = levelWinRoomVM.getAllLevelWins().filterIndexed { index, levelWin -> index % 2 == 1}
//    val levelList: List<LevelOverView> = LevelRoomViewModel(getApplication()).getLevelOverViewInList(levelWinList)
}