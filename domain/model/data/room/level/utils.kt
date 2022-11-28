package com.mobilegame.robozzle.domain.model.data.room

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mobilegame.robozzle.analyse.prettyPrint
import com.mobilegame.robozzle.data.room.Level.LevelData
import com.mobilegame.robozzle.domain.InGame.Direction
import com.mobilegame.robozzle.domain.InGame.PlayerInGame
import com.mobilegame.robozzle.domain.RobuzzleLevel.FunctionInstructions
import com.mobilegame.robozzle.domain.RobuzzleLevel.Position
import com.mobilegame.robozzle.domain.model.level.Level
import com.mobilegame.robozzle.domain.model.level.LevelOverView
import com.mobilegame.robozzle.domain.model.level.LevelState

private val MutableListStringType = object : TypeToken<MutableList<String>>() {}.type!!

private val ListStringType = object : TypeToken<List<String>>() {}.type!!
val ListFunctionInstructionType = object : TypeToken<List<FunctionInstructions>>() {}.type!!
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

internal fun FunctionInstructions.reset(): FunctionInstructions {
    val instructions: String = this.instructions.map { '.' }.joinToString("")
    val colors: String = this.colors.map { 'g' }.joinToString("")
    return FunctionInstructions(
        instructions = instructions,
        colors = colors,
    )
}

internal fun List<LevelData>.toLevelList(): List<Level> {
    val levelList: MutableList<Level> = mutableListOf()
    this.forEach {
        levelList.add(it.toLevel())
    }
    return levelList
}

internal fun buildLevelOverView(id: Int, difficulty: Int = 0, name: String, mapJson: String, state: LevelState = LevelState.NeverOpened): LevelOverView {
    val gson = Gson()
    return LevelOverView(
        id = id,
        diff = difficulty,
        name = name,
        map = gson.fromJson(mapJson, ListStringType),
        state = state
    )
}

internal fun buildLevelOverViewList(ids: List<Int>, diffs: List<Int> = emptyList(), names: List<String>, mapsJson: List<String>, winList: List<Int>): List<LevelOverView> {
    val mutableList: MutableList<LevelOverView> = mutableListOf()
    ids.forEachIndexed { index, id ->
        mutableList.add(
            element = buildLevelOverView(
                id = id,
                name = names[index],
                mapJson = mapsJson[index],
                state = if (winList.contains(id)) LevelState.Win else LevelState.NeverOpened
            )
        )
    }
    return mutableList.toList()
}

internal fun Level.updateFunctionInstructionListWith(newFunctionInstructionList: List<FunctionInstructions>): LevelData {
    val gson = Gson()
    return LevelData (
        name = this.name,
        id = this.id,
        difficulty = this.difficulty,
        mapJson = gson.toJson(this.map, ListStringType),
        instructionsMenuJson = gson.toJson(this.instructionsMenu, ListFunctionInstructionType),
        funInstructionsListJson = gson.toJson(newFunctionInstructionList, ListFunctionInstructionType),
        playerInitalJson = gson.toJson(this.playerInitial, ListPostionType),
        starsListJson = gson.toJson(this.starsList, ListPostionType)
    )
}

internal fun LevelData.toLevelOverView(): LevelOverView {
    return LevelOverView(id = this.id, diff = this.difficulty, name = this.name, map = Gson().fromJson(this.mapJson, ListStringType))
}