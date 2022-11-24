package com.mobilegame.robozzle.domain.model.data.general

import android.content.Context
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.RobuzzleLevel.FunctionInstructions
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

    fun getAllLevelOverViewFromDifficulty(diff: Int, displayLevelWin: Boolean): List<LevelOverView> = runBlocking(Dispatchers.IO) {
        var listIdByDifficulty: List<Int> = levelRoomVM.getIdByDifficulty(diff)
        val listName: List<String> = levelRoomVM.getNamesByDifficulty(diff)
        val listMapJson: List<String> = levelRoomVM.getMapJsonByDifficulty(diff)

        var listWin: List<Int> = levelWinRoomVM.getAllWinIdsInList()

        listWin = listIdByDifficulty.filter { listWin.contains(it) }
        if (!displayLevelWin) listIdByDifficulty = listIdByDifficulty.filterNot { listWin.contains(it) }
        buildLevelOverViewList(ids = listIdByDifficulty, names = listName, mapsJson = listMapJson, winList = listWin)
    }

//    fun getAllLevelOverViewFromDifficulty(diff: Int, displayLevelWin: Boolean): List<LevelOverView> = runBlocking(Dispatchers.IO) {
//        var listIdByDifficulty: List<Int> = levelRoomVM.getIdByDifficulty(diff)
//        val listName: List<String> = levelRoomVM.getNamesByDifficulty(diff)
//        val listMapJson: List<String> = levelRoomVM.getMapJsonByDifficulty(diff)
//
//        var listWin: List<Int> = levelWinRoomVM.getAllWinIdsInList()
//
//        listWin = listIdByDifficulty.filter { listWin.contains(it) }
//        if (!displayLevelWin) listIdByDifficulty = listIdByDifficulty.filterNot { listWin.contains(it) }
//        buildLevelOverViewList(ids = listIdByDifficulty, names = listName, mapsJson = listMapJson, winList = listWin)
//    }

    fun saveFunctionsInstructions(level: Level, newFunciontInstructionList: List<FunctionInstructions>) {
        levelRoomVM.updateFunctionsInstructions(level, newFunciontInstructionList)
    }
}