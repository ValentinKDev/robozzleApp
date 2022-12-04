package com.mobilegame.robozzle.presentation.ui.Screen.donation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import com.mobilegame.robozzle.domain.model.Screen.donation.DonationScreenViewModel
import com.mobilegame.robozzle.presentation.ui.Screen.donation.secondPart.NetworkAndNameInput
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposableVertically

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun foldedBar(vm: DonationScreenViewModel) {
    Box(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(vm.ui.selector.shape.corner))
            .shadow(vm.ui.selector.shape.elevation)
            .background(vm.ui.selector.colors.background)
    ) {
        Row( Modifier
            .clickable {
                vm.logic.foldUnfold()
            }
        ) {
            CenterComposableVertically {
                NetworkAndNameInput(vm)
            }
        }
    }
}