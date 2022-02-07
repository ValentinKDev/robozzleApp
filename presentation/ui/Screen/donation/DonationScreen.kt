package com.mobilegame.robozzle.presentation.ui.Screen.donation

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.ViewModel
import com.mobilegame.robozzle.presentation.ui.Screen.Creator.TestShared
import com.mobilegame.robozzle.presentation.ui.Screen.NavigationDestination
import kotlinx.coroutines.flow.*

@Composable
fun DonationScreen(testShared: TestShared) {
    Text(text = "donation screen")

//    LaunchedEffect("navigation") {
//        testShared.des.onEach {
//            navController.navigate(it)
//        }.launchIn(this)
//    }
}

class DonationScreenVM(): ViewModel(){
}