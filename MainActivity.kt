package com.mobilegame.robozzle

import android.os.Bundle
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import backColor
import com.mobilegame.robozzle.data.layout.RobuzzleConfiguration
import com.mobilegame.robozzle.domain.model.LaunchingViewModel
import com.mobilegame.robozzle.presentation.res.*
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.grayDark5
import com.mobilegame.robozzle.presentation.ui.Navigation.Navigation
import com.mobilegame.robozzle.presentation.ui.Navigation.Navigator

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LaunchingApp(Navigator(), this.window) {
//                Navigation(Navigator())
            }
        }
    }
}

@Composable
fun LaunchingApp(navigator: Navigator, window: Window, content: @Composable () -> Unit) {
    //todo: https://www.youtube.com/watch?v=GhNwvGePTbY 5min20s to trigger relaunching ?
    val context = LocalContext.current
    val density = LocalDensity.current
    var launch by remember { mutableStateOf(true) }
    var composer = currentComposer

    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

    window.statusBarColor = grayDark5.toArgb()

    Box(
        Modifier
            .fillMaxSize()
            .backColor(RobuzzleConfiguration.applicationBackgroundColor)
            .onGloballyPositioned {
                if (launch) {
                    LaunchingViewModel(context).launch(it)
                    launch = false
                }
            }
    ) {
        Navigation(Navigator())
    }
}


