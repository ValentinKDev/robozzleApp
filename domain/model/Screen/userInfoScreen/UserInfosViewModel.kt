package com.mobilegame.robozzle.domain.model.User

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.mobilegame.robozzle.domain.model.Screen.userInfoScreen.UserInfoScreenData
import com.mobilegame.robozzle.domain.model.Screen.userInfoScreen.UserInfoScreenDimensions
import com.mobilegame.robozzle.domain.model.Screen.userInfoScreen.UserInfoScreenLogic
import com.mobilegame.robozzle.domain.model.data.room.LevelWins.LevelWinRoomViewModel
import com.mobilegame.robozzle.domain.model.data.room.level.LevelRoomViewModel
import com.mobilegame.robozzle.domain.model.data.store.UserDataStoreViewModel
import com.mobilegame.robozzle.domain.model.level.LevelOverView

class UserInfosScreenViewModel(application: Application): AndroidViewModel(application) {

    val logic = UserInfoScreenLogic(application)
    val dimension = UserInfoScreenDimensions()
    val data = UserInfoScreenData(application)

}