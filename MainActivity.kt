package com.mobilegame.robozzle

import android.os.Bundle
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import com.mobilegame.robozzle.Extensions.backColor
import com.mobilegame.robozzle.domain.model.LaunchingViewModel
import com.mobilegame.robozzle.presentation.res.*
import com.mobilegame.robozzle.presentation.ui.Navigation
import com.mobilegame.robozzle.presentation.ui.Navigator
import com.mobilegame.robozzle.presentation.ui.Screen.ScreenData

@Suppress("EXPERIMENTAL_ANNOTATION_ON_OVERRIDE_WARNING")
class MainActivity : ComponentActivity() {
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            val density = LocalDensity.current
            val window: Window = this.window
            window.statusBarColor = grayDark5.toArgb()

            LaunchingViewModel(LocalContext.current).launch()
            val screenData = ScreenData()
            screenData.init(context, density)

            Box( Modifier
                    .fillMaxWidth()
                    .backColor(grayDark6)
            ) {
                Navigation(Navigator(), ScreenData())
            }
        }
    }
}


