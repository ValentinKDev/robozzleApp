package com.mobilegame.robozzle.presentation.ui.Screen.Config

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mobilegame.robozzle.R
import com.mobilegame.robozzle.domain.model.Screen.Config.ConfigScreenViewModel
import com.mobilegame.robozzle.presentation.res.MyColor
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposableHorizontally
import com.mobilegame.robozzle.presentation.ui.utils.GifImage

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PopupConfig(vm: ConfigScreenViewModel) {

    val visiblePopup by remember { vm.visiblePopup }.collectAsState()
    val content by remember { vm.showContent }.collectAsState()

    Column(Modifier.fillMaxSize()) {
        AnimatedVisibility(
            visible = visiblePopup,
            enter = fadeIn( animationSpec = tween(1400) ) ,
            exit = fadeOut( animationSpec = tween(if (content == 5 || content == 9) 2600 else 1300) )
        ) {
            Column(
                Modifier
                    .padding(20.dp)
                    .fillMaxSize(), verticalArrangement = Arrangement.Bottom) {
                CenterComposableHorizontally {
                    when (content) {
                        5 -> GifImage(data = R.drawable.captain)
                        9 -> GifImage(data = R.drawable.jordan)
                        else -> Card(
                            shape = RoundedCornerShape(10.dp),
                            backgroundColor = MyColor.grayDark2,
                            modifier = Modifier
                                .padding(5.dp),
                        ){
                            AnimatedContent(
                                targetState = content,
                                transitionSpec = {
                                    fadeIn() with fadeOut()
                                }
                            ) {
                                Text(
                                    modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp),
                                    fontSize = 15.sp,
                                    text = vm.getRoast(),
                                    color = MyColor.whiteDark3,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}