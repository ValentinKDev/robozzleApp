package com.mobilegame.robozzle.domain.model.room

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mobilegame.robozzle.data.base.Level.LevelData
import com.mobilegame.robozzle.data.server.dto.LevelRequest
import com.mobilegame.robozzle.domain.RobuzzleLevel.FunctionInstructions
import com.mobilegame.robozzle.domain.RobuzzleLevel.Position
import com.mobilegame.robozzle.domain.model.level.Level

private val ListStringType = object : TypeToken<List<String>>() {}.type!!
private val ListFunctionInstructionType = object : TypeToken<List<FunctionInstructions>>() {}.type!!
private val ListPostionType = object : TypeToken<List<Position>>() {}.type!!

internal fun List<String>.toLevelDataList(): List<LevelData> {
    val mutableList: MutableList<LevelData> = mutableListOf()
    this.forEach {
        mutableList.add(it.toLevelData())
    }
    return mutableList.toList()
}

internal fun String.toLevelData(): LevelData {
    val gson = Gson()
//    val level: Level = gson.fromJson(this.descriptionJson, Level::class.java)
    val level: Level = gson.fromJson(this, Level::class.java)
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
