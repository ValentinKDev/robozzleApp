package com.mobilegame.robozzle.presentation.ui.Screen.Config.Buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.mobilegame.robozzle.domain.model.Screen.Config.ConfigScreenViewModel
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.black9

@Composable
fun SwitchOption(
    optionType: ConfigOption,
    configVM: ConfigScreenViewModel,
    startState: Boolean,
    text: String,
) {
    var switchState by remember { mutableStateOf(startState) }

    LaunchedEffect(configVM.recompositionListner) {
        configVM.handleByOptionType(optionType, switchState)
    }

    Row(
        Modifier
            .fillMaxWidth()
            .height(configVM.layout.list.sizes.rowHeightDp)
            .background(black9)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                text = text,
                color = configVM.layout.list.colors.optionText,
                fontSize = configVM.layout.list.sizes.optionTextSp,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = configVM.layout.list.sizes.rowWidthPaddingDp)
            )
            Box(
                Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = configVM.layout.list.sizes.rowWidthPaddingDp)
            ) {
                Switch(
                    checked = switchState,
                    onCheckedChange = { switchState = it }
                )
            }
        }
    }
}
