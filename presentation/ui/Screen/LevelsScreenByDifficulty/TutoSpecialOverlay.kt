package com.mobilegame.robozzle.presentation.ui.Screen.LevelsScreenByDifficulty

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.domain.model.Screen.LevelsScreenByDiff.LevelsScreenByDifficultyViewModel
import com.mobilegame.robozzle.domain.model.Screen.Tuto.Tuto
import com.mobilegame.robozzle.domain.model.Screen.Tuto.matchStep
import com.mobilegame.robozzle.presentation.ui.Navigation.Navigator
import com.mobilegame.robozzle.presentation.ui.Navigation.levelTuto
import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.MainScreenWindowsInfos
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import com.mobilegame.robozzle.presentation.ui.Screen.Tuto.tutoLevel
import com.mobilegame.robozzle.presentation.ui.utils.tutoOverlay
import gradientBackground


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

    AnimatedVisibility(
        visibleState = visibleListState ,
        enter = vm.getEnterTransitionForList(fromScreen) ,
        exit = vm.getExitTransitionForList() ,
    ) {
        Column() {
            Spacer(modifier = Modifier.height(vm.ui.header.sizes.heightDp))
            tutoFakeList(vm, navigator)
        }
    }
    tutoOverlay( vm.ui.tuto, Tuto.ClickOnTutoLevel.description, visibleHeaderState.currentState && visibleListState.currentState )
    Column() {
        Spacer(modifier = Modifier.height(vm.ui.header.sizes.heightDp))
        AnimatedVisibility(
            visibleState = visibleListState ,
            enter = vm.getEnterTransitionForList(fromScreen) ,
            exit = vm.getExitTransitionForList() ,
        ) {
                Box(Modifier) {
                    if (vm.tutoVM.tuto.value.matchStep(Tuto.ClickOnTutoLevel)) {
                        DisplayLevelOverView(vm.levelOverviewList.value[0], vm, navigator)
                    }
                    Box(
                        Modifier
                            .padding(
                                bottom = vm.ui.levelOverview.padding.bottom,
                                top = vm.ui.levelOverview.padding.top,
                                start = vm.ui.levelOverview.padding.side,
                                end = vm.ui.levelOverview.padding.side,
                            )
                            .fillMaxWidth()
                            .background(if (vm.tutoVM.tuto.value.matchStep(Tuto.ClickOnTutoLevel)) animBackgroundColor else Color.Transparent)
                            .height(vm.ui.levelOverview.sizes.heightDp)
                    ) {
                        Row( modifier = Modifier.fillMaxSize() ) {
                            Box(Modifier.weight( vm.ui.levelOverview.ratios.mapWeight +vm.ui.levelOverview.ratios.descriptionWeight +vm.ui.levelOverview.ratios.stateIconWeight ))
                            Box(Modifier
                                .weight(vm.ui.levelOverview.ratios.rankIconWeight)
                                .drawBehind {
                                    drawRect(
                                        brush = Brush.radialGradient(
                                            colors = listOf(
                                                animBackgroundColor,
                                                animBackgroundColor,
                                                Color.Transparent
                                            ),
                                        ),
//                                        brush = Brush.radialGradient( colors = listOf(animBackgroundColor, animBackgroundColor, vm.ui.levelOverview.colors.filterIntial)),
                                        size = size
                                    )
                                }
                                .clickable {
                                    vm.tutoVM.nextTuto()
                                }
                            ) {
                                DisplayRankingIcon(vm, navigator, 0, true)
                            }
                        }
                    }
                }
        }
    }
//    }
}