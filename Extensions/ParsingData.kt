package com.mobilegame.robozzle.Extensions

import android.util.Log
import androidx.compose.runtime.State
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.data.base.Level.LevelData
import com.mobilegame.robozzle.domain.InGame.Direction
import com.mobilegame.robozzle.domain.InGame.PlayerInGame
import com.mobilegame.robozzle.domain.RobuzzleLevel.FunctionInstructions
import com.mobilegame.robozzle.domain.RobuzzleLevel.Position
import com.mobilegame.robozzle.data.remote.dto.LevelRequest
import com.mobilegame.robozzle.domain.RobuzzleLevel.RobuzzleLevel


fun State<List<LevelRequest>>.parseToRobuzzleLevelList(): List<RobuzzleLevel> {
    Log.e("LevelRequestListToRobuzzleLevels", "Start")
    var robuzzleLevelList: MutableList<RobuzzleLevel> = emptyList<RobuzzleLevel>().toMutableList()

    this.value.reversed().forEach {
//    this.value.forEach {
        Log.v(it.id,"lvl added")
        val robuzzleLevel = RobuzzleLevel(
            name = it.name,
            id = it.id.toInt(),
            difficulty = it.difficulty.toInt(),
            map = it.map,
            instructionsMenu = it.instructionsMenu.parseToFunctionInstructionMenuMutableList(),
            funInstructionsList = it.funInstructionsList.parseToFunctionInstructionMutableList(),
            playerInitial = it.playerInitial.parseToPlayerInGame(),
            starsList = it.starsList.parseToPositionMutableList(),
        )
        robuzzleLevelList.add(robuzzleLevel)
    }
    return robuzzleLevelList.toList()
}

private fun List<List<String>>.parseToPositionMutableList(): MutableList<Position> {
    val ret = mutableListOf<Position>()
    this.forEach {
        ret.add(Position(line = it[0].toInt(), column = it[1].toInt()))
    }
    return ret
}

private fun List<List<String>>.parseToPlayerInGame(): PlayerInGame {
    return ( PlayerInGame(
        Position(line = this[0][0].toInt(), column = this[0][1].toInt()),
        Direction(x = this[1][0].toInt(), y = this[1][1].toInt()) )
            )
}

private fun List<List<String>>.parseToFunctionInstructionMenuMutableList(): MutableList<FunctionInstructions> {
    val ret = mutableListOf<FunctionInstructions>()
    for (i in 0 until this.size){
        ret.add(FunctionInstructions(instructions = this[i][0], colors = this[i][1]))
    }
    return ret
}

private fun List<List<String>>.parseToFunctionInstructionMutableList(): MutableList<FunctionInstructions> {
    val ret = mutableListOf<FunctionInstructions>()
    for (i in 0 until this.size){
        ret.add(FunctionInstructions(instructions = this[i][0]))
    }
    return ret
}

fun State<List<LevelRequest>>.convertToLevelList(): List<LevelData> {
    var ret = emptyList<LevelData>().toMutableList()
    this.value.forEachIndexed { index, levelRequest ->
        ret.add(levelRequest.convertToLevel())
    }
    return ret
}

fun LevelRequest.convertToLevel(): LevelData {
    if (this.id.toInt() == 8) {
        errorLog("ID 8", this.funInstructionsList.toStr())
    }
    else {
        errorLog("ID ${this.id.toInt()}", "else")}
//    Log.e("Level", "name ${this.name}")
//    this.map.forEach { Log.v("","$it") }
//    Log.e("playerInitial", "${this.playerInitial}")
//    Log.e("playerInitialSTR", "${this.playerInitial.toStr()}")
    return LevelData(
        id = this.id.toInt(),
        name = this.name,
        difficulty = this.difficulty.toInt(),
        instructionsMenuStr = this.instructionsMenu.toStr(),
        funInstructionsListStr = this.funInstructionsList.toStr(),
        playerInitalStr = this.playerInitial.toStr(),
        starsListStr = this.starsList.toStr(),
        mapStr = this.map.toString()
//        id = id.toInt(),
//        name = name,
//        difficulty = difficulty.toInt(),
//        instructionsMenuStr = instructionsMenu.toStr(),
//        funInstructionsListStr = funInstructionsList.toStr(),
//        playerInitalStr = playerInitial.toStr(),
//        starsListStr = starsList.toStr(),
//        mapStr = map.toString()
    )
}

private fun List<List<String>>.toStr(): String {
    var ret = "[ "
    val maxClassIndex = this.size - 1
    this.forEachIndexed { index, list ->
        val maxStrIndex = list.size - 1
        ret += "[ "
        list.forEachIndexed { index, string ->
            ret += "\"" + string + "\""
            if ( index < maxStrIndex) ret += ", "
        }
        ret += "]"
        if ( index < maxClassIndex) ret += ","
        ret += " "
    }
    ret += "]"
    return ret
//    var ret = "["
//    this.forEachIndexed { _, list ->
//        ret += "[ "
//        list.forEachIndexed { index, string ->
//            ret += "[ "
//            ret += "\"" + string + "\""
//            ret += ", "
//            ret += "\"" + string + "\""
//            ret += "] "
//        }
//        ret += "] "
//    }
//    ret += "]"
//    return ret
}

