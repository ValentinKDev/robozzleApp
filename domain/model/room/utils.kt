package com.mobilegame.robozzle.domain.model.room

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mobilegame.robozzle.data.base.Level.LevelData
import com.mobilegame.robozzle.data.base.PlayerRanks.LevelResolvedData
import com.mobilegame.robozzle.data.remote.dto.LevelRequest
import com.mobilegame.robozzle.domain.LevelResolved.LevelResolved
import com.mobilegame.robozzle.domain.LevelResolved.WinDetails
import com.mobilegame.robozzle.domain.RobuzzleLevel.FunctionInstructions
import com.mobilegame.robozzle.domain.RobuzzleLevel.Position
import com.mobilegame.robozzle.domain.model.level.Level

private val ListStringType = object : TypeToken<List<String>>() {}.type!!
private val ListFunctionInstructionType = object : TypeToken<List<FunctionInstructions>>() {}.type!!
private val ListPostionType = object : TypeToken<List<Position>>() {}.type!!

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
private fun LevelRequest.toLevelData(): LevelData {
    val gson = Gson()
    val level: Level = gson.fromJson(this.descriptionJson, Level::class.java)
    return LevelData(
        id = level.id,
        name= level.name,
        difficulty = level.difficulty,
        mapJson = gson.toJson(level.map),
        instructionsMenuJson = gson.toJson(level.instructionsMenu),
        funInstructionsListJson = gson.toJson(level.funInstructionsList),
        playerInitalJson = gson.toJson(level.playerInitial),
        starsListJson = gson.toJson(level.starsList),
    )
}

internal fun LevelData.toLevel(): Level {
    val gson = Gson()
    return Level(
        id = this.id,
        name = this.name,
        difficulty = this.difficulty,
        map = gson.fromJson(this.mapJson, ListStringType),
        instructionsMenu = gson.fromJson(this.instructionsMenuJson, ListFunctionInstructionType),
        funInstructionsList = gson.fromJson(this.funInstructionsListJson, ListFunctionInstructionType),
        playerInitial = gson.fromJson(this.playerInitalJson, ListPostionType),
        starsList = gson.fromJson(this.starsListJson, ListPostionType)
    )
}

internal fun List<LevelData>.toLevelList(): List<Level> {
    val levelList: MutableList<Level> = mutableListOf()
    this.forEach {
        levelList.add(it.toLevel())
    }
    return levelList
}
