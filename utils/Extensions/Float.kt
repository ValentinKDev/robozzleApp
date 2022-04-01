package com.mobilegame.robozzle.utils.Extensions

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.runBlocking

inline fun Float.toDp(density: Float): Dp = (this / density).dp

inline fun getSmallerFloat(f1: Float, f2: Float): Float = if (f1 < f2) f1 else f2

//inline fun Float.isNotZero(): Boolean? = runBlocking { if (this.equals(0F)) null else false }