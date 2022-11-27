package com.mobilegame.robozzle.presentation.ui.Screen.Config.Buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.domain.model.Screen.Config.ConfigScreenViewModel
import com.mobilegame.robozzle.presentation.res.MyColor
import com.mobilegame.robozzle.presentation.ui.utils.padding.PaddingComposable

@Composable
fun ButtonOption(
    configVM: ConfigScreenViewModel,
    text: String,
    clickable: Modifier
) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(configVM.layout.list.sizes.rowHeightDp)
            .background(MyColor.black9)
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
            Box (
                Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = configVM.layout.list.sizes.rowWidthPaddingDp)
                    .clip(RoundedCornerShape(5.dp))
                    .height(configVM.layout.button.sizes.heightDp)
                    .width(configVM.layout.button.sizes.widthDp)
                    .background(MaterialTheme.colors.secondaryVariant)
                    .then(clickable)
            ) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(configVM.layout.button.colors.shadow)
                )
                PaddingComposable(
                    topPaddingRatio = configVM.layout.button.padding.topLight,
                    bottomPaddingRatio = configVM.layout.button.padding.bottomLight,
                    startPaddingRatio = configVM.layout.button.padding.startLight,
                    endPaddingRatio = configVM.layout.button.padding.endLight,
                ) {
                    Box (
                        Modifier
                            .fillMaxSize()
                            .align(Alignment.Center)
                            .clip(RoundedCornerShape(5.dp))
                            .background(configVM.layout.button.colors.light)
                    )
                }
            }
        }
    }
}