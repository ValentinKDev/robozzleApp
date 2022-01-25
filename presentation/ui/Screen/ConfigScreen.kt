package com.mobilegame.robozzle.presentation.ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.mobilegame.robozzle.analyse.infoLog

//todo : use a SaveStateViewModel to access and quickly display the app configuration that will be saved an other way more durably and load at app launch
@Composable
fun ConfigScreen() {
    infoLog("", "launch config Screen")
    Text(text = "Config")
}