package com.mobilegame.robozzle.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mobilegame.robozzle.domain.model.UserViewModel
import com.mobilegame.robozzle.toREMOVE.PlayerData
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
@Composable
fun MainSreen(navController: NavController, playerData: PlayerData, mUserViewModel: UserViewModel) {
    val connected: Boolean by mUserViewModel.connected.observeAsState(initial = false)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(30.dp)
    ){
//        Text(text = "name is ${playerData.name}")
//        Text(text = "playerName is ${mUserViewModel.GetCurrentUserName()}")
        if (connected) Text(text = "Connected as ${mUserViewModel.GetCurrentUserName()}")
        Padd()
        ButtonMainScren(text = "Profile", navController = navController, route = Screens.ProfilScreen.route)
        Spacer(modifier = Modifier.height(50.dp))
        ButtonMainScren(text = "Levels", navController = navController, route = Screens.LevelsScreen.route)
        Padd()
        ButtonMainScren(text = "Config", navController = navController, route = Screens.ConfigScreen.route)
        Padd()
        ButtonMainScren(text = "Creator", navController = navController, route = Screens.CreatorScreen.route)
    }
}

@Composable
fun ButtonMainScren(text: String, navController: NavController, route: String) {
    Box(
        modifier = Modifier
            .background(Color.Gray)
            .height(100.dp)
            .width(200.dp)
            .clickable { navController.navigate(route) }
    ) {
        Text(text, modifier = Modifier.align(Alignment.Center))
    }

}

@Composable
fun Padd() {
    Box( modifier = Modifier.size(50.dp) ) { }
}

