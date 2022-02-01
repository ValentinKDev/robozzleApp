package com.mobilegame.robozzle.domain.repository

import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.data.base.Level.LevelData
import com.mobilegame.robozzle.data.base.Level.LevelDao
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class LevelRepository(private val levelDao: LevelDao) {

    var aLevelData: LevelData? = null

    fun getAllLevelsIds(): List<Int> {
        return levelDao.getAllIds()
    }

    fun getAllLevelsFromRoom(): List<LevelData> {
        return levelDao.getAll()
    }

    fun  delAll() {
        levelDao.deleteAll()
    }

    fun  delLevel(levelData: LevelData) {
        levelDao.delete(levelData)
    }

    suspend fun addLevelsData(levelList: List<LevelData>) {
        levelList.forEachIndexed {index, it ->
            infoLog("$index", "id lvl added to room")
            addLevel(it)
        }
    }

    suspend fun addLevel(levelData: LevelData) {
        levelDao.addLevel(levelData)
    }

    fun getALevel(id: Int): LevelData? {
        return levelDao.getALevel(id)
    }

    suspend fun addLevels(levelData: List<LevelData>) {
        levelData.forEach { levelDao.addLevel(it)
        }
    }
}