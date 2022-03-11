package com.mobilegame.robozzle.utils.Extensions

import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.analyse.verbalLog
import com.mobilegame.robozzle.domain.InGame.Direction
import com.mobilegame.robozzle.domain.InGame.PlayerInGame
import com.mobilegame.robozzle.domain.RobuzzleLevel.FunctionInstructions
import com.mobilegame.robozzle.domain.RobuzzleLevel.Position
import java.lang.IndexOutOfBoundsException

fun <T> MutableList<T>.containsNot(element: T): Boolean = !this.contains(element)

//fun List<String>.countCha(exclude: Char? = null): Int {
//    val number = 0
//    this.toMutableList()
//    return number
//}

fun List<FunctionInstructions>.countInstructions(): Int {
    var number: Int = 0

    this.forEach {
        number += it.instructions.replace("[.]".toRegex(), "").length
    }
    return number
}

fun MutableList<String>.replaceInMatrice(replace: Char, position: Position): MutableList<String> {
    val newMatrice = this.copy()
    //todo : add a try catch
    this[position.line].let {
        this[position.column].let {
            newMatrice[position.line] = this[position.line].replaceAt(position.column, replace)
            return newMatrice
        }
    }
    return newMatrice
}

fun MutableList<String>.switchInMatrice(position1: Position, c1: Char, position2: Position, c2: Char): MutableList<String> {
    var newMatrice = this.copy()
    return try {
        newMatrice = this.replaceInMatrice(c1, position2)
        newMatrice = newMatrice.replaceInMatrice(c2, position1)
        newMatrice
    } catch (e: IndexOutOfBoundsException) {
        errorLog("ERROR", "e:")
        this
    }
}

fun List<Position>.toPlayerInGame(): PlayerInGame {
    return if (this.size == 2) {
        PlayerInGame(this[0], Direction(this[1].line, this[1].column))
    } else
        PlayerInGame(Position(-42, -42), Direction(-42, -42))
}

fun MutableList<Position>.Contains(pos: Position): Boolean{
    var ret = false
    forEachIndexed { index, position ->
        if (position.Match(pos)) ret = true
    }
    return ret
}

fun MutableList<Position>.IndexMatching(pos: Position): Int {
    errorLog("index matching", "${pos}")
    
    for (index in 0..this.size) {
        verbalLog("", "${index} - (${this[index].line}, ${this[index].column})")
        if (pos.Match(this[index])) {
            verbalLog("", "ret")
            return index
        }}
    return -1
}

fun <T>List<T>.clone(): List<T> {
    val mutableList: MutableList<T> = mutableListOf()
    this.forEach { mutableList.add(it) }
    return mutableList.toList()
}

//fun MutableList<Position>.clone(): MutableList<Position> {
//    var clonePosList: MutableList<Position> = mutableListOf()
//    for (index in 0 until this.size) {
//        clonePosList.add(Position(this[index].line, this[index].column))
//    }
//    return clonePosList
//}

fun MutableList<Int>.Contains(int: Int): Boolean {
    var ret = false
    forEachIndexed { index, i ->
        if (i == int) ret = true
    }
    return ret
}

fun <E> MutableList<E>.copy(): MutableList<E> {
    val ret = mutableListOf<E>()
    this.forEach {
        ret.add(it)
    }
    return ret
}

fun MutableList<FunctionInstructions>.countInstruction(): Int {
    var count = 0
    this.forEach{ function ->
        for (i in 0 until function.instructions.length) {
            if (function.instructions[i].match("[UrlRGB0123456798]".toRegex()))
                count += 1
        }
    }
    infoLog("number of instruction for this win", "$count")
    return count
}

//fun MutableList<FunctionInstructions>.countInstructions(): Int {
//    Print_FunInstructionList(this)
//    var count: Int = 0
//    for (functionNum in 0 until this.size) {
//        for (caseIndex in this[functionNum].instructions.indices) {
//            if (this[functionNum].instructions[caseIndex].isInstruction()) count += 1
//        }
//    }
//    return count
//}