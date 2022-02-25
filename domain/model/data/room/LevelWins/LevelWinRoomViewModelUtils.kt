package com.mobilegame.robozzle.domain.model.data.room.LevelWins

import com.google.gson.Gson
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.data.room.levelWins.LevelWinData
import com.mobilegame.robozzle.domain.Player.LevelWin
import com.mobilegame.robozzle.domain.RobuzzleLevel.FunctionInstructions
import com.mobilegame.robozzle.domain.WinDetails.WinDetails

internal fun String.getSolution(): List<FunctionInstructions> {
    val winDetails: WinDetails = Gson().fromJson(this, WinDetails::class.java)
    return winDetails.solutionFound
}

internal fun LevelWin.toLevelWinData(): LevelWinData = LevelWinData(
    levelId = this.levelId,
    levelName = this.levelName,
    points = this.points,
    winDetailsJson = Gson().toJson(this.winDetails, WinDetails::class.java)
)

internal fun List<LevelWinData>.toLevelWinList(): List<LevelWin> {
//    errorLog("toLevelWinList", "check")
    val mutableList: MutableList<LevelWin> = mutableListOf()
    this.forEach {
//        errorLog("windetailJson", "${it.winDetailsJson}")
        val windetails: WinDetails = Gson().fromJson(it.winDetailsJson, WinDetails::class.java)
        mutableList.add(
            LevelWin(
                levelId = it.levelId,
                levelName = it.levelName,
                points = it.points,
                winDetails = windetails
            )
        )
    }
    return mutableList.toList()
}
