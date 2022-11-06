package com.mobilegame.robozzle.presentation.ui.Screen.Config

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.domain.model.Screen.Config.ConfigScreenViewModel
import com.mobilegame.robozzle.domain.model.Screen.Navigation.NavViewModel
import com.mobilegame.robozzle.presentation.ui.Navigation.Navigator
import com.mobilegame.robozzle.presentation.ui.Screen.Config.Buttons.ConfigOption
import com.mobilegame.robozzle.presentation.ui.Screen.Config.Buttons.SwitchOption
import com.mobilegame.robozzle.presentation.ui.Screen.LevelsScreenByDifficulty.LevelsScreenByDifficultyHeader
import com.mobilegame.robozzle.presentation.ui.Screen.LevelsScreenByDifficulty.LevelsScreenByDifficultyList
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import com.mobilegame.robozzle.presentation.ui.Screen.SwitchLightTheme
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposable

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ConfigScreen(navigator: Navigator, vm: ConfigScreenViewModel = viewModel()) {

    LaunchedEffect(true) {
        vm.animVM.setVisible()
        errorLog("ConfigScreen", "Start")
    }

    val visibleHeaderState by remember {vm.animVM.visibleHeaderState}.collectAsState()
    val visibleListState by remember {vm.animVM.visibleListState}.collectAsState()

    BackHandler {
        vm.startExitAnimationAndPressBack()
    }

    vm.goingMainMenuListener(navigator)


    Column(Modifier.fillMaxSize()) {
        Row(modifier = Modifier
            .weight(vm.layout.header.ratios.height)
            .fillMaxWidth()) {
            AnimatedVisibility(
                visibleState = visibleHeaderState ,
                enter =  fadeIn(),
                exit = fadeOut()
            ) {
                    CenterComposable {
                        Text(
                            text = vm.layout.header.text.line,
                            color = vm.layout.header.colors.text,
                            fontSize = vm.layout.header.sizes.textSp,
                        )
                    }
            }
        }
        Column(
            Modifier
                .weight(vm.layout.list.ratios.height)
                .fillMaxWidth()
        ) {
                AnimatedVisibility(
                    visibleState = visibleListState,
                    enter = expandVertically(),
                    exit = shrinkVertically()
//                    expandVertically(expandFrom = Alignment.Top)
                ) {
                    Column {

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
        }
    }
    //todo: afficher les niveaux terminé
}
