package com.mobilegame.robozzle.domain.RobuzzleLevel

data class FunctionInstructions(
    var instructions: String,
    var colors: String = instructions.replace("[a-zA-Z0-9.*]".toRegex(), "g")
)

