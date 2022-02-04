package com.mobilegame.robozzle

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobilegame.robozzle.domain.model.LaunchingViewModel
import com.mobilegame.robozzle.presentation.ui.Navigation
import com.mobilegame.robozzle.presentation.ui.Navigator
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class MainActivity : ComponentActivity() {
    @DelicateCoroutinesApi
    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LaunchingViewModel(LocalContext.current).launch()
            Navigation(Navigator())
        }
    }
}


