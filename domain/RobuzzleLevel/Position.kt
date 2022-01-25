package com.mobilegame.robozzle.domain.RobuzzleLevel

data class Position(var line: Int, var column: Int) {
    fun Match(pos: Position): Boolean {
        return (line == pos.line && column == pos.column)
    }
}
