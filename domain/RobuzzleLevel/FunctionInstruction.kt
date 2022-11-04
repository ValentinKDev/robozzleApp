package com.mobilegame.robozzle.domain.RobuzzleLevel

import androidx.datastore.preferences.protobuf.Empty

data class FunctionInstruction (
    val instruction: Char,
    val color: Char,
) {
    companion object {
        val empty = FunctionInstruction('.','g')
    }
}

fun FunctionInstruction.isDelete(): Boolean = this.instruction == 'x'
