package com.mobilegame.robozzle.utils.Extensions

fun Boolean.toInt(): Int = if (this) 1 else 0

infix fun Boolean.Is(toCompare: Boolean) = this == toCompare

infix fun Boolean.Not(toCompare: Boolean) = this != toCompare
