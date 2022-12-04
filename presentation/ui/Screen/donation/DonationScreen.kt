package com.mobilegame.robozzle.presentation.ui.Screen.donation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobilegame.robozzle.domain.model.Screen.Navigation.NavViewModel
import com.mobilegame.robozzle.domain.model.Screen.donation.DonationScreenViewModel
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.red7
import com.mobilegame.robozzle.presentation.ui.Navigation.Navigator
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import noRippleClickable

@Composable
fun DonationScreen(navigator: Navigator, vm: DonationScreenViewModel = viewModel()) {

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    BackHandler {
        NavViewModel(navigator).navigateToMainMenu( fromScreen = Screens.Donation.route )
    }

    Box(
        Modifier
            .fillMaxSize()
            .noRippleClickable {
//                vm.logic.fold()
                focusManager.clearFocus()
            }
    ) {
        Column {
            Box(Modifier.weight(vm.ui.header.ratios.heightWeight)) {
                header(vm)
            }
            Box(Modifier.weight(vm.ui.presentation.ratios.heightWeight) ) {
                presentation(vm)
            }
            Box(Modifier.weight(vm.ui.selector.ratios.heightWeight) ) { }
            Box(Modifier.weight(vm.ui.keyboardSpace.ratios.heightWeight) ) { }
        }
        Box( modifier = Modifier.fillMaxWidth() ) {
            DonationScreenSecondPart(vm)
        }
    }
}

