package com.mobilegame.robozzle.presentation.ui.Screen.donation

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobilegame.robozzle.domain.model.Screen.Navigation.NavViewModel
import com.mobilegame.robozzle.domain.model.Screen.donation.DonationScreenViewModel
import com.mobilegame.robozzle.presentation.res.MyColor
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
            Row(modifier = Modifier
                .weight(vm.ui.header.ratios.height)
                .fillMaxWidth()
            ) {
                CenterComposable {
                    Text(
                        text = vm.ui.header.text.line,
                        color = vm.ui.header.colors.text,
                        fontSize = vm.ui.header.sizes.textSp,
                    )
                }
            }
            Box(
                Modifier
                    .weight(vm.ui.presentation.ratios.heightWeight)
            ) {
                PaddingComposable(
                    horizontalPadding = vm.ui.presentation.paddings.horizontalPadding,
                ) {
                    Text (
                        modifier = Modifier.verticalScroll(vm.logic.scroll),
                        text = vm.data.text.intro,
                        textAlign = TextAlign.Center,
                        color = vm.data.color.textColor
                    )
                }
            }
            Box(Modifier.weight(vm.ui.selector.ratios.heightWeight)) { }
        }
        Column(Modifier.fillMaxSize()) {
            Box( modifier = Modifier.fillMaxWidth() )
            {
                DonationScreenSecondPart(vm)
            }
        }
    }
}

