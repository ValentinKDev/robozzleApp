package com.mobilegame.robozzle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import com.mobilegame.robozzle.domain.model.LaunchingViewModel
import com.mobilegame.robozzle.presentation.ui.Navigation
import com.mobilegame.robozzle.presentation.ui.Navigator
import com.mobilegame.robozzle.presentation.ui.Screen.Creator.TestShared
import kotlinx.coroutines.*

class MainActivity : ComponentActivity() {
    @DelicateCoroutinesApi
    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LaunchingViewModel(LocalContext.current).launch()
            Navigation(Navigator(), TestShared())
        }
    }
}


