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
import com.mobilegame.robozzle.domain.model.LevelsScreenByDiff.LevelsScreenByDifficultyViewModel
import com.mobilegame.robozzle.domain.model.LevelsScreenByDiff.MapViewParam
import com.mobilegame.robozzle.domain.model.Screen.utils.RankingIconViewModel
import com.mobilegame.robozzle.domain.model.level.LevelOverView
import com.mobilegame.robozzle.presentation.res.MyColor
import com.mobilegame.robozzle.presentation.ui.Navigation.Navigator
import com.mobilegame.robozzle.presentation.ui.elements.MapView
import com.mobilegame.robozzle.presentation.ui.elements.RankingIconBouncing
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposable

@Composable
fun DisplayLevelOverView(level: LevelOverView, vm: LevelsScreenByDifficultyViewModel, rankingIconVM: RankingIconViewModel, navigator: Navigator) { val ctxt = LocalContext.current
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .padding(
                bottom = 6.dp,
                top = 6.dp,
                start = 6.dp,
                end = 6.dp,
            )
            .fillMaxWidth()
            .height(100.dp)
            .clickable {
                vm.startExitAnimationAndPressLevel(level.id)
            }
        ,
        elevation = 18.dp,
        backgroundColor = MyColor.grayDark3
    ) {
        Row( modifier = Modifier.fillMaxSize() )
        {
            Box(Modifier.weight(1.2f)) { vm.mapViewParamList.find { it.first == level.id } ?.let { DisplayLevelMap(it.second) } }
            Box(Modifier.weight(1.6f)) { DisplayLevelDescription(level) }
            Box(Modifier.weight(0.5f)) { DisplayLevelState(level, vm, navigator) }
            Box(Modifier.weight(0.7f)) { DisplayRankingIcon(rankingIconVM, navigator, level.id)}
        }
    }
}

@Composable
fun DisplayRankingIcon(rankingIconVM: RankingIconViewModel, navigator: Navigator, id: Int) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    when (isPressed) {
        true -> rankingIconVM.rankingIconIsPressed()
        false -> rankingIconVM.rankingIconIsReleased()
    }

    Box( modifier = Modifier
        .fillMaxSize()
        .clickable(
            interactionSource = interactionSource,
            indication = rememberRipple(color = Color.Transparent)
        ) {
            infoLog("clickable", "ranking icon")
        }
    ) {
        CenterComposable {
            RankingIconBouncing(
                vm = rankingIconVM,
                enableShadow = true,
                navigator = navigator,
                levelId = id
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
