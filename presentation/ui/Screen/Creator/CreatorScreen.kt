package com.mobilegame.robozzle.presentation.ui.Screen.Creator

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.outlined.North
//import androidx.compose.material.icons.outlined.Update
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.analyse.verbalLog
import com.mobilegame.robozzle.domain.model.Screen.Navigation.NavViewModel
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.green10
import com.mobilegame.robozzle.presentation.ui.Navigation.Navigator
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposable


@Composable
fun CreatorScreen(navigator: Navigator) {
    BackHandler {
        NavViewModel(navigator).navigateToMainMenu(fromScreen = Screens.Donation.route)
    }
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    var animIcon by remember { mutableStateOf(false) }

    when (isPressed) {
        true -> {
            animIcon = true
        }
        false -> {
            animIcon = false
        }
    }

    Column(Modifier.fillMaxSize()) {
        Button(onClick = {
            animIcon = true
        }) {
            Text(text = "Flash")
        }
        Spacer(modifier = Modifier.size(100.dp))
        Box(
            Modifier
                .wrapContentSize()
//                .size(150.dp)
                .background(green10)
                .clickable(
                    interactionSource = interactionSource,
                    indication = rememberRipple(color = Color.Transparent)
                ) { infoLog("clickable", "ranking icon") }
        ) {
            CenterComposable {
                animCol(isPressed = isPressed, animState = animIcon, height = 300F ) {
                    errorLog("aniCol", "******* animFinisher *******")
                }
            }
        }
    }
}

@Composable
fun animCol(animState: Boolean, isPressed: Boolean, height: Float, animFinished: (Dp) -> Unit):Dp {
    val animatedHeight: Dp by animateDpAsState(
        targetValue = when {
            animState && isPressed -> height.dp
            else -> (0.5F * height).dp
        },
        animationSpec = when {
            animState && isPressed-> { spring(dampingRatio = Spring.DampingRatioLowBouncy) }
            else -> { spring(dampingRatio = 0.18F, stiffness = Spring.StiffnessMedium) }
        },
        finishedListener = animFinished
    )
    Column( modifier = Modifier
        .height(animatedHeight)
        .width(10.dp)
        .background(Color.Blue)
        ,
    ) { }
    return animatedHeight
}