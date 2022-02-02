package com.mobilegame.robozzle.presentation.ui.Screen.MainMenu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mobilegame.robozzle.presentation.ui.button.ButtonInfo

@Composable
fun MainScreenButton(navController: NavController, info: ButtonInfo) {
    Button(
        modifier = Modifier
            .background(info.color)
            .width(info.width.dp)
            .height(info.height.dp)
        ,
        onClick = { navController.navigate(info.route) },
        enabled = info.enable
    ) {
        Text(text = info.text)
    }
}
