package com.mobilegame.robozzle.presentation.ui.Screen.donation

import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.mobilegame.robozzle.domain.model.Screen.donation.DonationScreenViewModel
import com.mobilegame.robozzle.presentation.ui.utils.padding.PaddingComposable

@Composable
fun presentation(vm: DonationScreenViewModel) {
    PaddingComposable(
        startPaddingRatio = vm.ui.presentation.ratios.startPadding,
        endPaddingRatio = vm.ui.presentation.ratios.endPadding
//        horizontalPadding = vm.ui.presentation.paddings.horizontalPadding,
    ) {
        Text (
            modifier = Modifier.verticalScroll(vm.logic.scroll),
            text = vm.data.text.intro,
//            textAlign = TextAlign.Center,
            color = vm.data.color.textColor
        )
    }
}