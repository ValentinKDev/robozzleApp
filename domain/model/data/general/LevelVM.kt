package com.mobilegame.robozzle.domain.model.data.general

import android.content.Context
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.RobuzzleLevel.RobuzzleLevel
import com.mobilegame.robozzle.domain.model.data.room.LevelWins.LevelWinRoomViewModel
import com.mobilegame.robozzle.domain.model.data.room.buildLevelOverViewList
import com.mobilegame.robozzle.domain.model.data.room.level.LevelRoomViewModel
import com.mobilegame.robozzle.domain.model.data.store.ArgumentsDataStoreViewModel
import com.mobilegame.robozzle.domain.model.level.Level
import com.mobilegame.robozzle.domain.model.level.LevelOverView
import com.mobilegame.robozzle.presentation.ui.utils.MapCleaner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class LevelVM (
    context: Context
){
    val levelRoomVM = LevelRoomViewModel(context)
    val levelWinRoomVM = LevelWinRoomViewModel(context)
    val argumentLevelDataStore = ArgumentsDataStoreViewModel(context)

    fun getLevelArgument(): Level = runBlocking(Dispatchers.IO) {
        val cleaner = MapCleaner()
        val id = argumentLevelDataStore.getLevelNumberArg()
        val lvl = levelRoomVM.getLevel(id)!!
        infoLog("getLevelargument()", "${lvl.playerInitial[0]}")
        lvl
    }

    fun getLevel(id: Int): Level? = runBlocking(Dispatchers.IO) {
        val level: Level? = levelRoomVM.getLevel(id)
        level?.let {
            //todo: what is this ???
            levelWinRoomVM.getALevelWinSolution(id)?.let { _solutionFunctionList ->
                var same = true
                _solutionFunctionList.forEachIndexed { _index, _solutionFunction ->
                    if (_solutionFunction.instructions.length != level.funInstructionsList[_index].instructions.length)
                        same = false
                }
                if (same) level.funInstructionsList = _solutionFunctionList.toMutableList()
            }
            level
        }
    }
    fun getRobuzzleLevel(id: Int): RobuzzleLevel = runBlocking(Dispatchers.IO) {
        val robuzzleLevel: RobuzzleLevel = levelRoomVM.getRobuzzle(id)!!
        levelWinRoomVM.getALevelWinSolution(id)?.let { _solutionFunctionList ->
            var same = true
            _solutionFunctionList.forEachIndexed { _index, _solutionFunction ->
                if (_solutionFunction.instructions.length != robuzzleLevel.funInstructionsList[_index].instructions.length)
                    same = false
            }
            if (same) robuzzleLevel.funInstructionsList = _solutionFunctionList.toMutableList()
        }
        robuzzleLevel
    }

    fun getAllLevelOverViewFromDifficulty(diff: Int): List<LevelOverView> = runBlocking(Dispatchers.IO) {
        val listIdByDifficulty: List<Int> = levelRoomVM.getIdByDifficulty(diff)
        val listName: List<String> = levelRoomVM.getNamesByDifficulty(diff)
        val listMapJson: List<String> = levelRoomVM.getMapJsonByDifficulty(diff)

        var listWin: List<Int> = levelWinRoomVM.getAllWinIdsInList()

        listWin = listIdByDifficulty.filter { listWin.contains(it) }
        buildLevelOverViewList(ids = listIdByDifficulty, names = listName, mapsJson = listMapJson, winList = listWin)
    }
}