package com.mobilegame.robozzle.utils.Extensions

import android.util.Range


//fun <U : Comparable<U>?> Range<U>.containNot(element: U): Boolean = this.contains(element)
infix fun IntRange.containsNot(element: Int): Boolean = !this.contains(element)