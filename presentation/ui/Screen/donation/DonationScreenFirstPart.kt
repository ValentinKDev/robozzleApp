package com.mobilegame.robozzle.presentation.ui.Screen.donation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.mobilegame.robozzle.domain.model.Screen.donation.DonationScreenViewModel
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposable
import com.mobilegame.robozzle.presentation.ui.utils.padding.PaddingComposable

@Composable
fun DonationScreenFirstPart(vm: DonationScreenViewModel) {
    Row( Modifier .fillMaxWidth()
    ) {
        Column( Modifier .fillMaxWidth()
        ) {
//            Box(Modifier.weight(vm.data.ratios.headerWeight)) {
            Box(Modifier.weight(vm.ui.header.ratios.heightWeight)) {
                CenterComposable {
                    Text(
//                        text = vm.data.text.header,
                        text = vm.ui.header.text.str,
                        modifier = Modifier.align(Alignment.Center),
//                        color = vm.data.color.textColor
                        color = vm.ui.header.colors.text,
//                        fontSize = ,
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
                        text = vm.data.text.intro,
                        color = vm.data.color.textColor
                    )
                }
            }
        }
    }
}
