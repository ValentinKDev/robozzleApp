package com.mobilegame.robozzle.domain.repository

import com.mobilegame.robozzle.data.room.Config.ConfigDao
import com.mobilegame.robozzle.data.room.Config.ConfigData

class ConfigRepository(private val configDao: ConfigDao) {
    fun getAllConfigs(): List<ConfigData> {
        return configDao.getAllConfigs()
    }

    suspend fun addConfig(config: ConfigData) {
        configDao.addConfig(config)
    }
    fun getConfig(): ConfigData {
        return configDao.getConfig()
    }

    fun getTrashesInGameState(): Boolean {
        return configDao.getTrashesInGameState()
    }
    fun getDisplayLevelWinInListState(): Boolean {
        return configDao.getDisplayLevelWinListState()
    }
    fun getOrientation(): Int {
        return configDao.getOrientation()
    }

    fun updateTrashesInGameStateTo(state: Boolean) {
        configDao.upDateTrashesInGameStateTo(state)
    }
    fun updateDisplayLevelWinInListStateTo(state: Boolean) {
        configDao.upDateDisplayLevelWinListTo(state)
    }
    fun updateOrientation(state: Int) {
        configDao.updateOrientation(state)
    }
}