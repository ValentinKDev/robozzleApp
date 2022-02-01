package com.mobilegame.robozzle.domain.model.room.level

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilegame.robozzle.data.base.Level.LevelData
import com.mobilegame.robozzle.data.base.Level.LevelDao
import com.mobilegame.robozzle.data.base.Level.LevelDataBase
import com.mobilegame.robozzle.data.remote.dto.LevelRequest
import com.mobilegame.robozzle.domain.model.level.Level
import com.mobilegame.robozzle.domain.model.room.toLevel
import com.mobilegame.robozzle.domain.model.room.toLevelData
import com.mobilegame.robozzle.domain.model.room.toLevelDataList
import com.mobilegame.robozzle.domain.model.room.toLevelList
import com.mobilegame.robozzle.domain.repository.LevelRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@InternalCoroutinesApi
class LevelRoomViewModel(context: Context): ViewModel() {
    private val dao: LevelDao = LevelDataBase.getInstance(context).levelDao()
    private val repo: LevelRepository = LevelRepository(dao)

    fun getLevel(id: Int): Level? = runBlocking(Dispatchers.IO) {
        var level: Level? = null
        repo.getALevel(id)?.let {
            level = it.toLevel()
        }
        level
    }

    fun getAllLevels(): List<Level> = runBlocking(Dispatchers.IO) {
        val listLevelData: List<LevelData> = repo.getAllLevelsFromRoom()
        val listLevel = listLevelData.toLevelList()
        listLevel
    }

    fun addLevel(levelRequest: LevelRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.addLevel(levelRequest.toLevelData())
        }
    }

    fun addLevels(list: List<LevelRequest>) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.addLevelsData(list.toLevelDataList())
        }
    }

    fun getLevelIds(): List<Int> = runBlocking {
        val levelIds: List<Int> = repo.getAllLevelsIds()
        levelIds
    }
}


