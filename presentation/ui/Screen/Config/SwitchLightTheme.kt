package com.mobilegame.robozzle.presentation.ui.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.mobilegame.robozzle.analyse.verbalLog
import com.mobilegame.robozzle.domain.model.Screen.Config.ConfigScreenViewModel
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.black8

@Composable
fun SwitchLightTheme(configVM: ConfigScreenViewModel) {
    val lightThemeState by remember { configVM.lightThemeState }.collectAsState()

    LaunchedEffect(configVM.recompositionListner) {
        configVM.noLightTheme()
    }

    Row(
        Modifier
            .fillMaxWidth()
            .height(configVM.layout.list.sizes.rowHeightDp)
            .background(black8)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = configVM.layout.list.texts.lightThemeLine,
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
                    checked = lightThemeState,
                    onCheckedChange = { configVM.setSwitchDarkThemStateTo(it) })
            }
        }
    }

}
