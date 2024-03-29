package com.mobilegame.robozzle.presentation.ui.Screen.Profil.userInfoScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.Player.LevelWin
import com.mobilegame.robozzle.domain.model.Screen.LevelsScreenByDiff.MapViewParam
import com.mobilegame.robozzle.domain.model.Screen.Navigation.NavViewModel
import com.mobilegame.robozzle.domain.model.Screen.utils.PressAnimationState
import com.mobilegame.robozzle.domain.model.User.UserInfosScreenViewModel
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.grayDark3
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.whiteDark4
import com.mobilegame.robozzle.presentation.ui.Navigation.Navigator
import com.mobilegame.robozzle.presentation.ui.elements.MapView
import com.mobilegame.robozzle.presentation.ui.elements.RankingIconBouncing
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposable
import com.mobilegame.robozzle.presentation.ui.utils.TextWithShadow
import com.mobilegame.robozzle.presentation.ui.utils.padding.PaddingComposable

@Composable
fun DisplayWinOverView(levelWin: LevelWin, navigator: Navigator, levelMap: List<String>, vm: UserInfosScreenViewModel) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val animRankingIcon by remember { vm.logic.rankingIconVM.animationState }.collectAsState()
    if (animRankingIcon == PressAnimationState.Finished) { NavViewModel(navigator).navigateToRanksLevel(levelWin.lvl_id.toString()) }

    when (isPressed) {
        true -> vm.logic.rankingIconVM.rankingIconIsPressed()
        false -> vm.logic.rankingIconVM.rankingIconIsReleased()
    }

    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .height(vm.uiData.thirdPart.winOverView.heightDp)
            .width(vm.uiData.thirdPart.winOverView.widthDp)
            .padding(
                top = vm.uiData.thirdPart.winOverView.topPaddingDp,
                bottom = vm.uiData.thirdPart.winOverView.bottomPaddingDp,
                start = vm.uiData.thirdPart.winOverView.startPaddingDp,
                end = vm.uiData.thirdPart.winOverView.endPaddingDp,
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { infoLog("clickable", "ranking icon") }
        ,
        elevation = 8.dp,
        backgroundColor = grayDark3
    ) {
        Column( modifier = Modifier.fillMaxSize()
        ) {
            Box( modifier = Modifier
                .fillMaxWidth()
                .weight(0.1F)
            ) {
                TextWithShadow(text = "level ${levelWin.lvl_id}", modifier = Modifier.align(Center))
            }
            Row(
                Modifier
                    .wrapContentWidth()
                    .weight(0.3F)
            ) {
                CenterComposable {
                    MapView(
                        MapViewParam(
                            levelMap,
                            vm.uiData.thirdPart.winOverView.mapWidthInt
                        )
                    )
                }
            }
            Column(
                Modifier
                    .fillMaxWidth()
                    .weight(0.2F)
                ,
                verticalArrangement = Arrangement.Bottom
            ) {
                PaddingComposable(
                    verticalPadding = 0.07F,
                    horizontalPadding = 0.05F,
                ) {
                    Row(Modifier .fillMaxWidth()
                    ) {
                        Row (
                            Modifier
                                .wrapContentHeight()
                                .weight(6F)
                            ,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Column() {
                                Box() {
                                    Text("level ${levelWin.lvl_name}", color = whiteDark4, )
                                }
                                Box() {
                                    Text( "instructions ${levelWin.details.instructionsNumber}", color = whiteDark4, )
                                }
                            }
                        }
                        Row( modifier = Modifier
                            .wrapContentHeight()
                            .weight(1F)
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null
                            ) {
                                infoLog("clickable", "ranking icon")
                            }
                            ,
                            horizontalArrangement = Arrangement.End
                        ) {
                            RankingIconBouncing(
                                vm = vm.logic.rankingIconVM,
                                enableShadow = false,
                                navigator = navigator,
                                levelId = levelWin.lvl_id
                            )
                        }
                    }
                }
            }
        }
    }
}
