package com.mobilegame.robozzle.domain.InGame

class Direction(var x: Int, var y: Int) {
    fun OppositeOf(i:Int):Int {return (-1 * i)}
    fun OppositeOf_x():Int {return OppositeOf(x)}
    fun OppositeOf_y():Int {return OppositeOf(y)}
    fun ToChar(): Char {
        return when (x) {
            -1 -> 'l'
            1 -> 'r'
            0 -> when (y) {
                1 -> 'u'
                -1 -> 'd'
                else -> '.'
            }
            else -> return '.'
        }
    }
    fun copy(): Direction = Direction(this.x, this.y)
}
