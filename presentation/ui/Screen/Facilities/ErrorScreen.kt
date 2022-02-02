package com.mobilegame.robozzle.presentation.ui.Screen.Facilities

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.mobilegame.robozzle.analyse.errorLog

@Composable
fun ErrorScreen() {
    Text(text = "ErrorScreen")
    errorLog("Launch", "Error Screen")

}
