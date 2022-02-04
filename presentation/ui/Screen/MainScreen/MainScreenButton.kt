package com.mobilegame.robozzle.presentation.ui.Screen.MainScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.domain.model.Screen.NavViewModel
import com.mobilegame.robozzle.presentation.ui.Navigator
import com.mobilegame.robozzle.presentation.ui.button.NavigationButtonInfo

@Composable
//fun MainScreenButton(navController: NavController, info: NavigationButtonInfo) {
fun MainScreenButton(navigator: Navigator, info: NavigationButtonInfo) {
    Button(
        modifier = Modifier
            .background(info.color)
            .width(info.width.dp)
            .height(info.height.dp)
        ,
//        onClick = { navController.navigate(info.route) },
        onClick = {
//                  SomeVM(navigator, nav)
//            MainScreenViewModel(navigator).navigateTo(info)
//            MainScreenViewModel(navigator).nav(info)
//                  navigator.em(info.destination)
                  NavViewModel(navigator).navigateTo(info.destination, info.arg)
        },
        enabled = info.enable
    ) {
        Text(text = info.text)
    }
}
