package com.mobilegame.robozzle.domain.model.Screen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.model.level.LevelOverView
import com.mobilegame.robozzle.domain.model.data.room.level.LevelRoomViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@InternalCoroutinesApi
class LevelsScreenViewModel(application: Application): AndroidViewModel(application) {
    //todo: might be wise enough to create a specific class Level overview to manipulate only id, name, map
//    private val _ = MutableStateFlow<List<Level>>(mutableListOf())
//    val robuzzleLevelList: StateFlow<List<Level>> = _robuzzleLevelList
    private val _levelOverViewList = MutableStateFlow<List<LevelOverView>>(mutableListOf())
    val levelOverViewList: StateFlow<List<LevelOverView>> = _levelOverViewList


    private val levelRoomViewModel = LevelRoomViewModel(getApplication())

    fun loadLevelListById(difficulty: Int) {
        infoLog("load Level list by diff", "start")
        _levelOverViewList.value  = levelRoomViewModel.getAllLevelOverViewFromDifficulty(difficulty)
    }

    init {
//        loadLevelListById()
    }
}