package com.mobilegame.robozzle.domain.repository

import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.data.room.Level.LevelData
import com.mobilegame.robozzle.data.room.Level.LevelDao
import com.mobilegame.robozzle.domain.model.level.LevelState

class LevelRepository(private val levelDao: LevelDao) {

    fun getAllLevelsIds(): List<Int> {
        return levelDao.getAllIds()
    }

    fun getAllLevelsFromRoom(): List<LevelData> {
        return levelDao.getAll()
    }

    fun getLevelsByDifficulty(diff: Int): List<LevelData> {
        return levelDao.getAllFromDifficulty(diff)
    }

    fun getIdByDifficulty(diff: Int): List<Int> {
        return levelDao.getAllIdFromDifficulty(diff)
    }

    fun getNamesByDifficulty(diff: Int): List<String> {
        return levelDao.getAllNameFromDifficulty(diff)
    }

//    fun getStatesByDifficulty(diff: Int): List<LevelState> {
//        return levelDao.getAllLevelStatesByDifficulty(diff)
//    }

    fun getMapJsonByDifficulty(diff: Int): List<String> {
        return levelDao.getAllMapJsonFromDifficulty(diff)
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
        val leveldata = levelDao.getALevel(id)
        return leveldata
    }

    suspend fun addLevels(levelData: List<LevelData>) {
        levelData.forEach { levelDao.addLevel(it)
        }
    }
}