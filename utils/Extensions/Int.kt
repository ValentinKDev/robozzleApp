package com.mobilegame.robozzle.utils.Extensions

infix fun Int.Not(compare: Int): Boolean = this != compare
infix fun Int.Is(compare: Int): Boolean = this == compare
fun Int.IsPair(): Boolean = this % 2 == 0
