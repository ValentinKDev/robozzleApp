package com.mobilegame.robozzle.presentation.ui.Screen.donation

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.domain.model.Screen.Navigation.NavViewModel
import com.mobilegame.robozzle.domain.model.Screen.donation.DonationScreenViewModel
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.red7
import com.mobilegame.robozzle.presentation.ui.Navigation.Navigator
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import noRippleClickable

@Composable
fun DonationScreen(navigator: Navigator, vm: DonationScreenViewModel = viewModel()) {
    val focusManager = LocalFocusManager.current
    val visibleHeaderState by remember {vm.logic.animHeaderListVM.visibleHeaderState}.collectAsState()
    val visibleListState by remember {vm.logic.animHeaderListVM.visibleListState}.collectAsState()

    LaunchedEffect(true) {
        vm.logic.animHeaderListVM.setVisible()
        errorLog("DonationScreen", "Start")
    }

    BackHandler { vm.logic.startExitAnimationAndPressBack() }

    vm.logic.backButtonListner(navigator)

    Box(
        Modifier
            .fillMaxSize()
            .noRippleClickable {
                focusManager.clearFocus()
            }
    ) {
        Column {
            Column(Modifier.weight(vm.ui.header.ratios.heightWeight)) { }
            Column(Modifier.weight(vm.ui.presentation.ratios.heightWeight) ) {
                AnimatedVisibility(
                    visibleState = visibleListState,
                    enter = expandVertically(),
                    exit = shrinkVertically()
                ) {
                    presentation(vm)
                }
            }
            Box(Modifier.weight(vm.ui.selector.ratios.heightWeight) ) { }
            Box(Modifier.weight(vm.ui.keyboardSpace.ratios.heightWeight) ) { }
        }

        AnimatedVisibility(
            visibleState = visibleListState,
            enter = expandVertically(),
            exit = fadeOut(tween(250))
        ) {
            Box( modifier = Modifier.fillMaxWidth() ) {
                DonationScreenSecondPart(vm)
            }
        }

        Column {
            Column(Modifier.weight(vm.ui.header.ratios.heightWeight)) {
                AnimatedVisibility(
                    visibleState = visibleHeaderState ,
                    enter =  fadeIn(),
                    exit = fadeOut()
                ) {
                    header(vm)
                }
            }
            Column(Modifier.weight(vm.ui.presentation.ratios.heightWeight) ) { }
            Box(Modifier.weight(vm.ui.selector.ratios.heightWeight) ) { }
            Box(Modifier.weight(vm.ui.keyboardSpace.ratios.heightWeight) ) { }
        }
    }
}

