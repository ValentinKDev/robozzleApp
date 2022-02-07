package com.mobilegame.robozzle.domain.model.data.room.LevelWins

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.mobilegame.robozzle.data.room.levelWins.LevelWinDao
import com.mobilegame.robozzle.data.room.levelWins.LevelWinData
import com.mobilegame.robozzle.data.room.levelWins.LevelWinDataBase
import com.mobilegame.robozzle.domain.Player.LevelWin
import com.mobilegame.robozzle.domain.WinDetails.WinDetails
import com.mobilegame.robozzle.domain.model.data.room.level.isBetterThan
//import com.mobilegame.robozzle.domain.LevelResolved.LevelResolved
import com.mobilegame.robozzle.domain.repository.LevelWinsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@InternalCoroutinesApi
class LevelWinRoomViewModel(context: Context): ViewModel() {
    private val dao: LevelWinDao = LevelWinDataBase.getInstance(context).playerRanksDao()
    private val repo: LevelWinsRepository = LevelWinsRepository(dao)

    fun winDetailsIsBetter(idLevel: Int, winDetails: WinDetails): Boolean = runBlocking(Dispatchers.IO) {
        repo.getLevelWinData(idLevel)?. let {
            val winDetailData = Gson().fromJson(it.winDetailsJson, WinDetails::class.java)
            winDetails.isBetterThan(winDetailData)
        } ?: true
    }

    fun noBetterInStock(levelId: Int, points: Int): Boolean = runBlocking(Dispatchers.IO) {
        repo.getPoint(levelId)?.let { pointsRoom ->
            points > pointsRoom
        } ?: true
    }

    fun addLevelWinData(levelId: Int, levelName: String, points: Int, winDetails: WinDetails) {
         viewModelScope.launch(Dispatchers.IO) {
             val lvlWinData = LevelWinData(
//                 id = levelId,
                 levelId = levelId,
                 levelName = levelName,
                 points = points,
                 winDetailsJson = Gson().toJson(winDetails)
             )
             repo.addLevelWinData(lvlWinData)
         }
    }

    fun getAllLevelWins(): List<LevelWin> = runBlocking(Dispatchers.IO) {
        repo.getListLevelWinsData().toLevelWinList()
    }
}

fun List<LevelWinData>.toLevelWinList(): List<LevelWin> {
    val mutableList: MutableList<LevelWin> = mutableListOf()
    this.forEach {
        val windetails: WinDetails = Gson().fromJson(it.winDetailsJson, WinDetails::class.java)
        mutableList.add(
            LevelWin(
                levelId = it.levelId,
                levelName = it.levelName,
                points = it.points,
                winDetails = windetails
            )
        )
    }
    return mutableList.toList()
}


