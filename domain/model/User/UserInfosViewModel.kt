package com.mobilegame.robozzle.domain.model.User

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.mobilegame.robozzle.data.store.DataStoreService
import com.mobilegame.robozzle.domain.model.data.store.UserDataStoreViewModel

class UserInfoViewModel(application: Application): AndroidViewModel(application) {
    val userDataStoreVM = UserDataStoreViewModel(
        service = DataStoreService.createUserService(application)
    )

    val name: String = userDataStoreVM.getUser().name

    get

    init {
        //Load the playerRanks from Room
        //Load the playerRanks (list of resolvedLevel) with Ultimate request
            //compare list from server and room use the longest or the room one
                //if server longest -> update room
                //if room longest -> update server
                //if room = server size -> update server
        //charger la page
    }

}