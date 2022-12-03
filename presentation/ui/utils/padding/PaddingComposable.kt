package com.mobilegame.robozzle.presentation.ui.utils.padding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import backColor
import com.mobilegame.robozzle.presentation.res.*
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.blue4
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.green4
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.greendark2
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.red4
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.yellow4

@Composable
fun PaddingComposable(
    topPaddingRatio: Float = 0F,
    bottomPaddingRatio: Float = 0F,
    startPaddingRatio: Float = 0F,
    endPaddingRatio: Float = 0F,
    enableColor: Boolean? = null,
    content: @Composable () -> Unit,
) {
    val verticalElementWeight: Float? = if ( topPaddingRatio + bottomPaddingRatio < 1F ) 1F - topPaddingRatio - bottomPaddingRatio else null
    val horizontalElementWeight: Float? = if ( endPaddingRatio + startPaddingRatio < 1F ) 1F - startPaddingRatio - endPaddingRatio else null

    verticalElementWeight?.let {
        horizontalElementWeight?.let {
            Column(Modifier.fillMaxSize()) {
                if (topPaddingRatio == 0F)
                    Row( content = {}, modifier = Modifier.fillMaxWidth().backColor(enableColor?.let { red4 } ?: Color.Transparent))
                else
                    Row( content = {}, modifier = Modifier.fillMaxWidth().weight(topPaddingRatio).backColor(enableColor?.let { red4 } ?: Color.Transparent))
                Row(Modifier.fillMaxWidth().weight(verticalElementWeight)) {
                    if (startPaddingRatio == 0F)
                        Column( content = {}, modifier = Modifier.fillMaxHeight().backColor(enableColor?.let { green4 } ?: Color.Transparent))
                    else
                        Column( content = {}, modifier = Modifier.fillMaxHeight().weight(startPaddingRatio).backColor(enableColor?.let { green4 } ?: Color.Transparent))
                    Column( Modifier.fillMaxWidth().weight(horizontalElementWeight))
                    {
                        content.invoke()
                    }
                    if (endPaddingRatio == 0F)
                        Column( content = {}, modifier = Modifier.fillMaxHeight().backColor(enableColor?.let { yellow4 } ?: Color.Transparent))
                    else
                        Column( content = {}, modifier = Modifier.fillMaxHeight().weight(endPaddingRatio).backColor(enableColor?.let { yellow4 } ?: Color.Transparent))
                }
                if (bottomPaddingRatio == 0F)
                    Row( content = {}, modifier = Modifier.fillMaxWidth().backColor(enableColor?.let { blue4 } ?: Color.Transparent))
                else
                    Row( content = {}, modifier = Modifier.fillMaxWidth().weight(bottomPaddingRatio).backColor(enableColor?.let { blue4 } ?: Color.Transparent))
            }
        }
    }
}

@Composable
fun PaddingComposable(
    verticalPadding: Float = 0F,
    horizontalPadding: Float = 0F,
    enableColor: Boolean? = null,
    content: @Composable () -> Unit,
) {
    val rowElementRatio: Float? = if (2 * verticalPadding < 1F) 1F - (2 * verticalPadding) else null
    val columnElementRatio: Float? = if (2 * horizontalPadding < 1F) 1F - (2 * horizontalPadding) else null

    rowElementRatio?.let {
        columnElementRatio?.let {
            Column(Modifier.fillMaxSize()) {
                if (verticalPadding == 0F)
                    Row( content = {}, modifier = Modifier.fillMaxWidth().background(enableColor?.let { blue4 } ?: Color.Transparent))
                else
                    Row( content = {}, modifier = Modifier.fillMaxWidth().weight(verticalPadding).background(enableColor?.let { blue4 } ?: Color.Transparent))
                Row(Modifier.fillMaxWidth().weight(rowElementRatio)) {
                    if (horizontalPadding == 0F)
                        Column( content = {}, modifier = Modifier.fillMaxHeight().background(enableColor?.let { green4 } ?: Color.Transparent))
                    else
                        Column( content = {}, modifier = Modifier.fillMaxHeight().weight(horizontalPadding).background(enableColor?.let { green4 } ?: Color.Transparent))
                    Column( Modifier.fillMaxWidth().weight(columnElementRatio)) {
                        content.invoke()
                    }
                    if (horizontalPadding == 0F)
                        Column( content = {}, modifier = Modifier.fillMaxHeight().background(enableColor?.let { yellow4 } ?: Color.Transparent))
                    else
                        Column( content = {}, modifier = Modifier.fillMaxHeight().weight(horizontalPadding).background(enableColor?.let { yellow4 } ?: Color.Transparent))
                }
                if (verticalPadding == 0F)
                    Row( content = {}, modifier = Modifier.fillMaxWidth().background(enableColor?.let { blue4 } ?: Color.Transparent))
                else
                    Row( content = {}, modifier = Modifier.fillMaxWidth().weight(verticalPadding).background(enableColor?.let { blue4 } ?: Color.Transparent))
            }
        }
    }
}
