package com.mobilegame.robozzle.domain.model.data.room.Config

import android.content.Context
import androidx.lifecycle.ViewModel
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.data.room.Config.ConfigDao
import com.mobilegame.robozzle.data.room.Config.ConfigData
import com.mobilegame.robozzle.data.room.Config.ConfigDataBase
import com.mobilegame.robozzle.domain.repository.ConfigRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class ConfigRoomViewModel(context: Context): ViewModel() {
    private val dao: ConfigDao = ConfigDataBase.getInstance(context).configDao()
    private val repo: ConfigRepository = ConfigRepository(dao)

    fun getConfigData(): ConfigData = runBlocking(Dispatchers.IO) {
        repo.getConfig()
    }

    fun getTrashesInGameState(): Boolean = runBlocking(Dispatchers.IO) {
        repo.getTrashesInGameState()
    }

    fun getDisplayLevelWinInListState(): Boolean = runBlocking(Dispatchers.IO) {
        repo.getDisplayLevelWinInListState()
    }

    fun updateTrashesInGameStateTo(state: Boolean) = runBlocking(Dispatchers.IO) {
        repo.updateTrashesInGameStateTo(state)
    }

    fun updateDisplayLevelWinInListStateTo(state: Boolean) = runBlocking(Dispatchers.IO) {
        repo.updateDisplayLevelWinInListStateTo(state)
    }

    fun initiateConfig() = runBlocking(Dispatchers.IO) {
        if (repo.getAllConfigs().isEmpty()) {
            errorLog("ConfigRoomViewModel::initiateConfig", "get all config is empty")
            repo.addConfig(fistConfig)
        }
    }
}