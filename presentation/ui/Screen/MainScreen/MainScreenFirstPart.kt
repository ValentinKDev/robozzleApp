package com.mobilegame.robozzle.presentation.ui.Screen.MainScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.domain.model.Screen.mainScreen.MainScreenViewModel
import com.mobilegame.robozzle.presentation.res.whiteDark2
import com.mobilegame.robozzle.presentation.ui.Navigator
import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.button.MainScreenButton
import com.mobilegame.robozzle.presentation.ui.Screen.Screens

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainScreenFirstPart(
    navigator: Navigator,
    fromScreen: Screens = Screens.None,
    w: MainScreenWindowsInfos,
    vm: MainScreenViewModel
) {
    val visibleElements by remember(vm) {vm.visibleElements}.collectAsState(false)
    val name = vm.getName()

    Row( Modifier .fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
    ) {
        Row(
            modifier = Modifier
                .align(CenterVertically)
            ,
        ) {
            AnimatedVisibility(visible = visibleElements) {
                Text(
                    text = name,
                    color = whiteDark2
                )
            }
        }
        Box( modifier = Modifier
            .align(Alignment.CenterVertically)
            .padding(vertical = 10.dp, horizontal = 10.dp)
        ) {
            MainScreenButton(
                navigator = navigator,
                info = vm.getNavInfoToUser(),
                fromScreen = fromScreen,
                vm = vm,
                w = w
            )
        }
    }
}
