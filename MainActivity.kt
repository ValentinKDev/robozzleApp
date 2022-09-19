package com.mobilegame.robozzle

import android.content.Context
import android.os.Bundle
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import backColor
import com.mobilegame.robozzle.data.configuration.RobuzzleConfiguration
import com.mobilegame.robozzle.domain.model.LaunchingViewModel
import com.mobilegame.robozzle.presentation.res.*
import com.mobilegame.robozzle.presentation.ui.Navigation
import com.mobilegame.robozzle.presentation.ui.Navigator
import com.mobilegame.robozzle.data.configuration.ScreenConfig
import com.mobilegame.robozzle.domain.model.data.store.ScreenDataStoreViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LaunchingApp(this.window) {
                Navigation(Navigator())
            }
        }
    }
}

@Composable
fun LaunchingApp(window: Window, content: @Composable () -> Unit) {
    val context = LocalContext.current
    val density = LocalDensity.current

    window.statusBarColor = grayDark5.toArgb()
    Box(
        Modifier
            .fillMaxSize()
            .backColor(RobuzzleConfiguration.applicationBackgroundColor)
            .onGloballyPositioned {
                LaunchingViewModel(context).launch(it)
            }
    ) {
        content.invoke()
    }
}


