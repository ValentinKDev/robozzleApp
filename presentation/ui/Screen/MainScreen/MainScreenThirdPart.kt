package com.mobilegame.robozzle.presentation.ui.Screen.MainScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import backColor
import com.mobilegame.robozzle.domain.model.Screen.mainScreen.MainScreenViewModel
import com.mobilegame.robozzle.presentation.res.MyColor
import com.mobilegame.robozzle.presentation.ui.Navigation.Navigator
import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.button.MainScreenButton
import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.button.MainScreenButtonStyle
import com.mobilegame.robozzle.presentation.ui.Screen.Screens

@Composable
fun MainScreenThirdPart(
    navigator: Navigator,
    fromScreen: Screens = Screens.None,
    w: MainScreenWindowsInfos,
    vm: MainScreenViewModel
) {
    Row( modifier = Modifier
        .fillMaxSize()
//        .backColor(green10)
        .padding(horizontal = 10.dp, vertical = 15.dp)
    ) {
        Row(horizontalArrangement = Arrangement.Start, modifier = Modifier
            .weight(0.3F)
            .align(Alignment.CenterVertically))
        {
                MainScreenButton(navigator, info = MainScreenButtonStyle.Creator.type, fromScreen, vm, w, false)
        }
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier
//            .backColor(blueDark5)
            .align(Alignment.CenterVertically))
        {
            MainScreenButton(navigator, info = MainScreenButtonStyle.Donation.type, fromScreen, vm, w)
        }
        Row(horizontalArrangement = Arrangement.End, modifier = Modifier
            .weight(0.3F)
//            .backColor(green5)
            .align(Alignment.CenterVertically))
        {
            MainScreenButton(navigator, info = MainScreenButtonStyle.Config.type, fromScreen, vm, w)
        }
    }
}
