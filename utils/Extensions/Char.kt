package com.mobilegame.robozzle.utils.Extensions

import com.mobilegame.robozzle.presentation.ui.elements.CaseColor

fun Char.ToInt(): Int { return (this.toString().toInt()) }

fun Char.isDirection(): Boolean {return "[urld]".toRegex().matches(this.toString())}

fun Char.isInstruction(): Boolean {
    return (("[Url]".toRegex().matches(this.toString())) || this.isDigit())
}

fun Char.match(regex: Regex): Boolean {
    return this.toString().matches(regex)
}

fun Char.toCaseColor(): CaseColor {
    return when (this) {
        'R' -> CaseColor.Red
        'G' -> CaseColor.Green
        'B' -> CaseColor.Blue
        else -> CaseColor.None
    }
}