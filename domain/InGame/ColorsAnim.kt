package com.mobilegame.robozzle.domain.InGame

import com.mobilegame.robozzle.domain.RobuzzleLevel.Position

data class ColorsAnim (
    var toRemoveMap: MutableMap<Int, MutableMap<Char, Position>> = emptyMap<Int, MutableMap<Char, Position>>().toMutableMap(),
    var removedMap: MutableMap<Int, MutableMap<Char, Position>> = emptyMap<Int, MutableMap<Char, Position>>().toMutableMap()
)