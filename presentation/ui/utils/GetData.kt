package com.mobilegame.robozzle.presentation.ui.utils

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned

@Composable
fun getLayoutCoordinates(): LayoutCoordinates? {
    var layoutCoordinates: LayoutCoordinates? = null
    Box( Modifier
        .fillMaxSize()
        .onGloballyPositioned {
            layoutCoordinates = it
        }
    )
    return layoutCoordinates
}
