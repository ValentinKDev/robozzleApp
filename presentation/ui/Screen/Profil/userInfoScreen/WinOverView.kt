package com.mobilegame.robozzle.presentation.ui.Screen.Profil.userInfoScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.Player.LevelWin
import com.mobilegame.robozzle.domain.model.Screen.NavViewModel
import com.mobilegame.robozzle.domain.model.Screen.userInfoScreen.UserInfoScreenDimensions
import com.mobilegame.robozzle.domain.model.Screen.utils.RankingIconViewModel
import com.mobilegame.robozzle.domain.model.User.UserInfosScreenViewModel
import com.mobilegame.robozzle.presentation.res.*
import com.mobilegame.robozzle.presentation.ui.Navigator
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import com.mobilegame.robozzle.presentation.ui.elements.MapView
import com.mobilegame.robozzle.presentation.ui.elements.RankingIconBouncing
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposable
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposableVertically
import com.mobilegame.robozzle.presentation.ui.utils.padding.PaddingComposable

@Composable
fun DisplayWinOverView(levelWin: LevelWin, navigator: Navigator, levelMap: List<String>, vm: UserInfosScreenViewModel) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    when (isPressed) {
        true -> vm.logic.rankingIconVM.rankingIconIsPressed()
        false -> vm.logic.rankingIconVM.rankingIconIsReleased(navigator, levelWin.levelId)
    }

    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .padding(
                top = vm.dimension.winOverViewDimensions[UserInfoScreenDimensions.WinOverViewDimensions.PaddingTop]?.dp
                    ?: 0.dp,
//                bottom = 6.dp,
//                start = 6.dp,
//                end = 6.dp,
            )
            .fillMaxWidth()
            .height(150.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = rememberRipple(color = Color.Transparent)
            ) {
                infoLog("clickable", "ranking icon")
            }
        ,
        elevation = 8.dp,
        backgroundColor = grayDark3
    ) {
        Column( modifier = Modifier.fillMaxSize()
        ) {
            Box( modifier = Modifier
                .fillMaxWidth()
//                .wrapContentHeight()
                .weight(0.1F)
            ) {
                TextWithShadow(text = "level ${levelWin.levelId}", modifier = Modifier.align(Center))
//                Text(
//                    text ="level ${levelWin.levelId}",
//                    color = whiteDark4,
//                    modifier = Modifier
//                        .align(Alignment.Center)
//                        .shadow(elevation = 2.dp)
//                )
            }
            Row(
                Modifier
//                    .wrapContentHeight()
                    .wrapContentWidth()
                    .weight(0.3F)
                ,
            ) {
//                CenterComposableVertically {
                CenterComposable {
                    MapView(
                        widthInt = vm.dimension.mapOverViewSize,
                        map = levelMap,
                    )
                }
            }
//            Box(Modifier.fillMaxWidth()) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .weight(0.2F)
//                .background(greendark7)
                ,
                verticalArrangement = Arrangement.Bottom
            ) {
                PaddingComposable(
                    verticalPadding = 0.07F,
                    horizontalPadding = 0.05F,
                ) {
                    Row(Modifier
                        .fillMaxWidth()
                    ) {
                        Row (
                            Modifier
//                        .wrapContentSize()
                                .wrapContentHeight()
                                .weight(6F)
                            ,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Column() {
                                Box() {
                                    Text("level ${levelWin.levelName}", color = whiteDark4, )
                                }
                                Box() {
                                    Text( "instructions ${levelWin.winDetails.instructionsNumber}", color = whiteDark4, )
                                }
                            }
                        }
                        Row( modifier = Modifier
                            .wrapContentHeight()
                            .weight(1F)
                            .clickable(
                                interactionSource = interactionSource,
                                indication = rememberRipple(color = Color.Transparent)
                            ) {
                                infoLog("clickable", "ranking icon")
                            }
                            ,
                            horizontalArrangement = Arrangement.End
                        ) {
                            RankingIconBouncing(sizeAtt = 35, rankingIconVM = vm.logic.rankingIconVM, isPressed = isPressed)
                        }
                    }
                }
            }
        }
    }
}
