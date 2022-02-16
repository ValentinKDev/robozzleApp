package com.mobilegame.robozzle.presentation.ui.utils

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun CenterText(str: String) {
    Column( modifier = Modifier.fillMaxSize()
    ) {
        Row( modifier = Modifier.fillMaxHeight().align(Alignment.CenterHorizontally)
        ) {
            Box( modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Text(text = str)
            }
        }
    }
}