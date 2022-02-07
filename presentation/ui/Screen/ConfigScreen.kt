package com.mobilegame.robozzle.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.mobilegame.robozzle.analyse.infoLog

//todo : use a SaveStateViewModel to access and quickly display the app configuration that will be saved an other way more durably and load at app launch
@Composable
fun ConfigScreen() {
    infoLog("", "launch config Screen")
    Column() {
        Text(text = "Config")
        Button(
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
            onClick = {
            },
        ) {
            Text(text = "2")
        }
    }
}