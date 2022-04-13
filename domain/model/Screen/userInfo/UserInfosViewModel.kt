package com.mobilegame.robozzle.domain.model.User

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.mobilegame.robozzle.domain.model.Screen.userInfo.UserInfoScreenData
import com.mobilegame.robozzle.domain.model.Screen.userInfo.UserInfoScreenDimensions
import com.mobilegame.robozzle.domain.model.Screen.userInfo.UserInfoScreenLogic

class UserInfosScreenViewModel(application: Application): AndroidViewModel(application) {

    val logic = UserInfoScreenLogic(application)
    val uiData = UserInfoScreenDimensions()


}