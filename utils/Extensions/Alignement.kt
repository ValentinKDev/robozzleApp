package com.mobilegame.robozzle.utils.Extensions

import androidx.compose.ui.Alignment

fun Alignment.isOnLeft(): Boolean = this == Alignment.CenterStart || this == Alignment.Start || this == Alignment.BottomStart
fun Alignment.isOnRight(): Boolean = this == Alignment.CenterEnd || this == Alignment.End || this == Alignment.BottomEnd
