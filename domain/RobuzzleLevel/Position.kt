package com.mobilegame.robozzle.domain.RobuzzleLevel

data class Position(var line: Int, var column: Int) {
    fun Match(pos: Position): Boolean {
        return (this.line == pos.line && this.column == pos.column)
    }

    fun copy(): Position = Position(this.line, this.column)

//    fun getSafe(int: Int): P

    companion object {
        val Zero: Position = Position(0, 0)
        val Error: Position = Position(-42, -42)
    }
}
