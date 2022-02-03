package com.mobilegame.robozzle.domain.repository

import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.data.room.PlayerRanks.LevelResolvedData
import com.mobilegame.robozzle.data.room.PlayerRanks.PlayerRanksDao
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class PlayerRanksRepository(private val playerRanksDao: PlayerRanksDao) {

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
}
