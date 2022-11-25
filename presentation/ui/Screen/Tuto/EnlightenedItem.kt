package com.mobilegame.robozzle.presentation.ui.Screen.Tuto

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.presentation.res.MyColor
import com.mobilegame.robozzle.presentation.res.mapCaseColorList
import gradientBackground

@Composable
fun enlightItem(modifier: Modifier, ui: TutoObj) {

    val infiniteTransition = rememberInfiniteTransition()
//    val animBackgroundColor3 by infiniteTransition.animateColor(
////        initialValue = ui.colors.enlightAnyInit,
////        initialValue = ui.colors.transparent,
//        initialValue = MyColor.darkgray3Enlight,
////        targetValue = MyColor.white9,
//        targetValue = MyColor.gray9,
//        animationSpec = infiniteRepeatable(
//            tween(durationMillis = 1400, easing = FastOutLinearInEasing),
//            RepeatMode.Reverse
//        )
//    )
//    val animBackgroundColor2 by infiniteTransition.animateColor(
////        initialValue = ui.colors.enlightAnyInit,
//        initialValue = MyColor.darkgray2Enlight,
////        initialValue = ui.colors.transparent,
////        targetValue = MyColor.white7,
//        targetValue = MyColor.gray5,
////        targetValue = ui.colors.enlightAnyTarget,
//        animationSpec = infiniteRepeatable(
//            tween(durationMillis = 1400, easing = FastOutLinearInEasing),
//            RepeatMode.Reverse
//        )
//    )
//    val animBackgroundColor by infiniteTransition.animateColor(
////        initialValue = ui.colors.enlightAnyInit,
//        initialValue = MyColor.darkgray1Enlight,
////        targetValue = MyColor.white5,
//        targetValue = MyColor.gray3,
////        targetValue = ui.colors.enlightAnyTarget,
//        animationSpec = infiniteRepeatable(
//            tween(durationMillis = 1400, easing = FastOutLinearInEasing),
//            RepeatMode.Reverse
//        )
//    )

    val animWhite by infiniteTransition.animateColor(
//        initialValue = ui.colors.enlightAnyInit,
        initialValue = Color.Transparent,
        targetValue = MyColor.white8,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 1400, easing = FastOutLinearInEasing),
            RepeatMode.Reverse
        )
    )

    Box(
        Modifier
//            .gradientBackground(listOf(animBackgroundColor3, animBackgroundColor2, animBackgroundColor), 175F)
//            .gradientBackground(listOf(animBackgroundColor, animBackgroundColor2, animBackgroundColor3), 175F)
            .background(animWhite)
            .then(modifier)
    ) { }
//    var str = ""
//    mapCaseColorList('g').forEach {
//        str += it.toHexCode()
//        str += ", "
//    }
//    errorLog("enlightItem", "mapColor = $str")
//    Color.Blue
}

private fun Color.toHexCode(): String {
    val alpha = this.alpha * 255
    val red = this.red * 255
    val green = this.green * 255
    val blue = this.blue * 255
    return String.format("#%02x#%02x%02x%02x", alpha.toInt() ,red.toInt(), green.toInt(), blue.toInt())
}

