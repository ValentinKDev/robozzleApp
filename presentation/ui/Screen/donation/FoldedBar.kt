package com.mobilegame.robozzle.presentation.ui.Screen.donation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.domain.model.Screen.donation.DonationScreenViewModel
import com.mobilegame.robozzle.presentation.res.MyColor
import com.mobilegame.robozzle.presentation.ui.Screen.donation.secondPart.NetworkAndNameInput
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposableVertically

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun foldedBar(vm: DonationScreenViewModel) {
    Box(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(vm.ui.selector.foldedBar.shape.corner))
            .shadow(vm.ui.selector.foldedBar.shape.elevation)
            .background(vm.ui.selector.foldedBar.colors.background)
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