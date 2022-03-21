package com.mobilegame.robozzle.presentation.ui.utils.spacer

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun VerticalSpace(heightInt: Int? = null, heightDp: Dp? = null) {
    Spacer( modifier = Modifier
        .fillMaxWidth()
        .height(heightInt?.dp ?: heightDp ?: 0.dp)
    )
}
