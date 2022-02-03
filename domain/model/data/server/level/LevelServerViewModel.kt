package com.mobilegame.robozzle.domain.model.data.server.level

import androidx.lifecycle.ViewModel
import com.mobilegame.robozzle.data.server.Level.LevelService
import com.mobilegame.robozzle.domain.Player.PlayerWin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class LevelServerViewModel(
//    context: Context
): ViewModel() {
    private val service = LevelService.create()

//    fun getLevelRanking(id: Int): List<PlayerWin> = runBlocking(Dispatchers.IO) {
//        service.getLevelRanking(id).toListPlayerWin()
//    }

//    fun getTotalLevels(): Int = runBlocking(Dispatchers.IO) {
//        service.getLevelsNumber()
//    }

    fun getLevelListById(listOfId: List<Int>): List<String> = runBlocking(Dispatchers.IO) {
        service.getLevelsById(listOfId)
    }

    fun getLevelIdList(): List<Int> = runBlocking(Dispatchers.IO) {
        service.getLevelIdList().toIntList()
    }

//    fun getLevelsList(): List<Level> = runBlocking(Dispatchers.IO) {
//        service.getAllLevels().toLevelList()
//    }

    fun getAllLevelList(): List<String> = runBlocking(Dispatchers.IO) {
        service.getAllLevels()
    }
}

