package com.mobilegame.robozzle.domain.model.User

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.mobilegame.robozzle.domain.model.Screen.userInfo.UserInfoScreenUi
import com.mobilegame.robozzle.domain.model.Screen.userInfo.UserInfoScreenLogic

class UserInfosScreenViewModel(application: Application): AndroidViewModel(application) {

    val logic = UserInfoScreenLogic(application)
    val uiData = UserInfoScreenUi(application)

}