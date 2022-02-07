package com.mobilegame.robozzle.domain.model.Screen

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobilegame.robozzle.domain.Player.PlayerWin
import com.mobilegame.robozzle.domain.model.data.general.RankVM
import com.mobilegame.robozzle.domain.model.data.room.level.LevelRoomViewModel
import com.mobilegame.robozzle.domain.model.data.server.ranking.RankingServerViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@InternalCoroutinesApi
class RanksLevelScreenViewModel(): ViewModel() {
    private var rankingList: List<PlayerWin> = emptyList()
    var firstColRankingList: List<PlayerWin> = emptyList()
    var secondColRankingList: List<PlayerWin> = emptyList()
    var thirdColRankingList: List<PlayerWin> = emptyList()

//    private val levelRoomVM = LevelRoomViewModel(context)


    fun load(levelId: Int, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            rankingList = RankVM(context).getLevelRanking(levelId).sortedBy { it.points }
            firstColRankingList = getFirstColumnList()
            secondColRankingList = getSecondColumnList()
            thirdColRankingList = getThirdColumnList()
        }

    }

    fun getFirstColumnList(): List<PlayerWin> = runBlocking {
        rankingList
//            .filterIndexed(index )
            .filterIndexed { index, _ ->
                index % 3 == 0
            }
            .mapIndexed { index, element ->
                index - 2
                element
            }
    }

    fun getSecondColumnList(): List<PlayerWin> = runBlocking {
        rankingList
            .filterIndexed { index, _ ->
                index % 3 == 0
            }
            .mapIndexed { index, element ->
                index - 1
                element
            }
    }

    fun getThirdColumnList(): List<PlayerWin> = runBlocking {
        rankingList
            .filterIndexed { index, _ ->
                index % 3 == 0
            }
    }
}