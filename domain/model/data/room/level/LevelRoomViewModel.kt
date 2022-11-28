package com.mobilegame.robozzle.domain.model.data.room.level

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.analyse.prettyPrint
import com.mobilegame.robozzle.analyse.verbalLog
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

    fun clearAllSolutionsSaved() {
        infoLog("LevelRoomVM::clearAllSolutionSaved", "start")
        viewModelScope.launch(Dispatchers.IO) {
            val list: List<LevelData> = repo.getAllLevelsFromRoom()
            val gson = Gson()
            list.forEach { levelData ->
                verbalLog("LevelRoomVM::clearAllSolutionSaved", "level ${levelData.id}")
                val solutionJson = levelData.funInstructionsListJson
                val solution: List<FunctionInstructions> = gson.fromJson(solutionJson, ListFunctionInstructionType)
                prettyPrint("LevelRoomVM::clearAllSolutionSaved", "solutionRoom", solution, Log.INFO)
                val cleanSolution = mutableListOf<FunctionInstructions>()
                solution.forEach { cleanSolution.add(it.reset()) }
                prettyPrint("LevelRoomVM::clearAllSolutionSaved", "cleanSolution", cleanSolution, Log.INFO)
                val cleanSolutionJson = gson.toJson(cleanSolution)
                repo.addLevel(
                    LevelData(
                        id = levelData.id,
                        name = levelData.name,
                        difficulty = levelData.difficulty,
                        mapJson = levelData.mapJson,
                        instructionsMenuJson = levelData.funInstructionsListJson,
                        funInstructionsListJson = cleanSolutionJson,
                        playerInitalJson = levelData.playerInitalJson,
                        starsListJson = levelData.starsListJson
                    )
                )
            }
        }
    }
}


