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

    fun updateTrashesInGameStateTo(state: Boolean) {
        return configDao.upDateTrashesInGameStateTo(state)
    }

    fun updateDisplayLevelWinInListStateTo(state: Boolean) {
        return configDao.upDateDisplayLevelWinListTo(state)
    }
}