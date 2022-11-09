package com.mobilegame.robozzle.domain.model.data.room.level

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilegame.robozzle.data.room.Level.LevelData
import com.mobilegame.robozzle.data.room.Level.LevelDao
import com.mobilegame.robozzle.data.room.Level.LevelDataBase
import com.mobilegame.robozzle.domain.Player.LevelWin
import com.mobilegame.robozzle.domain.RobuzzleLevel.FunctionInstructions
import com.mobilegame.robozzle.domain.model.level.Level
import com.mobilegame.robozzle.domain.model.level.LevelOverView
import com.mobilegame.robozzle.domain.model.data.room.*
import com.mobilegame.robozzle.domain.model.data.room.toLevel
import com.mobilegame.robozzle.domain.model.data.room.toLevelData
import com.mobilegame.robozzle.domain.model.data.room.toLevelDataList
import com.mobilegame.robozzle.domain.model.data.room.toLevelList
import com.mobilegame.robozzle.domain.repository.LevelRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

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

//    fun getRobuzzle(id: Int): RobuzzleLevel? = runBlocking(Dispatchers.IO) {
//        val robuzzleLevel = repo.getALevel(id)?.toRobuzzleLevel()
//        robuzzleLevel
//    }

    fun getAllLevels(): List<Level> = runBlocking(Dispatchers.IO) {
        val listLevelData: List<LevelData> = repo.getAllLevelsFromRoom()
        val listLevel = listLevelData.toLevelList()
        listLevel
    }

    fun addLevel(levelRequest: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.addLevel(levelRequest.toLevelData())
        }
    }

    fun addLevels(list: List<String>) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.addLevelsData(list.toLevelDataList())
        }
    }

    fun getLevelIds(): List<Int> = runBlocking(Dispatchers.IO) {
        val levelIds: List<Int> = repo.getAllLevelsIds()
        levelIds
    }

    fun getIdByDifficulty(difficutly: Int) = runBlocking(Dispatchers.IO) {
        repo.getIdByDifficulty(difficutly)
    }
    fun getNamesByDifficulty(diff: Int) = runBlocking(Dispatchers.IO) {
        repo.getNamesByDifficulty(diff)
    }
    fun getMapJsonByDifficulty(difficutly: Int) = runBlocking(Dispatchers.IO) {
        repo.getMapJsonByDifficulty(difficutly)
    }

    fun getLevelOverViewInList(listWin: List<LevelWin>): List<LevelOverView> = runBlocking(Dispatchers.IO) {
        val mutableList: MutableList<LevelOverView> = mutableListOf()
        listWin.forEach { win ->
            repo.getALevel(win.lvl_id)?.let { levelData ->
                mutableList.add(levelData.toLevelOverView())
            }
        }
        mutableList.toList()
    }

    fun updateFunctionsInstructions(level: Level, newFunctionInstructionsList: List<FunctionInstructions>) = viewModelScope.launch(Dispatchers.IO) {
        repo.addLevel(level.updateFunctionInstructionListWith(newFunctionInstructionsList))
    }
}


