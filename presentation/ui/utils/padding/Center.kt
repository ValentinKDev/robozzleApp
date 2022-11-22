package com.mobilegame.robozzle.presentation.ui.utils

import androidx.compose.foundation.layout.*
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import backColor

@Composable
fun CenterComposable(content: @Composable () -> Unit) {
    Box( modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) { content.invoke() }
}

@Composable
fun CenterComposableVertically(content: @Composable () -> Unit) {
    Row( modifier = Modifier.fillMaxHeight() ) {
        Column( modifier = Modifier
            .wrapContentSize()
            .align(Alignment.CenterVertically) ) {
            Box( modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                content.invoke()
            }
        }
    }
}

@Composable
fun CenterComposableHorizontally(content: @Composable () -> Unit) {
    Column( modifier = Modifier.fillMaxWidth()
//        .backColor(Color.Transparent)
    ) {
        Row( modifier = Modifier
//            .backColor(Color.Transparent)
            .wrapContentHeight()
            .align(Alignment.CenterHorizontally) ) {
            Box( modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                content.invoke()
            }
        }
    }
}

@Composable
fun CenterText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current
) {
    Column( modifier = Modifier.fillMaxSize()
    ) {
        Row( modifier = Modifier
            .fillMaxHeight()
            .align(Alignment.CenterHorizontally)
        ) {
            Box( modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Text(
                    text,
                    modifier,
                    color,
                    fontSize,
                    fontStyle,
                    fontWeight,
                    fontFamily,
                    letterSpacing,
                    textDecoration,
                    textAlign = TextAlign.Center,
                    lineHeight,
                    overflow,
                    softWrap,
                    maxLines,
                    onTextLayout,
                    style
                )
            }
        }
    }
}