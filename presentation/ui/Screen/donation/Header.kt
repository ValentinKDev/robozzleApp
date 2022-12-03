package com.mobilegame.robozzle.presentation.ui.Screen.donation

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mobilegame.robozzle.domain.model.Screen.donation.DonationScreenViewModel
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposable

@Composable
internal fun header(vm: DonationScreenViewModel) {
    Row(modifier = Modifier
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
}