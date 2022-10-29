package com.mobilegame.robozzle.presentation.ui.Screen.MainScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.mobilegame.robozzle.domain.model.Screen.mainScreen.MainScreenViewModel
import com.mobilegame.robozzle.presentation.ui.Navigation.Navigator
import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.button.MainScreenButton
import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.button.MainScreenButtonStyle
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import com.mobilegame.robozzle.presentation.ui.utils.spacer.VerticalSpace

@Composable
fun MainScreenSecondPart(
    navigator: Navigator,
    fromScreen: Screens = Screens.None,
    w: MainScreenWindowsInfos,
    vm: MainScreenViewModel
) {
    Column( modifier = Modifier
        .fillMaxSize()
    ) {
        Column(
            Modifier
                .align(Alignment.CenterHorizontally)
                .weight(1F),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            MainScreenButton(navigator, info = MainScreenButtonStyle.LevelDifficulty1.type, fromScreen, vm, w)
            VerticalSpace(10)
        }
        Column(
            Modifier
                .align(Alignment.CenterHorizontally)
                .weight(1F),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            MainScreenButton(navigator, info = MainScreenButtonStyle.LevelDifficulty2.type, fromScreen, vm, w)
            VerticalSpace(10)
        }
        Column(
            Modifier
                .align(Alignment.CenterHorizontally)
                .weight(1F),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            MainScreenButton(navigator, info = MainScreenButtonStyle.LevelDifficulty3.type, fromScreen, vm, w)
            VerticalSpace(10)
        }
        Column(
            Modifier
                .align(Alignment.CenterHorizontally)
                .weight(1F) ,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            MainScreenButton(navigator, info = MainScreenButtonStyle.LevelDifficulty4.type, fromScreen, vm, w)
            VerticalSpace(10)
        }
        Column(
            Modifier
                .align(Alignment.CenterHorizontally)
                .weight(1F)
            ,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            MainScreenButton(navigator, info = MainScreenButtonStyle.LevelDifficulty5.type, fromScreen, vm, w)
            VerticalSpace(10)
        }
    }
}