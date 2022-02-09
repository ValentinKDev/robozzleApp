package com.mobilegame.robozzle.domain.model.Screen

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.Player.PlayerWin
import com.mobilegame.robozzle.domain.model.data.general.RankVM
import com.mobilegame.robozzle.domain.model.data.room.level.LevelRoomViewModel
import com.mobilegame.robozzle.domain.model.data.server.ranking.RankingServerViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class RanksLevelScreenViewModel(application: Application): AndroidViewModel(application) {
    private val _rankingList = MutableStateFlow<List<PlayerWin>>(emptyList())
    val rankingList: StateFlow<List<PlayerWin>> = _rankingList


    var firstColRankingList: List<PlayerWin> = emptyList()
    var secondColRankingList: List<PlayerWin> = emptyList()
    var thirdColRankingList: List<PlayerWin> = emptyList()

    fun load(levelId: Int) {
//        val list = RankVM(getApplication()).getLevelRanking(levelid)
//        val mutable: MutableList<List<PlayerWin>> = mutableListOf()
//        list.forEachIndexed { index ->
//            if (index )
//            if (index % 2 != 0 && index)
//            list[index]
//        }
        _rankingList.value = RankVM(getApplication()).getLevelRanking(levelId)
    }


//    init {

//    }

//    fun load(levelId: Int, context: Context) {
//        viewModelScope.launch(Dispatchers.IO) {
//            rankingList = RankVM(context).getLevelRanking(levelId).sortedBy { it.points }
//            infoLog("rankingList.size", "${rankingList.size}")
//            firstColRankingList = getFirstColumnList()
//            infoLog("first.size", "${firstColRankingList.size}")
//            secondColRankingList = getSecondColumnList()
//            thirdColRankingList = getThirdColumnList()
//        }
//
//    }

//    fun getFirstColumnList(): List<PlayerWin> = runBlocking {
//        rankingList
//            .filterIndexed { index, _ ->
//                index % 3 == 0
//            }
//            .mapIndexed { index, element ->
//                index - 2
//                element
//            }
//    }

//    fun getSecondColumnList(): List<PlayerWin> = runBlocking {
//        rankingList
//            .filterIndexed { index, _ ->
//                index % 3 == 0
//            }
//            .mapIndexed { index, element ->
//                index - 1
//                element
//            }
//    }

//    fun getThirdColumnList(): List<PlayerWin> = runBlocking {
//        rankingList
//            .filterIndexed { index, _ ->
//                index % 3 == 0
//            }
//    }
}