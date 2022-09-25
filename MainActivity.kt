package com.mobilegame.robozzle

import android.os.Bundle
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import backColor
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.verbalLog
import com.mobilegame.robozzle.data.configuration.RobuzzleConfiguration
import com.mobilegame.robozzle.domain.model.LaunchingViewModel
import com.mobilegame.robozzle.presentation.res.*
import com.mobilegame.robozzle.presentation.ui.Navigation
import com.mobilegame.robozzle.presentation.ui.Navigator
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

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
    val context = LocalContext.current
    val density = LocalDensity.current
    var launch by remember { mutableStateOf(true) }
    var composer = currentComposer

//    val recompose by remember(navigator) { navigator.forceReload }.collectAsState(initial = false)

//    LaunchedEffect(true) {
//        navigator.forceReload.collect {
//            if (it) {
//                errorLog(
//                    "RECOMPOSITION",
//                    "***********************************************************************"
//                )
//                composer.composition.recompose()
//            }
//        }
//    }

//    if (relaunch) {
//        errorLog("RECOMPOSITION", "***********************************************************************")
//        currentComposer.composition.recompose()
//    }

    window.statusBarColor = grayDark5.toArgb()
    Box(
        Modifier
            .fillMaxSize()
            .backColor(RobuzzleConfiguration.applicationBackgroundColor)
            .onGloballyPositioned {
//                verbalLog("LaunchingApp", "recompose $recompose")
                if (launch) {
//                    LaunchingViewModel(context).launch(it, navigator)
                    LaunchingViewModel(context).launch(it)
                    launch = false
                }
            }
    ) {
        Navigation(Navigator())
//        content.invoke()
    }
}


