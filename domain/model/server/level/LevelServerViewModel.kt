package com.mobilegame.robozzle.domain.model.server.level

import android.content.Context
import androidx.lifecycle.ViewModel
import com.mobilegame.robozzle.data.server.Level.LevelService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class LevelServerViewModel(
//    context: Context
): ViewModel() {
    val service = LevelService.create()

    fun getTotalLevels(): Int = runBlocking(Dispatchers.IO) {
        service.getLevelsNumber()
    }

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

