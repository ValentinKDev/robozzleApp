package com.mobilegame.robozzle.Extensions

fun Char.ToInt(): Int { return (this.toString().toInt()) }

fun Char.isDirection(): Boolean {return "[urld]".toRegex().matches(this.toString())}

fun Char.isInstruction(): Boolean {
    return (("[Url]".toRegex().matches(this.toString())) || this.isDigit())
}

fun Char.match(regex: Regex): Boolean {
    return this.toString().matches(regex)
}
