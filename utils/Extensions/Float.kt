package com.mobilegame.robozzle.utils.Extensions

inline fun Float.toDp(density: Float): Int = (this / density).toInt()