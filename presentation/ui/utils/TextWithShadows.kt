package com.mobilegame.robozzle.presentation.ui.utils

import androidx.compose.foundation.layout.offset
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.presentation.res.grayDark5
import com.mobilegame.robozzle.presentation.res.whiteDark4

@Composable
fun TextWithShadow(
    text: String,
    modifier: Modifier
) {
    Text(
        text = text,
        color = grayDark5,
        modifier = modifier
            .offset(
                x = 2.dp,
                y = 2.dp
            )
            .alpha(0.75f)
    )
    Text(
        text = text,
        color = whiteDark4,
        modifier = modifier
    )
}
