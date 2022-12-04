package com.mobilegame.robozzle.presentation.ui.Screen.donation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.mobilegame.robozzle.domain.model.Screen.donation.DonationScreenViewModel
import com.mobilegame.robozzle.presentation.ui.utils.padding.PaddingComposable

@Composable
fun presentation(vm: DonationScreenViewModel) {
    Text (
        modifier = Modifier
            .verticalScroll(vm.logic.scroll)
            .padding(
                start = vm.ui.presentation.padding.startPadding,
                end = vm.ui.presentation.padding.endPadding,
                bottom = vm.ui.presentation.padding.bottomPadding
            )
        ,
        text = vm.ui.presentation.text.intro,
        color = vm.ui.presentation.colors.text
    )
}