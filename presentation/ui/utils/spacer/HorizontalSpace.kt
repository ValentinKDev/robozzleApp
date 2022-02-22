package com.mobilegame.robozzle.presentation.ui.utils.spacer

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HorizontalSpace(widthDp: Int) {
    Spacer( modifier = Modifier
        .fillMaxHeight()
        .width(widthDp.dp) )
}