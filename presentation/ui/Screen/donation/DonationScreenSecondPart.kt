package com.mobilegame.robozzle.presentation.ui.Screen.donation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobilegame.robozzle.domain.model.Screen.donation.DonationScreenViewModel

@ExperimentalAnimationApi
@Composable
fun DonationScreenSecondPart(vm: DonationScreenViewModel) {
    val ctxt = LocalContext.current

    Column( modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .background(Color.Gray)
        .padding(top = 15.dp)
        ,
    ) {
        Column( modifier = Modifier
            .fillMaxHeight()
            .width(300.dp)
            .align(Alignment.CenterHorizontally)
        ) {
            Row( modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
            ) {
                /** Column to place scrolling bar */
                Column( modifier = Modifier
                    .fillMaxHeight()
                    .weight(8F)
                ) {
                    FoldableScrollingBar(vm = vm)
                }
                /** Column to place the copy button */
                Column( modifier = Modifier
                    .fillMaxHeight()
                    .weight(1F)
                ) {
                    IconButton(
                        modifier = Modifier
                            .height(40.dp)
                            .fillMaxWidth()
                        ,
                        onClick = {
                            vm.logic.clipAndToast(ctxt)
                        },
                    ) {
                        Icon(imageVector = Icons.Default.ContentCopy, contentDescription = "copy icone", tint = Color.Black)
                    }
                }
            }
        }
    }
}
