package com.mobilegame.robozzle.presentation.ui.Screen.donation.secondPart

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.model.Screen.donation.DonationScreenViewModel
import com.mobilegame.robozzle.presentation.ui.Screen.Profil.register_login.ButtonRegister
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposable
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposableVertically
import com.mobilegame.robozzle.presentation.ui.utils.padding.PaddingComposable
import noRippleClickable

@ExperimentalComposeUiApi
@Composable
fun NetworkAndNameInput(vm: DonationScreenViewModel) {
    val ctxt = LocalContext.current
    val input by remember(vm) {vm.logic.input}.collectAsState( initial = "" )
    val unfold by vm.logic.unfold.collectAsState()
    infoLog("unfold", "$unfold")

    Box(Modifier
        .clickable {
            vm.logic.foldUnfold()
        }
        .fillMaxSize()
    ) {
        if (unfold) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .clickable {
                        vm.logic.foldUnfold()
//                        errorLog("clicked", "field")
                    }
                ,
//                label = "label",
                maxLines = 1,
                value = input,
                onValueChange = { vm.logic.handleInput(it) },
                textStyle = TextStyle(
                    color = vm.data.color.addressColor
                ),
                //todo: icon to specify the user can write at start ?
//                trailingIcon = {
//                }

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