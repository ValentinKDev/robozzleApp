package com.mobilegame.robozzle.domain.repository

import android.util.Log
import com.mobilegame.robozzle.Extensions.convertToLevel
import com.mobilegame.robozzle.data.base.Level.LevelData
import com.mobilegame.robozzle.data.base.Level.LevelDao
import com.mobilegame.robozzle.data.remote.dto.LevelRequest
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class LevelRepository(private val levelDao: LevelDao) {

    var aLevelData: LevelData? = null

    //todo: lauchn the thread from viewModel with viewModelScope
    fun getAllLevelsFromRoom(): List<LevelData> {
        return levelDao.getAll()
    }

    fun  delAll() {
        levelDao.deleteAll()
    }

    fun  delLevel(levelData: LevelData) {
        levelDao.delete(levelData)
    }

    suspend fun addLevelRequests(lvlRequests: List<LevelRequest>) {
        lvlRequests.forEachIndexed {index, it ->
            addLevelRequest(it)
            Log.i(index.toString(),  "Req added to database")
        }
    }
    suspend fun addLevelRequest(lvlRequest: LevelRequest) {
        levelDao.addLevel(lvlRequest.convertToLevel())
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