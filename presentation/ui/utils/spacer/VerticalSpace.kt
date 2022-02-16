package com.mobilegame.robozzle.presentation.ui.utils.spacer

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun VerticalSpace(height: Int) {
    Spacer( modifier = Modifier
        .fillMaxWidth()
        .height(height.dp) )
}
