package com.mobilegame.robozzle.presentation.ui.Screen.LevelsScreenByDifficulty

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.model.Screen.LevelsScreenByDiff.LevelsScreenByDifficultyViewModel
import com.mobilegame.robozzle.domain.model.Screen.LevelsScreenByDiff.MapViewParam
import com.mobilegame.robozzle.domain.model.Screen.Tuto.Tuto
import com.mobilegame.robozzle.domain.model.Screen.Tuto.Tuto.Companion.isMainScreenTutoOn
import com.mobilegame.robozzle.domain.model.Screen.Tuto.matchStep
import com.mobilegame.robozzle.domain.model.level.LevelOverView
import com.mobilegame.robozzle.presentation.res.MyColor
import com.mobilegame.robozzle.presentation.ui.Navigation.Navigator
import com.mobilegame.robozzle.presentation.ui.elements.MapView
import com.mobilegame.robozzle.presentation.ui.elements.RankingIconBouncing
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposable

@Composable
fun DisplayLevelOverView(level: LevelOverView, vm: LevelsScreenByDifficultyViewModel, navigator: Navigator, enable: Boolean = true) {
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                bottom = vm.ui.levelOverview.padding.bottom,
                top = vm.ui.levelOverview.padding.top,
                start = vm.ui.levelOverview.padding.side,
                end = vm.ui.levelOverview.padding.side,
            )
            .height(vm.ui.levelOverview.sizes.heightDp)
            .clickable {
                if (enable)
                    vm.startExitAnimationAndPressLevel(level.id)
            }
        ,
        elevation = 18.dp,
        backgroundColor = vm.ui.levelOverview.colors.backgroundColor
    ) {
        Row( modifier = Modifier.fillMaxSize() )
        {
            Box(Modifier.weight(vm.ui.levelOverview.ratios.mapWeight)) {
                vm.mapViewParamList.find { it.first == level.id } ?.let { DisplayLevelMap(it.second) }
            }
            Box(Modifier.weight(vm.ui.levelOverview.ratios.descriptionWeight)) {
                DisplayLevelDescription(level)
            }
            Box(Modifier.weight(vm.ui.levelOverview.ratios.stateIconWeight)) {
                DisplayLevelState(level, vm, navigator)
            }
            Box(Modifier.weight(vm.ui.levelOverview.ratios.rankIconWeight)) {
//                if (!(level.id == 0 && vm.tutoVM.tuto.value.matchStep(Tuto.ClickOnRankingIcon)))
                if (!(level.id == 0 && vm.tutoVM.tuto.value.isMainScreenTutoOn()))
                    DisplayRankingIcon(vm, navigator, level.id, enable)
            }
        }
    }
}

@Composable
fun DisplayRankingIcon(vm: LevelsScreenByDifficultyViewModel, navigator: Navigator, id: Int, enable: Boolean) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    when (isPressed) {
        true -> vm.rankingIconVM.rankingIconIsPressed()
        false -> vm.rankingIconVM.rankingIconIsReleased()
    }

    Box(
        if (enable)
            Modifier.clickable(
                interactionSource = interactionSource,
                indication = rememberRipple(color = Color.Transparent)
            ) {}
        else Modifier
    ) {
        CenterComposable {
            RankingIconBouncing(
                vm = vm.rankingIconVM,
                enableShadow = true,
                navigator = navigator,
                levelId = id,
//                exit = LevelsScreenByDifficultyViewModel::startExitAnimationAndPressRankingLevel
            )
        }
    }
}

@Composable
fun DisplayLevelDescription(level: LevelOverView) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        CenterComposable {
            Text(
                text = level.name,
                textAlign = TextAlign.Center,
                color = MyColor.whiteDark4,
                modifier = Modifier
                    .padding(5.dp)
                ,
            )
        }
    }
}

@Composable
fun DisplayLevelState(level: LevelOverView, vm: LevelsScreenByDifficultyViewModel, navigator: Navigator) {
    if (level.state.isWin()) {
        CenterComposable {
            Icon(
                imageVector = Icons.Outlined.Check,
                tint = MyColor.greendark3,
                contentDescription = "win",
            )
        }
    }
}

@Composable
fun DisplayLevelMap(param: MapViewParam) {
    CenterComposable {
        MapView(param)
    }
}
