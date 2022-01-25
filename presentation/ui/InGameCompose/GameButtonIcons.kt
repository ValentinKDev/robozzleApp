package com.mobilegame.robozzle.presentation.ui.InGameCompose

import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Pause
import androidx.compose.material.icons.sharp.PlayArrow
import androidx.compose.runtime.Composable

@Composable
fun PlayPauseIcon(isPlaying: Boolean) {
    Icon(
        imageVector = if (isPlaying) Icons.Sharp.Pause else Icons.Sharp.PlayArrow,
        contentDescription = "PlayPause",
    )
}
