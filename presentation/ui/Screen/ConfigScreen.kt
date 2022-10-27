package com.mobilegame.robozzle.presentation.ui

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.currentComposer
import androidx.compose.ui.graphics.Color
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.model.Screen.NavViewModel
import com.mobilegame.robozzle.presentation.ui.Screen.Screens

//todo : use a SaveStateViewModel to access and quickly display the app configuration that will be saved an other way more durably and load at app launch
@Composable
fun ConfigScreen(navigator: Navigator) {
    LaunchedEffect(key1 = true) {
        Log.e("ConfigScreen", "Start")
    }
    BackHandler {
        NavViewModel(navigator).navigateTo(
            destination = Screens.MainMenu,
            argStr = Screens.Config.route
        )
    }
    infoLog("", "launch config Screen")
    val composer = currentComposer
    Column() {
        Text(text = "Config")
        Button(
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
            onClick = {
//                navigator.reload_launcher(true)
                navigator.reload_launcher("truc")
            },
        ) {
            Text(text = "2")
        }
    }
}