//fun List<Level>.toRobuzzleLevelList(): List<RobuzzleLevel> {
//    val ret: MutableList<RobuzzleLevel> = emptyList<RobuzzleLevel>().toMutableList()
//    this.forEach {
//        ret.add(it.toRobuzzleLevel())
//    }
//    return ret
//}

fun List<LevelData>.toRobuzzleLevelList(): List<RobuzzleLevel> {
    var list = mutableListOf<RobuzzleLevel>()
    this.forEach {
        list.add(it.toRobuzzleLevel())
    }
//    list.forEach { Print_rb_Level(it) }
    return list
}

fun LevelData.toRobuzzleLevel(): RobuzzleLevel {
    var ret: RobuzzleLevel
//    Log.e("Level ${this.id}", "name ${this.name}")
//    this.mapStr.toMap().forEach { Log.v("","$it") }
    ret = RobuzzleLevel(
        name = this.name,
        id = this.id.toInt(),
        difficulty = this.difficulty.toInt(),
        map = this.mapStr.toMap(),
        instructionsMenu = this.instructionsMenuStr.toMutableListOfFunctionInstructions(),
        funInstructionsList = this.funInstructionsListStr.toMutableListOfFunctionInstructions(),
        playerInitial = this.playerInitalStr.toPlayerInitial(),
        starsList = this.starsListStr.toMutableListOfPostions(),
    )
    return ret
}
fun String.toMap(): List<String> {
    var map = this
        .split(",", "]", " ", "[")
    map = map.filterNot { it == "" }
    return map
}

fun String.toMutableListOfPostions(): MutableList<Position> {
    val ret = mutableListOf<Position>()
    val listPostionStr = this.toListofClassStr()

    listPostionStr.forEach { ret.add(it.toPostion()) }
//    println("$listPostionStr")
    return ret
}

fun String.toMutableListOfFunctionInstructions(): MutableList<FunctionInstructions> {
    val ret = mutableListOf<FunctionInstructions>()
    val listStr = this.toListofClassStr()
    listStr.forEach {
        val split = it.split(",")
//        println("$split")
        ret.add(FunctionInstructions(split[0], if (split.size == 2) split[1] else split[0]))
    }
    return ret
}

fun String.toPlayerInitial(): PlayerInGame {
    val listStr = this.toListofClassStr()
    return PlayerInGame(
        pos = listStr[0].toPostion(),
        direction = listStr[1].toDirection()
    )
}

fun String.toDirection(): Direction {
    val split = this.split(",")
    return Direction(split[0].toInt(), split[1].toInt())
}

//todo: protect from misUse by checking input ?
fun String.toPostion(): Position {
    val split = this.split(",")
    return Position(split[0].toInt(), split[1].toInt())
}

fun String.toListofClassStr(): List<String> {
    var ret = this
        .replace("],", "")
        .replace("[]\" ]".toRegex(), "")
        .split("[")
    ret = ret.filterNot { it == "" }
    return ret
}


//fun RobuzzleLevel.parseToLevel(): Level {
//    var ret = Level(0, "0", 0, "0", "0", "0","0", "0")
//    val id = this.id.toInt()
//    val name = this.name
//    val diff = this.difficulty
//    var instructionsMenuStr = this.instructionsMenu.toList().funInsturctionstoStr()
//    var functionInstructionsStr = this.funInstructionsList.toList().funInsturctionstoStr()
//    var playerInitialStr = this.playerInital.toStr()
//    var starListStr = this.starsList.toList().postionsToStr()
//    val mapStr = this.map.toStr()
//
//    ret = Level(
//        id = id,
//        name = name,
//        difficulty = diff,
//        instructionsMenu = instructionsMenuStr,
//        funInstructionsList = functionInstructionsStr,
//        playerInital = playerInitialStr,
//        starsList = starListStr,
//        mapStr = mapStr
//    )
//    return ret
//}

//private fun List<String>.toStr(): String {
//    var ret = "["
//    this.forEachIndexed{ index, line ->
//        ret += "[ "
//        ret += "\"" + line +"\""
//        ret += "] "
//    }
//    ret += "]"
//    return ret
//}

private fun List<Position>.postionsToStr(): String {
    var ret = "["
    this.forEachIndexed { _, position ->
        ret += "[ "
        ret += "\"" + position.line.toString() + "\""
        ret += ", "
        ret += "\"" + position.column.toString() + "\""
        ret += "] "
    }
    ret += "]"
    return ret
}

private fun PlayerInGame.toStr(): String {
    val ret = "[ " + "[ " + "\"" + this.direction.x + "\""  + ", " + "\"" + this.direction.y + "\"" + "] " + ", " + "[ " + "[ " + "\"" + this.pos.line + "\"" + ", " + "\"" + this.pos.column + "\"" + "] " + "]"
    return ret
}


fun List<FunctionInstructions>.funInsturctionstoStr(): String {
    var ret = "[ "
    this.mapIndexed { index, functionInstructions ->  }
    this.forEachIndexed { _, functionInstructions ->
        ret += "[ "
        ret += functionInstructions.instructions
        ret += ", "
        ret += functionInstructions.colors
        ret += "] "
    }
    ret += "]"
    return ret
}
