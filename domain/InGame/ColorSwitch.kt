package com.mobilegame.robozzle.domain.InGame

import com.mobilegame.robozzle.domain.RobuzzleLevel.Position

data class ColorSwitch(
    val oldColor: Char,
    val newColor: Char,
    val pos: Position
)