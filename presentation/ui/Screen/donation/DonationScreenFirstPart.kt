package com.mobilegame.robozzle.presentation.ui.Screen.donation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.domain.model.Screen.donation.DonationScreenViewModel
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposable
import com.mobilegame.robozzle.presentation.ui.utils.padding.PaddingComposable

@Composable
fun DonationScreenFirstPart(vm: DonationScreenViewModel) {
    Row( Modifier .fillMaxWidth()
    ) {
        Column( Modifier .fillMaxWidth()
        ) {
            Box(Modifier.weight(vm.data.ratios.headerWeight)) {
                CenterComposable {
                    Text(
                        text = vm.data.text.header,
                        modifier = Modifier.align(Alignment.Center),
                        color = vm.data.color.textColor
                    )
                }
            }
            Box(Modifier.weight(vm.data.ratios.introWeight)) {
                PaddingComposable(
                    verticalPadding = vm.data.ratios.introVerticalPadding,
                    horizontalPadding = vm.data.ratios.introHorizontalPadding,
                ) {
                    Text (
                        modifier = Modifier.verticalScroll(vm.logic.scroll),
                        text = DonationScreenViewModel().data.text.intro,
                        color = vm.data.color.textColor
                    )
                }
            }
        }
    }
}
