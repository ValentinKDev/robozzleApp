package com.mobilegame.robozzle

import android.os.Bundle
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import com.mobilegame.robozzle.domain.model.LaunchingViewModel
import com.mobilegame.robozzle.presentation.res.*
import com.mobilegame.robozzle.presentation.ui.Navigation
import com.mobilegame.robozzle.presentation.ui.Navigator
import com.mobilegame.robozzle.presentation.ui.Screen.Creator.TestShared
import kotlinx.coroutines.*

class MainActivity : ComponentActivity() {
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val window: Window = this.window
            window.statusBarColor = grayDark5.toArgb()

            LaunchingViewModel(LocalContext.current).launch()
            Box(Modifier.fillMaxWidth()
            .background(grayDark6)
            ) {
                Navigation(Navigator(), TestShared())
            }
        }
    }
}


