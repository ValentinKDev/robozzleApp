package com.mobilegame.robozzle.domain.model.Screen

import android.app.Application
import android.icu.util.Calendar
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.model.level.LevelOverView
import com.mobilegame.robozzle.domain.model.data.room.level.LevelRoomViewModel
import com.mobilegame.robozzle.presentation.ui.Navigator
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import com.mobilegame.robozzle.presentation.ui.elements.OnTouchBounceState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LevelsScreenByDifficultyViewModel(application: Application): AndroidViewModel(application) {
    //todo: might be wise enough to create a specific class Level overview to manipulate only id, name, map

    private val _rankingIconTouchState = MutableStateFlow(OnTouchBounceState.Released)
    val rankingIconTouchState: MutableStateFlow<OnTouchBounceState> = _rankingIconTouchState

    private var onTouchStart = Calendar.getInstance().timeInMillis
    private var onTouchEnd = Calendar.getInstance().timeInMillis

    fun rankingIconIsPressed() {
        if (_rankingIconTouchState.value == OnTouchBounceState.Released) {
            onTouchStart = Calendar.getInstance().timeInMillis
            _rankingIconTouchState.value = OnTouchBounceState.Pressed
        }
    }
    fun rankingIconIsReleased(navigator: Navigator, levelId: Int) {
        if (_rankingIconTouchState.value == OnTouchBounceState.Pressed) {
            _rankingIconTouchState.value = OnTouchBounceState.Released
            onTouchEnd = Calendar.getInstance().timeInMillis
            val diff = onTouchEnd - onTouchStart
            errorLog("diff ", "$diff")
            NavViewModel(navigator).navigateTo(
                destination = Screens.RanksLevel,
                argStr = levelId.toString(),
                delayTiming = when (diff) {
                    in 0..20 -> 100
                    in 21..50 -> 250
                    in 51..80 -> 400
                    in 81..120 -> 400
                    in 121..180 -> 450
                    in 181..300 -> 550
                    in 301..600 -> 600
                    else -> 650
                }
            )
        }
    }

    private val _levelOverViewList = MutableStateFlow<List<LevelOverView>>(mutableListOf())
    val levelOverViewList: StateFlow<List<LevelOverView>> = _levelOverViewList

    private val levelRoomViewModel = LevelRoomViewModel(getApplication())

    private val _listVisible = MutableStateFlow<Boolean>(false)
    val listVisible: StateFlow<Boolean> = _listVisible.asStateFlow()
    fun setListVisible(value: Boolean) {_listVisible.value = value}

    private val _headerVisible = MutableStateFlow(false)
    val headerVisible: StateFlow<Boolean> = _headerVisible.asStateFlow()
    fun setHeaderVisible(value: Boolean) {_headerVisible.value = value}

    fun setVisiblityAtHeadClick() {
        viewModelScope.launch {
            setListVisible(false)
            delay(200)
            setHeaderVisible(false)
        }
    }

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