package com.mobilegame.robozzle.presentation.ui.elements

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.domain.model.Screen.LevelsScreenByDiff.LevelsScreenByDifficultyViewModel
import com.mobilegame.robozzle.domain.model.Screen.utils.RankingIconViewModel
import com.mobilegame.robozzle.presentation.ui.Navigation.Navigator
import gradientBackground
import kotlin.reflect.KFunction2

@Composable
fun RankingIconBouncing(
    vm: RankingIconViewModel,
    enableShadow: Boolean,
    navigator: Navigator,
    levelId: Int,
) {
    Box(
         Modifier
            .width(vm.gui.width.dp)
            .height(vm.gui.boxHeight.dp)
    ) {
        Row {
            Column(
                modifier = Modifier.height(vm.gui.height.dp).weight(1F),
                verticalArrangement = Arrangement.Bottom
            ) {
                animCol( color = ColumColor.Green, vm = vm, enableShadow = enableShadow ) {
                    vm.finisherAction(type = ColumColor.Green, navigator = navigator, levelId = levelId)
                }
            }
            Column(
                modifier = Modifier.height(vm.gui.height.dp).weight(1.1F),
                verticalArrangement = Arrangement.Bottom
            ) {
                animCol( color = ColumColor.Blue, vm = vm, enableShadow = enableShadow ) {
                    vm.finisherAction(type = ColumColor.Blue, navigator = navigator, levelId = levelId)
                }
            }
            Column(
                modifier = Modifier.height(vm.gui.height.dp).weight(1F),
                verticalArrangement = Arrangement.Bottom
            ) {
                animCol( color = ColumColor.Red, vm = vm, enableShadow = enableShadow ) {
                    vm.finisherAction(type = ColumColor.Red, navigator = navigator, levelId = levelId)
                }
            }
        }
    }
}

@Composable
fun animCol(
    color: ColumColor,
    vm: RankingIconViewModel,
    enableShadow: Boolean,
    finisher: (Dp) -> Unit
) {
    val animatedHeight: Dp by animateDpAsState(
        targetValue = vm.getTargetHeightByColor(color),
        animationSpec = vm.getAnimSpecByColor(color),
        finishedListener = finisher
    )

    Column( modifier = Modifier
        .height(animatedHeight)
        .fillMaxWidth()
        .gradientBackground(vm.getColors(color), 0F)
        .graphicsLayer { shadowElevation = vm.getGraphicsLayersByColors(color, enableShadow).toPx() }
        ,
    ) { }
}

enum class ColumColor {
    Red, Blue, Green
}

