package com.mobilegame.robozzle.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.model.Screen.MainScreenViewModel
import com.mobilegame.robozzle.domain.model.data.store.UserDataStoreViewModel
import com.mobilegame.robozzle.presentation.res.gray6
import com.mobilegame.robozzle.presentation.res.grayDark5
import com.mobilegame.robozzle.presentation.res.grayDark6
import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.ButtonId
import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.MainScreenButton
import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.MainScreenButtonStyle
import com.mobilegame.robozzle.presentation.ui.utils.spacer.VerticalSpace

@ExperimentalAnimationApi
@Composable
fun MainScreen(navigator: Navigator, fromButton: Int = ButtonId.None.key, vm: MainScreenViewModel = viewModel()) {
    val visibleElements by remember(vm) {vm.visibleElements}.collectAsState(false)
    infoLog("MainScreen", "launch")

    LaunchedEffect(key1 = "Launch MainScreen") {
        vm.changeVisibility()
        vm.updateButtonSelected(ButtonId.None.key)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(grayDark6)
        ,
    ){
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.2F)
                .padding(vertical = 10.dp, horizontal = 10.dp)
        ) {
            AnimatedVisibility(visible = visibleElements) {
                Box( modifier = Modifier .align(CenterVertically)
                ) {
                    UserDataStoreViewModel(LocalContext.current).getName()?.let {
                        Text(text = "player : $it")
                    }?: Text(text = "Not registered")
                }
            }
            Box( modifier = Modifier .align(CenterVertically) )
            {
                MainScreenButton( navigator,
                    UserDataStoreViewModel(LocalContext.current).getName()?.let
                    { MainScreenButtonStyle.UserInfos.type } ?: MainScreenButtonStyle.RegisterLogin.type,
                    fromButton,
                    vm
                )
            }
        }
            Column(
                modifier = Modifier
                    .weight(0.6F)
                    .fillMaxWidth()
                ,
            ) {
                Column(
                    Modifier
                        .align(CenterHorizontally)
                        .weight(1F),

                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    MainScreenButton(navigator, info = MainScreenButtonStyle.LevelDifficulty1.type, fromButton, vm)
                    VerticalSpace(10)
                }
                Column(
                    Modifier
                        .align(CenterHorizontally)
                        .weight(1F)
                    ,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    MainScreenButton(navigator, info = MainScreenButtonStyle.LevelDifficulty2.type, fromButton, vm)
                    VerticalSpace(10)
                }
                Column(
                    Modifier
                        .align(CenterHorizontally)
                        .weight(1F)
                    ,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    MainScreenButton(navigator, info = MainScreenButtonStyle.LevelDifficulty3.type, fromButton, vm)
                    VerticalSpace(10)
                }
                Column(
                    Modifier
                        .align(CenterHorizontally)
                        .weight(1F)
                    ,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    MainScreenButton(navigator, info = MainScreenButtonStyle.LevelDifficulty4.type, fromButton, vm)
                    VerticalSpace(10)
                }
                Column(
                    Modifier
                        .align(CenterHorizontally)
                        .weight(1F)
                    ,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    MainScreenButton(navigator, info = MainScreenButtonStyle.LevelDifficulty5.type, fromButton, vm)
                    VerticalSpace(10)
                }
            }
//        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 10.dp,
                    vertical = 15.dp
                )
                .weight(0.2F)
        ) {
            Row(horizontalArrangement = Arrangement.Start, modifier = Modifier
                .weight(0.3F)
                .align(CenterVertically)) {
                MainScreenButton(navigator, info = MainScreenButtonStyle.Creator.type, fromButton, vm)
            }
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.align(CenterVertically)) {
                MainScreenButton(navigator, info = MainScreenButtonStyle.Donation.type, fromButton, vm)
            }
            Row(horizontalArrangement = Arrangement.End, modifier = Modifier
                .weight(0.3F)
                .align(CenterVertically)) {
                MainScreenButton(navigator, info = MainScreenButtonStyle.Config.type, fromButton, vm)
            }
        }
    }
}