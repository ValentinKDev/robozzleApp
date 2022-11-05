package com.mobilegame.robozzle.domain.model.data.room.LevelWins

import com.google.gson.Gson
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.data.room.levelWins.LevelWinData
import com.mobilegame.robozzle.domain.Player.LevelWin
import com.mobilegame.robozzle.domain.RobuzzleLevel.FunctionInstructions
import com.mobilegame.robozzle.domain.WinDetails.WinDetails

internal fun String.getSolution(): List<FunctionInstructions> {
    val winDetails: WinDetails = Gson().fromJson(this, WinDetails::class.java)
    return winDetails.solutionFound
}

internal fun LevelWin.toLevelWinData(): LevelWinData = LevelWinData(
    levelId = this.lvl_id,
    levelName = this.lvl_name,
    points = this.points,
    winDetailsJson = Gson().toJson(this.details, WinDetails::class.java)
)

internal fun List<LevelWinData>.toLevelWinList(): List<LevelWin> {
//    errorLog("toLevelWinList", "check")
    val mutableList: MutableList<LevelWin> = mutableListOf()
    this.forEach {
        errorLog("windetailJson", "${it.winDetailsJson}")
        val windetails: WinDetails = Gson().fromJson(it.winDetailsJson, WinDetails::class.java)
        mutableList.add(
            LevelWin(
                lvl_id = it.levelId,
                lvl_name = it.levelName,
                points = it.points,
                details = windetails
            )
        )
    }
    return mutableList.toList()
}
