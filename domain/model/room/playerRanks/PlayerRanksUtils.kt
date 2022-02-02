package com.mobilegame.robozzle.domain.model.room.level

import com.google.gson.Gson
import com.mobilegame.robozzle.data.base.Level.LevelData
import com.mobilegame.robozzle.data.base.PlayerRanks.LevelResolvedData
import com.mobilegame.robozzle.domain.LevelResolved.LevelResolved
import com.mobilegame.robozzle.domain.LevelResolved.WinDetails
import com.mobilegame.robozzle.domain.RobuzzleLevel.RobuzzleLevel

internal fun List<LevelResolvedData>.toLevelResolvedType(): List<LevelResolved> {
    val list: MutableList<LevelResolved> = mutableListOf()
    this.forEachIndexed { index, data ->
        list.add(
//            index = index,
            element = data.toLevelResolvedType()
        )
    }
    return list.toList()
}

internal fun LevelResolvedData.toLevelResolvedType(): LevelResolved {
    val winDetails: WinDetails = Gson().fromJson(this.winDetailsJson, WinDetails::class.java)
    return LevelResolved(
//        lvl_id = this.idLevel,
        lvl_id = this.id,
        points = this.points,
        details = winDetails
    )
}

internal fun List<LevelResolved>.toLevelResolvedDataList(): List<LevelResolvedData> {
    val list: MutableList<LevelResolvedData> = mutableListOf()
    this.forEach {
        list.add(it.toLevelResolvedData())
    }
    return list.toList()
}

internal fun LevelResolved.toLevelResolvedData(): LevelResolvedData {
    val gson = Gson()
    return LevelResolvedData(
        id = this.lvl_id,
//        idLevel = this.,
        points = this.points,
        winDetailsJson = gson.toJson(this.details),
    )
}

//internal fun LevelData.toRobuzzleLevel(): RobuzzleLevel {
