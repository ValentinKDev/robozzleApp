package com.mobilegame.robozzle.domain.RobuzzleLevel

data class FunctionInstruction (
    val instruction: Char,
    val color: Char,
)

fun FunctionInstruction.isDelete(): Boolean = this.instruction == 'x'
