package com.mobilegame.robozzle.presentation.ui.Screen.MainScreen

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.domain.model.Screen.mainScreen.MainScreenViewModel
import com.mobilegame.robozzle.presentation.ui.Navigator
import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.button.MainScreenButton
import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.button.MainScreenButtonStyle
import com.mobilegame.robozzle.presentation.ui.button.MainMenuButton

@ExperimentalAnimationApi
@Composable
fun MainScreenThirdPart(
    navigator: Navigator,
    fromButton: Int = MainMenuButton.None.key,
    w: MainScreenWindowsInfos,
    vm: MainScreenViewModel
) {
    Row( modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 10.dp, vertical = 15.dp)
    ) {
        Row(horizontalArrangement = Arrangement.Start, modifier = Modifier
            .weight(0.3F)
            .align(Alignment.CenterVertically))
        {
            MainScreenButton(navigator, info = MainScreenButtonStyle.Creator.type, fromButton, vm, w)
        }
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.align(Alignment.CenterVertically))
        {
            MainScreenButton(navigator, info = MainScreenButtonStyle.Donation.type, fromButton, vm, w)
        }
        Row(horizontalArrangement = Arrangement.End, modifier = Modifier
            .weight(0.3F)
            .align(Alignment.CenterVertically))
        {
            MainScreenButton(navigator, info = MainScreenButtonStyle.Config.type, fromButton, vm, w)
        }
    }
}
