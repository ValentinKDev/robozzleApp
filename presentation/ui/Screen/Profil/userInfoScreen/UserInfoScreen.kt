package com.mobilegame.robozzle.presentation.ui.Screen.Profil

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.domain.model.Screen.utils.RankingIconViewModel
import com.mobilegame.robozzle.domain.model.User.UserInfosScreenViewModel
import com.mobilegame.robozzle.presentation.ui.*
import com.mobilegame.robozzle.presentation.ui.Navigation.Navigator
import com.mobilegame.robozzle.presentation.ui.Screen.Profil.userInfoScreen.UserInfoScreenFirstPart
import com.mobilegame.robozzle.presentation.ui.Screen.Profil.userInfoScreen.UserInfoScreenSecondPart
import com.mobilegame.robozzle.presentation.ui.Screen.Profil.userInfoScreen.UserInfoScreenThirdPart


@Composable
fun UserInfoScreen(navigator: Navigator, vm: UserInfosScreenViewModel = viewModel()) {

    val listVisible by remember(vm){ vm.logic.gridVisible}.collectAsState()

    LaunchedEffect(true) {
        vm.logic.setVisibilityAtLaunch()
        errorLog("Launch", "UserInfoScreen()")
    }

    BackHandler {
        vm.logic.backHandler(navigator)
    }

    Column() {
        Box(
            Modifier
                .fillMaxWidth()
                .weight(vm.uiData.secondPart.dimensions.heightWeight) ) {
            UserInfoScreenFirstPart( vm = vm, navigator = navigator )
        }
        Box(
            Modifier
                .fillMaxWidth()
                .weight(vm.uiData.secondPart.dimensions.heightWeight) ) {
            UserInfoScreenSecondPart(vm = vm)
        }
        Column(
            Modifier
                .fillMaxWidth()
                .weight(vm.uiData.thirdPart.dimensions.heightWeight) ) {
            AnimatedVisibility(
                visible = listVisible,
                enter = slideInVertically(),
                exit = slideOutVertically(animationSpec = tween(300))
                        + fadeOut( animationSpec = tween( 300 ))
            ) {
                UserInfoScreenThirdPart(
                    vm = vm,
                    navigator = navigator
                )
            }
        }
    }
}