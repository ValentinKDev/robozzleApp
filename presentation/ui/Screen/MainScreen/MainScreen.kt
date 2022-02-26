package com.mobilegame.robozzle.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
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
import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.*
import com.mobilegame.robozzle.presentation.ui.button.MainMenuButton
import com.mobilegame.robozzle.presentation.ui.utils.spacer.VerticalSpace

@ExperimentalAnimationApi
@Composable
fun MainScreen(navigator: Navigator, fromButton: Int = MainMenuButton.None.key, w: MainScreenWindowsInfos = MainScreenWindowsInfos(), vm: MainScreenViewModel = viewModel()) {
    val visibleElements by remember(vm) {vm.visibleElements}.collectAsState(false)
    infoLog("MainScreen", "launch")

    LaunchedEffect(key1 = "Launch MainScreen") {
        vm.changeVisibility()
        vm.updateButtonSelected(MainMenuButton.None.key)
    }

    Column( modifier = Modifier.fillMaxSize() ) {
        Row( modifier = Modifier.fillMaxWidth().weight(w.firstPartScreenWeight),
            horizontalArrangement = Arrangement.End,
        ) {
            AnimatedVisibility(visible = visibleElements) {
                Box( modifier = Modifier.align(CenterVertically) ) {
                    UserDataStoreViewModel(LocalContext.current).getName()?.let {
                        Text(text = "player : $it")
                    }?: Text(text = "Not registered")
                }
            }
            Box( modifier = Modifier
                .align(CenterVertically)
                .padding(vertical = 10.dp, horizontal = 10.dp)
            ) {
                MainScreenButton( navigator,
                    UserDataStoreViewModel(LocalContext.current).getName()?.let { MainScreenButtonStyle.UserInfos.type } ?: MainScreenButtonStyle.RegisterLogin.type,
                    fromButton,
                    vm,
                    w
                )
            }
        }
        Column( modifier = Modifier.fillMaxWidth().weight(w.secondPartScreenWeight) ) {
            Column( Modifier
                    .align(CenterHorizontally)
                    .weight(1F),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                MainScreenButton(navigator, info = MainScreenButtonStyle.LevelDifficulty1.type, fromButton, vm, w)
                VerticalSpace(10)
            }
            Column( Modifier
                    .align(CenterHorizontally)
                    .weight(1F),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                MainScreenButton(navigator, info = MainScreenButtonStyle.LevelDifficulty2.type, fromButton, vm, w)
                VerticalSpace(10)
            }
            Column( Modifier
                    .align(CenterHorizontally)
                    .weight(1F),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                MainScreenButton(navigator, info = MainScreenButtonStyle.LevelDifficulty3.type, fromButton, vm, w)
                VerticalSpace(10)
            }
            Column( Modifier
                .align(CenterHorizontally)
                .weight(1F) ,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                MainScreenButton(navigator, info = MainScreenButtonStyle.LevelDifficulty4.type, fromButton, vm, w)
                VerticalSpace(10)
            }
            Column( Modifier
                .align(CenterHorizontally)
                .weight(1F)
                ,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                MainScreenButton(navigator, info = MainScreenButtonStyle.LevelDifficulty5.type, fromButton, vm, w)
                VerticalSpace(10)
            }
        }
        Row( modifier = Modifier
            .fillMaxWidth()
            .padding( horizontal = 10.dp, vertical = 15.dp )
            .weight(w.thirdPartScreenWeight)
        ) {
            Row(horizontalArrangement = Arrangement.Start, modifier = Modifier
                .weight(0.3F)
                .align(CenterVertically))
            {
                MainScreenButton(navigator, info = MainScreenButtonStyle.Creator.type, fromButton, vm, w)
            }
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.align(CenterVertically))
            {
                MainScreenButton(navigator, info = MainScreenButtonStyle.Donation.type, fromButton, vm, w)
            }
            Row(horizontalArrangement = Arrangement.End, modifier = Modifier
                .weight(0.3F)
                .align(CenterVertically))
            {
                MainScreenButton(navigator, info = MainScreenButtonStyle.Config.type, fromButton, vm, w)
            }
        }
    }
}