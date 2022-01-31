package com.mobilegame.robozzle.domain.repository

import android.util.Log
import com.mobilegame.robozzle.Extensions.convertToLevel
import com.mobilegame.robozzle.data.base.Level.Level
import com.mobilegame.robozzle.data.base.Level.LevelDao
import com.mobilegame.robozzle.data.base.PlayerRanks.PlayerRanksDao
import com.mobilegame.robozzle.data.remote.dto.LevelRequest
import com.mobilegame.robozzle.data.remote.dto.UltimateUser.PlayerRanksRequest
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class PlayerRanksRepository(private val playerRanksDao: PlayerRanksDao) {

//    var aLevel: Level? = null

    fun getPlayerRanksFromRoom(): PlayerRanksRequest {
        return PlayerRanksRequest(resolvedLevels = playerRanksDao.getAll())

    }

//    fun resolvedLevelDataToRequest
//    //todo: lauchn the thread from viewModel with viewModelScope
//    fun getAllLevelsFromRoom(): List<Level> {
//        return levelDao.getAll()
//    }
//
//    fun  delAll() {
//        levelDao.deleteAll()
//    }
//
//    fun  delLevel(level: Level) {
//        levelDao.delete(level)
//    }
//
//    suspend fun addLevelRequests(lvlRequests: List<LevelRequest>) {
//        lvlRequests.forEach {
//            addLevelRequest(it)
//            Log.i(it.id, "Req added to database")
//        }
//    }
//
//    suspend fun addLevelRequest(lvlRequest: LevelRequest) {
//        levelDao.addLevel(lvlRequest.convertToLevel())
//    }
//
//    suspend fun addLevel(level: Level) {
//        levelDao.addLevel(level)
//    }
//
//    suspend fun addLevels(levels: List<Level>) {
//        levels.forEach { levelDao.addLevel(it)
//        }
//    }

//    fun getALevel(id: Int) {
//        aLevel = levelDao.getALevel(id)
//    }
}
