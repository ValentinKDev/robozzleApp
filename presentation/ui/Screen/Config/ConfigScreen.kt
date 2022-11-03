package com.mobilegame.robozzle.presentation.ui.Screen.Config

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.domain.model.Screen.Config.ConfigScreenViewModel
import com.mobilegame.robozzle.presentation.ui.Navigation.Navigator
import com.mobilegame.robozzle.presentation.ui.Screen.Config.Buttons.ConfigOption
import com.mobilegame.robozzle.presentation.ui.Screen.Config.Buttons.SwitchOption
import com.mobilegame.robozzle.presentation.ui.Screen.SwitchLightTheme
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposable

@Composable
fun ConfigScreen(navigator: Navigator, vm: ConfigScreenViewModel = viewModel()) {

    LaunchedEffect(true) {
        errorLog("ConfigScreen", "Start")
    }

    Column(Modifier.fillMaxSize()) {
        Row(
            Modifier
                .weight(vm.layout.header.ratios.height)
                .fillMaxWidth()
        ) {
            CenterComposable {
                Text(
                    text = vm.layout.header.text.line,
                    color = vm.layout.header.colors.text,
                    fontSize = vm.layout.header.sizes.textSp,
                )
            }
        }
        Column(
            Modifier
                .weight(vm.layout.list.ratios.height)
                .fillMaxWidth()
        ) {
            SwitchLightTheme(
                configVM = vm
            )
            SwitchOption(
                optionType = ConfigOption.ToTrash,
                configVM = vm,
                startState = vm.switchToTrash,
                text = vm.layout.list.texts.dragAndDropToTrash,
            )
            SwitchOption(
                optionType = ConfigOption.DisplayWinLevel,
                configVM = vm,
                startState = vm.switchToDisplayLevelWin,
                text = vm.layout.list.texts.displayLevelWin
            )
        }
    }
    //todo: afficher les niveaux terminé
}
