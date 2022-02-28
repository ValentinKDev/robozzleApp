package com.mobilegame.robozzle.presentation.ui.Screen.donation

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.domain.model.Screen.donation.DonationScreenViewModel

@Composable
fun DonationScreenFirstPart() {
    Row(
        Modifier
            .fillMaxWidth()
    ) {
        Column( Modifier
            .fillMaxWidth()
        ) {
            Box(Modifier.align(Alignment.CenterHorizontally)) {
                Text( text = "donation screen")
            }
            Box(
                Modifier.padding(
                    top = 20.dp,
                    bottom = 10.dp,
                    start = 5.dp,
                    end = 5.dp,
                )
            ) {
                Text (DonationScreenViewModel().data.loremIpsum)
            }
        }
    }
}
