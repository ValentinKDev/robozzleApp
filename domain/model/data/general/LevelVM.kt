package com.mobilegame.robozzle.domain.model.data.general

import android.content.Context
import com.mobilegame.robozzle.domain.RobuzzleLevel.RobuzzleLevel
import com.mobilegame.robozzle.domain.model.data.room.LevelWins.LevelWinRoomViewModel
import com.mobilegame.robozzle.domain.model.data.room.level.LevelRoomViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class LevelVM (
    context: Context
){
    val levelRoomVM = LevelRoomViewModel(context)
    val levelWinRoomVM = LevelWinRoomViewModel(context)

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
}