package com.mobilegame.robozzle.domain.model.data.room.LevelWins

import com.google.gson.Gson
import com.mobilegame.robozzle.data.room.levelWins.LevelWinData
import com.mobilegame.robozzle.domain.Player.LevelWin
import com.mobilegame.robozzle.domain.WinDetails.WinDetails

internal fun LevelWin.toLevelWinData(): LevelWinData = LevelWinData(
    levelId = this.levelId,
    levelName = this.levelName,
    points = this.points,
    winDetailsJson = Gson().toJson(this.winDetails, WinDetails::class.java)
)