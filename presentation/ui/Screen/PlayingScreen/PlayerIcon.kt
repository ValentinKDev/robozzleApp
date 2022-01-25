package com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ArrowDownward
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material.icons.outlined.ArrowUpward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PlayerDirectionIcon(dir: Char, size: Int) {
    Box(modifier = Modifier.fillMaxSize() ) {
        Icon(
            imageVector = when (dir) {
                'r' -> Icons.Outlined.ArrowForward
                'l' -> Icons.Outlined.ArrowBack
                'u' -> Icons.Outlined.ArrowUpward
                'd' -> Icons.Outlined.ArrowDownward
                else -> Icons.Default.Home
            },
            contentDescription = "playerDir",
            modifier = Modifier
                .size(size.dp)
                .align(Alignment.Center)
        )
    }
}