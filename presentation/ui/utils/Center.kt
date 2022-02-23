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
    textAlign: TextAlign? = null,
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
                    textAlign,
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