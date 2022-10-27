package com.mobilegame.robozzle.presentation.ui.Screen.donation

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobilegame.robozzle.domain.model.Screen.NavViewModel
import com.mobilegame.robozzle.domain.model.Screen.donation.DonationScreenViewModel
import com.mobilegame.robozzle.presentation.ui.Navigator
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import com.mobilegame.robozzle.presentation.ui.utils.padding.PaddingComposable
import noRippleClickable

@Composable
fun DonationScreen(navigator: Navigator, vm: DonationScreenViewModel = viewModel()) {
    BackHandler {
        NavViewModel(navigator).navigateTo(
            destination = Screens.MainMenu,
            argStr = Screens.Donation.route
        )
    }
    Box( Modifier.fillMaxSize()
        .noRippleClickable { vm.logic.fold() }
    ) {
        Column(Modifier.fillMaxSize()) {
            PaddingComposable(
                bottomPaddingRatio = vm.data.ratios.firstPartBottomPadding
            ) {
                Box( modifier = Modifier
                    .fillMaxWidth()
                ) {
                    DonationScreenFirstPart(vm)
                }
            }
        }
        Column(Modifier.fillMaxSize()) {
            Box( modifier = Modifier.fillMaxWidth() )
            {
                DonationScreenSecondPart(vm)
            }
        }
    }
}

