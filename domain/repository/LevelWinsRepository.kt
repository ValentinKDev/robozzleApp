package com.mobilegame.robozzle.domain.repository

import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.data.room.levelWins.LevelWinData
import com.mobilegame.robozzle.data.room.levelWins.LevelWinDao
import kotlinx.coroutines.InternalCoroutinesApi

//@InternalCoroutinesApi
class LevelWinsRepository(private val levelWinDao: LevelWinDao) {

    fun getListLevelWinsData(): List<LevelWinData> {
        return levelWinDao.getAll()
    }

    fun getPoint(levelId: Int): Int? {
        return levelWinDao.getPointsForId(levelId)
    }

    fun getLevelWinIds(): List<Int> {
        return levelWinDao.getIds()
    }

    fun getLevelWinData(idLevel: Int): LevelWinData? {
        return levelWinDao.getLevelLevelWinData(idLevel)
    }

    suspend fun addLevelWinData(lvlResolvedData: LevelWinData) {
        levelWinDao.addLevelWinData(lvlResolvedData)
    }

    fun deleteAllLevelWinData() {
        levelWinDao.deleteAll()
    }

    suspend fun addListLevelWinData(lvlWinDataList: List<LevelWinData>) {
        lvlWinDataList.forEachIndexed { index, it ->
            infoLog(index.toString(), "levelWin added")
            addLevelWinData(it)
        }
    }
}
