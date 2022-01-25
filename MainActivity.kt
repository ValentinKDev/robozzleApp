package com.mobilegame.robozzle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.mobilegame.robozzle.presentation.ui.Navigation
import kotlinx.coroutines.InternalCoroutinesApi

class MainActivity : ComponentActivity() {
    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { Navigation() }
    }
}


