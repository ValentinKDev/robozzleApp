package com.mobilegame.robozzle.presentation.ui.utils.spacer

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

//todo use the inline fun mediaQuery to manage all the spacer with the differents devices
@Composable
//)
//fun HorizontalSpace(widthDp: Int? = null, widthRatio: Float?) {
fun HorizontalSpace(widthDp: Int) {

    Spacer( modifier = Modifier
        .fillMaxHeight()
        .width(widthDp.dp)
    )
}