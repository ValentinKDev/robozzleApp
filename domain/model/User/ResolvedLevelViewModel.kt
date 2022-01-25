package com.mobilegame.robozzle.domain.model.User

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.mobilegame.robozzle.data.remote.ResolvedLevel.ResolvedLevelService

class ResolvedLevelViewModel(application: Application): AndroidViewModel(application) {
    val service: ResolvedLevelService

    init {
        service = ResolvedLevelService.create()
        loadWinLevelsList()
        var i: Int = 5
//        i.ToInt()
    }

    private fun loadWinLevelsList() {

    }
}