package com.mobilegame.robozzle.domain.model.Screen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.model.level.LevelOverView
import com.mobilegame.robozzle.domain.model.data.room.level.LevelRoomViewModel
import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.ButtonId
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LevelsScreenByDifficultyViewModel(application: Application): AndroidViewModel(application) {
    //todo: might be wise enough to create a specific class Level overview to manipulate only id, name, map

    private val _levelOverViewList = MutableStateFlow<List<LevelOverView>>(mutableListOf())
    val levelOverViewList: StateFlow<List<LevelOverView>> = _levelOverViewList

    private val levelRoomViewModel = LevelRoomViewModel(getApplication())

    fun loadLevelListById(levelDifficulty: Int) {
        infoLog("load Level list by diff", "start")
        _levelOverViewList.value  = levelRoomViewModel.getAllLevelOverViewFromDifficulty(levelDifficulty)
    }
}