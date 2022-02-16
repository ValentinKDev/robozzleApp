package com.mobilegame.robozzle.domain.model.Screen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.model.level.LevelOverView
import com.mobilegame.robozzle.domain.model.data.room.level.LevelRoomViewModel
import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.ButtonId
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LevelsScreenByDifficultyViewModel(application: Application): AndroidViewModel(application) {
    //todo: might be wise enough to create a specific class Level overview to manipulate only id, name, map

    private val _levelOverViewList = MutableStateFlow<List<LevelOverView>>(mutableListOf())
    val levelOverViewList: StateFlow<List<LevelOverView>> = _levelOverViewList

    private val levelRoomViewModel = LevelRoomViewModel(getApplication())

    private val _listVisible = MutableStateFlow<Boolean>(false)
    val listVisible: StateFlow<Boolean> = _listVisible.asStateFlow()
    fun setListVisible(value: Boolean) {_listVisible.value = value}

    private val _headerVisible = MutableStateFlow(false)
    val headerVisible: StateFlow<Boolean> = _headerVisible.asStateFlow()
    fun setHeaderVisible(value: Boolean) {_headerVisible.value = value}

    fun setVisibilityAtLaunch() {
        viewModelScope.launch {
            setHeaderVisible(true)
            delay(200)
            setListVisible(true)
        }
    }

    fun loadLevelListById(levelDifficulty: Int) {
        infoLog("load Level list by diff", "start")
        _levelOverViewList.value  = levelRoomViewModel.getAllLevelOverViewFromDifficulty(levelDifficulty)
    }
}