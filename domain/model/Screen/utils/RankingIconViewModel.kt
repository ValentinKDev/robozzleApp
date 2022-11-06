package com.mobilegame.robozzle.domain.model.Screen.utils

import androidx.compose.animation.core.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.analyse.verbalLog
import com.mobilegame.robozzle.domain.model.Screen.Navigation.NavViewModel
import com.mobilegame.robozzle.presentation.res.MyColor
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.redDark8
import com.mobilegame.robozzle.presentation.ui.Navigation.Navigator
import com.mobilegame.robozzle.presentation.ui.elements.ColumColor
import kotlinx.coroutines.flow.MutableStateFlow

class RankingIconViewModel(): ViewModel() {

    lateinit var gui : RankingIconPresentationData
    fun create(height: Int): RankingIconViewModel {
        gui = RankingIconPresentationData(boxHeight = height)
        return this
    }

    private val _animationState = MutableStateFlow<Boolean>(false)
    private fun isAnimated(): Boolean = _animationState.value
    fun changeAnimationStateTo(state: Boolean) { _animationState.value = state }

    private val finisherRed = mutableStateOf(false)
    private val finisherBlue = mutableStateOf(false)
    private val finisherGreen = mutableStateOf(false)
    private fun isAnimationFinished(): Boolean = finisherBlue.value && finisherRed.value && finisherGreen.value

    fun rankingIconIsReleased() { changeAnimationStateTo(false) }
    fun rankingIconIsPressed() {
        changeAnimationStateTo(true)
    }

    fun finisherAction(type: ColumColor, navigator: Navigator, levelId: Int): (Dp) -> Unit {
        when (type) {
            ColumColor.Red -> {
                infoLog("RankingViewModel::finisherAction", "AnimationFinished Red")
                finisherRed.value = true
            }
            ColumColor.Blue -> {
                infoLog("RankingViewModel::finisherAction", "AnimationFinished Blue")
                finisherBlue.value = true
            }
            ColumColor.Green -> {
                infoLog("RankingViewModel::finisherAction", "AnimationFinished Green")
                finisherGreen.value = true
            }
        }
        if ( isAnimationFinished()) {
            verbalLog("RankingViewModel::finisherAction", "AnimationFinished")
            NavViewModel(navigator).navigateToRanksLevel(levelId.toString())
        }
        return {}
    }
    fun getTargetHeightByColor(type: ColumColor): Dp {
        return when (type) {
            ColumColor.Red -> { when {
                isAnimated() -> gui.redTargetHeight
                else -> gui.redHeight
            } }
            ColumColor.Blue -> { when {
                isAnimated() -> gui.blueTargetHeight
                else -> gui.blueHeight
            } }
            ColumColor.Green -> { when {
                isAnimated() -> gui.greenTargetHeight
                else -> gui.greenHeight
            } }
        }
    }
    fun getAnimSpecByColor (type: ColumColor): AnimationSpec<Dp> {
        return when (type) {
            ColumColor.Red -> when {
                isAnimated() -> { spring(dampingRatio = Spring.DampingRatioLowBouncy) }
                else -> { spring(dampingRatio = 0.18F, stiffness = Spring.StiffnessMedium) }
            }
            ColumColor.Blue -> when {
//                isAnimated() -> spring(dampingRatio = Spring.DampingRatioLowBouncy, stiffness = Spring.StiffnessHigh)
//                else -> { spring(dampingRatio = 0.25F, stiffness = Spring.StiffnessLow) }
                isAnimated() -> spring(dampingRatio = Spring.DampingRatioLowBouncy, stiffness = Spring.StiffnessLow)
                else -> { spring(dampingRatio = 0.5F, stiffness = Spring.StiffnessHigh) }
            }
            ColumColor.Green -> when {
                isAnimated() -> spring(dampingRatio = Spring.DampingRatioLowBouncy, stiffness = Spring.StiffnessVeryLow)
                else -> spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = (Spring.StiffnessLow + Spring.StiffnessMedium) / 2.0F)
            }
        }
    }
    fun getGraphicsLayersByColors(type: ColumColor, enableShadow: Boolean): Dp {
        return when (type) {
            ColumColor.Red -> if (enableShadow) 15.dp else 0.dp
            ColumColor.Blue -> if (enableShadow) 25.dp else 0.dp
            ColumColor.Green -> if (enableShadow) 15.dp else 0.dp
        }
    }
    fun getColors(type: ColumColor): List<Color> {
        return when (type) {
            ColumColor.Red -> listOf(Color(0xAAAF0000), redDark8)
            ColumColor.Blue -> listOf(MyColor.blueDark1, MyColor.blueDark5)
            ColumColor.Green -> listOf(MyColor.greendark4, MyColor.greendark9)
        }
    }

}

data class RankingIconPresentationData(
    val boxHeight: Int = 0,
) {
    val height: Float = boxHeight * 0.85F
    val width: Float = height * (6.0F / 7.0F)
//    val redTargetHeight: Dp = (0.65 * (4.0F/5.0F) * height).dp
    val redTargetHeight: Dp = (0.55 * (4.0F/5.0F) * height).dp
    val redHeight: Dp = ((4.0F/5.0F) * height).dp
//    val blueTargetHeight: Dp = (0.70F  * height).dp
    val blueTargetHeight: Dp = (0.60F  * height).dp
    val blueHeight: Dp = (height).dp
//    val greenTargetHeight: Dp = (0.75 * (3.0F/5.0F) * height).dp
    val greenTargetHeight: Dp = (0.65 * (3.0F/5.0F) * height).dp
    val greenHeight: Dp =  ((3.0F/5.0F) * height).dp
}