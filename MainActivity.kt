package com.mobilegame.robozzle

import android.os.Bundle
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import com.mobilegame.robozzle.data.configuration.RobuzzleConfiguration
import com.mobilegame.robozzle.presentation.ui.utils.extensions.backColor
import com.mobilegame.robozzle.domain.model.LaunchingViewModel
import com.mobilegame.robozzle.presentation.res.*
import com.mobilegame.robozzle.presentation.ui.Navigation
import com.mobilegame.robozzle.presentation.ui.Navigator
import com.mobilegame.robozzle.data.configuration.ScreenConfig

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val screenConfig = ScreenConfig()

            val window: Window = this.window
            window.statusBarColor = grayDark5.toArgb()

            val context = LocalContext.current
            val density = LocalDensity.current

            LaunchingViewModel(context).launch(screenConfig)

            Box( Modifier
                    .fillMaxSize()
                    .backColor(RobuzzleConfiguration.applicationBackgroundColor)
            ) {
                Navigation(Navigator(), screenConfig)
            }
        }
    }
}


