package com.mobilegame.robozzle.presentation.ui.Screen.RanksLevel

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.mobilegame.robozzle.domain.model.Screen.RanksLevelScreenViewModel
import com.mobilegame.robozzle.presentation.res.MyColor
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposable

@Composable
internal fun header(vm: RanksLevelScreenViewModel) {
    vm.level?.let {
        CenterComposable {
            Text(
                text = vm.ui.header.text.str + it.name,
                textAlign = TextAlign.Center,
//                fontWeight = FontWeight(5),
//                fontWeight = 0.4F,
                fontSize = vm.ui.header.sizes.textSp,
                color = vm.ui.header.colors.text,
            )
        }
    }
}