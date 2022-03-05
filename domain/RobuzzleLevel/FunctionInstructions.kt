package com.mobilegame.robozzle.domain.RobuzzleLevel

import kotlinx.serialization.Serializable

@Serializable
data class FunctionInstructions(
    var instructions: String,
//    var colors: String = instructions.replace("[a-zA-Z0-9.*]".toRegex(), "g")
    var colors: String
) {
//    fun String.replace(index: Int, charStr: String) {
//        this = this.replace().toString()
//    }
}
