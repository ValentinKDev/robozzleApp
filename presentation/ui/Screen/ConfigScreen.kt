package com.mobilegame.robozzle.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.presentation.ui.Screen.Creator.testViewModel

//todo : use a SaveStateViewModel to access and quickly display the app configuration that will be saved an other way more durably and load at app launch
@Composable
fun ConfigScreen(testViewModel: testViewModel = viewModel()) {
    infoLog("", "launch config Screen")
    Column() {
        Text(text = "Config")
        Button(
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
            onClick = {
                infoLog("num before", "${testViewModel.num}")
                testViewModel.num = 7
                infoLog("num after", "${testViewModel.num}")
            },
        ) {
            Text(text = "2")
        }
    }
}