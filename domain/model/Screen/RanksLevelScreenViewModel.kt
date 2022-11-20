package com.mobilegame.robozzle.domain.model.Screen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.Player.PlayerWin
import com.mobilegame.robozzle.domain.model.data.general.RankVM
import com.mobilegame.robozzle.domain.model.data.room.level.LevelRoomViewModel
import com.mobilegame.robozzle.domain.model.level.Level
import com.mobilegame.robozzle.presentation.ui.Screen.RanksLevel.RanksLevelLayout
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class RanksLevelScreenViewModel(application: Application): AndroidViewModel(application) {
    val ui = RanksLevelLayout.create(application)

    var level: Level? = null

    private val _rankingList = MutableStateFlow<List<PlayerWin>?>(null)
    val rankingList: StateFlow<List<PlayerWin>?> = _rankingList

    private val _visibleScreen = MutableStateFlow<Boolean>(false)
    val visibleScreen: StateFlow<Boolean> = _visibleScreen.asStateFlow()
    fun setVisibleScreenTo(state: Boolean) { _visibleScreen.value = state }


    var firstColRankingList: List<PlayerWin> = emptyList()
    var secondColRankingList: List<PlayerWin> = emptyList()
    var thirdColRankingList: List<PlayerWin> = emptyList()

    fun load(levelId: Int) {
        infoLog("RanksLevelScreenVM::load", "start")
        _rankingList.value = RankVM(getApplication()).getLevelRanking(levelId)?.sortedBy { it.points }?.reversed()
        level = LevelRoomViewModel(getApplication()).getLevel(levelId)
        infoLog("RanksLevelScreenVM::load", "rankinglist = ${rankingList.value}")
    }

}