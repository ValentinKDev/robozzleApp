package com.mobilegame.robozzle.presentation.ui.Screen.LevelsScreenByDifficulty

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.domain.model.Screen.LevelsScreenByDiff.LevelsScreenByDifficultyViewModel
import com.mobilegame.robozzle.presentation.ui.Navigation.Navigator
import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.MainScreenWindowsInfos
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import com.mobilegame.robozzle.presentation.ui.utils.tutoOverlay


@Composable
internal fun tutoSpecialOverlay(
    vm: LevelsScreenByDifficultyViewModel,
    fromScreen: Screens,
    navigator: Navigator
) {
    val visibleListState by remember {vm.visibleListState}.collectAsState()
    val visibleHeaderState by remember {vm.visibleHeaderState}.collectAsState()

    val infiniteTransition = rememberInfiniteTransition()
    val animBackgroundColor by infiniteTransition.animateColor(
        initialValue = vm.ui.levelOverview.colors.filterIntial,
        targetValue = vm.ui.levelOverview.colors.filterTarget,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 1400, easing = FastOutLinearInEasing),
            RepeatMode.Reverse
        )
    )

    tutoOverlay(vm.ui.tuto, vm.tutoVM.tuto.description, visibleHeaderState.currentState && visibleListState.currentState)
    Column() {
        Spacer(modifier = Modifier.height(vm.ui.header.sizes.heightDp))
        AnimatedVisibility(
            visibleState = visibleListState ,
            enter = vm.getEnterTransitionForList(fromScreen) ,
            exit = vm.getExitTransitionForList() ,
        ) {
                Box(Modifier) {
                    DisplayLevelOverView(vm.levelOverviewList.value[0], vm, navigator)
                    Box(
                        Modifier
                            .padding(
                                bottom = vm.ui.levelOverview.padding.bottom,
                                top = vm.ui.levelOverview.padding.top,
                                start = vm.ui.levelOverview.padding.side,
                                end = vm.ui.levelOverview.padding.side,
                            )
                            .fillMaxWidth()
                            .background(animBackgroundColor)
                            .height(vm.ui.levelOverview.sizes.heightDp)
                    ) { }
                }
            }
        }
//    }
}