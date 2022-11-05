package com.mobilegame.robozzle.domain.model.Screen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.Player.PlayerWin
import com.mobilegame.robozzle.domain.model.data.general.RankVM
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RanksLevelScreenViewModel(application: Application): AndroidViewModel(application) {
    private val _rankingList = MutableStateFlow<List<PlayerWin>>(emptyList())
    val rankingList: StateFlow<List<PlayerWin>> = _rankingList


    var firstColRankingList: List<PlayerWin> = emptyList()
    var secondColRankingList: List<PlayerWin> = emptyList()
    var thirdColRankingList: List<PlayerWin> = emptyList()

    fun load(levelId: Int) {
        infoLog("RanksLevelScreenVM::load", "start")
        _rankingList.value = RankVM(getApplication()).getLevelRanking(levelId)
        infoLog("RanksLevelScreenVM::load", "rankinglist = ${rankingList.value}")
    }
}