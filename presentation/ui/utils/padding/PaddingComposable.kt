package com.mobilegame.robozzle.presentation.ui.utils.padding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.mobilegame.robozzle.Extensions.backColor
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.presentation.res.*

@Composable
fun PaddingComposable(
    topPaddingRatio: Float = 0f,
    bottomPaddingRatio: Float = 0f,
    startPaddingRatio: Float = 0f,
    endPaddingRatio: Float = 0f,
    enableColor: Boolean? = null,
    content: @Composable () -> Unit,
) {
    val verticalElementWeight: Float? = if ( topPaddingRatio in 0F..1F && bottomPaddingRatio in 0F..1F && (topPaddingRatio + bottomPaddingRatio) < 1F ) 1F - topPaddingRatio - bottomPaddingRatio else null
    val horizontalElementWeight: Float? = if ( startPaddingRatio in 0F..1F && endPaddingRatio in 0f..1F && (endPaddingRatio + startPaddingRatio ) < 1F ) 1F - startPaddingRatio - endPaddingRatio else null

    verticalElementWeight?.let {
        horizontalElementWeight?.let {
            Column(Modifier.fillMaxSize()) {
                Row( content = {}, modifier = Modifier.fillMaxWidth().weight(topPaddingRatio).backColor(enableColor?.let { redDark2 } ?: Color.Transparent))
                Row(Modifier.fillMaxWidth().weight(verticalElementWeight)) {
                    Column( content = {}, modifier = Modifier.fillMaxHeight().weight(startPaddingRatio).backColor(enableColor?.let { greendark2 } ?: Color.Transparent))
                    Column( Modifier.fillMaxWidth().weight(horizontalElementWeight)) {
                        content.invoke()
                    }
                    Column( content = {}, modifier = Modifier.fillMaxHeight().weight(endPaddingRatio).backColor(enableColor?.let { yellowDark2 } ?: Color.Transparent))
                }
                Row( content = {}, modifier = Modifier.fillMaxWidth().weight(bottomPaddingRatio).backColor(enableColor?.let { blueDark2 } ?: Color.Transparent))
            }
        }
    }
}

@Composable
fun PaddingComposable(
    verticalPadding: Float = 0f,
    horizontalPadding: Float = 0f,
    content: @Composable () -> Unit,
    enableColor: Boolean? = null
) {
    val rowElementRatio: Float? = if ( verticalPadding in 0F..1F && 2 * verticalPadding < 1F ) 1F - (2 * verticalPadding) else null
    val columnElementRatio: Float? = if ( horizontalPadding in 0F..1F && 2 * horizontalPadding < 1F  ) 1F - (2 * horizontalPadding) else null

    rowElementRatio?.let {
        columnElementRatio?.let {
            Column(Modifier.fillMaxSize()) {
                Row( content = {}, modifier = Modifier.fillMaxWidth().weight(verticalPadding).background(enableColor?.let { redDark2 } ?: Color.Transparent))
                Row(Modifier.fillMaxWidth().weight(rowElementRatio)) {
                    Column( content = {}, modifier = Modifier.fillMaxHeight().weight(horizontalPadding).background(enableColor?.let { greendark2 } ?: Color.Transparent))
                    Column( Modifier.fillMaxWidth().weight(columnElementRatio)) {
                        content.invoke()
                    }
                    Column( content = {}, modifier = Modifier.fillMaxHeight().weight(horizontalPadding).background(enableColor?.let { yellowDark2 } ?: Color.Transparent))
                }
                Row( content = {}, modifier = Modifier.fillMaxWidth().weight(verticalPadding).background(enableColor?.let { blueDark2 } ?: Color.Transparent))
            }
        }
    }
}
