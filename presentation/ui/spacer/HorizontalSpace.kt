package com.mobilegame.robozzle.presentation.ui.spacer

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HorizontalSpace(width: Int) {
    Spacer( modifier = Modifier
        .fillMaxHeight()
        .width(width.dp) )
}