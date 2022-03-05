package com.mobilegame.robozzle.Extensions

import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.analyse.verbalLog
import com.mobilegame.robozzle.domain.RobuzzleLevel.FunctionInstructions
import com.mobilegame.robozzle.domain.RobuzzleLevel.Position

fun <T> MutableList<T>.containsNot(element: T): Boolean = !this.contains(element)


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

fun MutableList<Position>.clone(): MutableList<Position> {
    var clonePosList: MutableList<Position> = mutableListOf()
    for (index in 0 until this.size) {
        clonePosList.add(Position(this[index].line, this[index].column))
    }
    return clonePosList
}

fun MutableList<Int>.Contains(int: Int): Boolean {
    var ret = false
    forEachIndexed { index, i ->
        if (i == int) ret = true
    }
    return ret
}

fun <E> MutableList<E>.copy(): MutableList<E> {
    val ret = emptyList<E>().toMutableList()
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