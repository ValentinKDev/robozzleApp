package com.mobilegame.robozzle.domain.repository

import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.data.base.PlayerRanks.LevelResolvedData
import com.mobilegame.robozzle.data.base.PlayerRanks.PlayerRanksDao
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class PlayerRanksRepository(private val playerRanksDao: PlayerRanksDao) {

//    var aLevel: Level? = null

    fun getPlayerRanksFromRoom(): List<LevelResolvedData> {
        return playerRanksDao.getAll()
    }

    fun getLevelResolved(idLevel: Int): LevelResolvedData? {
        return playerRanksDao.getLevelResolved(idLevel)
    }

    //todo : rename these because the parsing from LevelResolved to LevelResolvedData is made in the VM
    suspend fun addLevelResolved(lvlResolvedData: LevelResolvedData) {
        playerRanksDao.addLevelResolved(lvlResolvedData)
    }

    suspend fun addListLevelResolved(lvlResolvedDataList: List<LevelResolvedData>) {
        lvlResolvedDataList.forEachIndexed { index, it ->
            infoLog(index.toString(), "lvl resolved added")
            addLevelResolved(it)
        }
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
