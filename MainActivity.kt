package com.mobilegame.robozzle

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobilegame.robozzle.domain.model.MainMenuViewModel
import com.mobilegame.robozzle.domain.model.MainMenurViewModelFactory
import com.mobilegame.robozzle.domain.model.UserViewModel
import com.mobilegame.robozzle.domain.model.UserViewModelFactory
import com.mobilegame.robozzle.presentation.ui.Navigation
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

class MainActivity : ComponentActivity() {
    @DelicateCoroutinesApi
    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val context = LocalContext
        setContent {
            val context = LocalContext.current
            val mUserViewModel: UserViewModel = viewModel(
                factory = UserViewModelFactory(context.applicationContext as Application)
            )
            val mainMenuVM: MainMenuViewModel = viewModel(
                factory = MainMenurViewModelFactory(context.applicationContext as Application)
            )
            Navigation(mUserViewModel, mainMenuVM)
        }
    }
}


