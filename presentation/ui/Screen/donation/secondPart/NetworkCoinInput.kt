package com.mobilegame.robozzle.presentation.ui.Screen.donation.secondPart

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import com.mobilegame.robozzle.domain.model.Screen.donation.DonationScreenViewModel
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposable
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposableVertically
import com.mobilegame.robozzle.presentation.ui.utils.padding.PaddingComposable
import noRippleClickable

//import noRippleClickable

@ExperimentalComposeUiApi
@Composable
fun NetworkAndNameInput(vm: DonationScreenViewModel) {
    val ctxt = LocalContext.current
    val input by remember(vm) {vm.logic.output}.collectAsState( initial = "" )
    val unfold by vm.logic.unfold.collectAsState()

//    TextField(value = , onValueChange = )
    Box(Modifier
        .clickable { vm.logic.foldUnfold() }
        .fillMaxSize()
    ) {
        if (unfold) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .clickable {
                        vm.logic.foldUnfold()
                    }
                ,
                maxLines = 1,
                value = input,
                onValueChange = { vm.logic.handleInput(it) },
                textStyle = TextStyle(
                    color = vm.data.color.addressColor
                ),
            )
        }
        else {
            Row(Modifier
                .fillMaxSize()
            ) {
                Row(modifier = Modifier.weight(7F)) {
                    PaddingComposable(
                        startPaddingRatio = 0.05F
                    ) {
                        CenterComposableVertically {
                            Text(
                                text = vm.data.text.inputField,
                                color = vm.data.color.addressColor,
                                modifier = Modifier
                            )
                        }
                    }
                }
                Row(modifier = Modifier
                    .weight(1F),
                    horizontalArrangement = Arrangement.End
                ) {
//                    if ()
                    Box(
                        Modifier
                            .fillMaxSize()
                            .noRippleClickable {
                                vm.logic.clipAndToast(ctxt)
                            }
                        ,
                    ) {
                        CenterComposable {
                            Icon(
                                imageVector = Icons.Default.ContentCopy,
                                contentDescription = "copy icone",
                                tint = vm.data.color.iconColor
                            )
                        }
                    }
                }
            }
        }
    }
}