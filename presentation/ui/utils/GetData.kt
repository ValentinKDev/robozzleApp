package com.mobilegame.robozzle.presentation.ui.utils

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import com.mobilegame.robozzle.analyse.errorLog

@Composable
fun getWindowCoordinates(): Rect? {
    var windowCoordinates: Rect? = null
    Box( Modifier
        .fillMaxSize()
        .onGloballyPositioned {
            windowCoordinates= it.boundsInRoot()
//            errorLog("getLayoutCoordinates() 1", "${it}")
        }
    )
//    errorLog("getLayoutCoordinates() 2", "${windowCoordinates}")
    return windowCoordinates
}
