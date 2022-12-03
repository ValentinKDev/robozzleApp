package com.mobilegame.robozzle.presentation.ui.Screen.donation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobilegame.robozzle.domain.model.Screen.Navigation.NavViewModel
import com.mobilegame.robozzle.domain.model.Screen.donation.DonationScreenViewModel
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.red4
import com.mobilegame.robozzle.presentation.ui.Navigation.Navigator
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposable
import com.mobilegame.robozzle.presentation.ui.utils.padding.PaddingComposable
import noRippleClickable

@Composable
fun DonationScreen(navigator: Navigator, vm: DonationScreenViewModel = viewModel()) {

    BackHandler {
        NavViewModel(navigator).navigateToMainMenu( fromScreen = Screens.Donation.route )
    }

    Box(
        Modifier
            .fillMaxSize()
            .noRippleClickable { vm.logic.fold() }
    ) {
        Column {
            Box(Modifier.weight(vm.ui.header.ratios.height)) {
                header(vm)
            }
            Box(Modifier.weight(vm.ui.presentation.ratios.heightWeight) ) {
                presentation(vm)
            }
            Box(Modifier.weight(vm.ui.selector.ratios.heightWeight)) { }
        }
        Box( modifier = Modifier.fillMaxWidth() ) {
            DonationScreenSecondPart(vm)
        }
    }
}

