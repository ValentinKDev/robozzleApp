package com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.ScreenParts.secondPart

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.data.configuration.inGame.InGameColors
import com.mobilegame.robozzle.data.configuration.inGame.elements.CaseColoringIcon
import com.mobilegame.robozzle.presentation.res.blue9
import com.mobilegame.robozzle.presentation.res.gray9
import com.mobilegame.robozzle.presentation.res.green9
import com.mobilegame.robozzle.presentation.res.red9
import com.mobilegame.robozzle.presentation.ui.elements.CaseColor
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposable
import gradientBackground

@Preview
@Composable
fun test() {
    val ctxt = LocalContext.current
    val density = LocalDensity.current.density

    val dataColors = InGameColors
//    val data = CaseColoringIcon(40F * 2.75F, density)
//    val data = CaseColoringIcon().init(40F * 2.75F, density)

//    Box(Modifier.size(40.dp).gradientBackground(dataColors.gradientOfGray, 175F)){
//        DrawCaseColoringIcon(CaseColor.Green, data, dataColors, false)
//    }
}

@Composable
fun DrawCaseColoringIcon(colorInto: CaseColor, data: CaseColoringIcon, dataColors: InGameColors, darkFilter: Boolean) {
    CenterComposable {
        Canvas(modifier = Modifier.size(data.canvasSizeDp).shadow(elevation = 8.dp)) {
            drawCircle(
                brush = Brush.verticalGradient(
                    when (colorInto) {
                        CaseColor.Red -> if (darkFilter) dataColors.gradientOfRedDark else dataColors.gradientOfRed
                        CaseColor.Green -> if (darkFilter) dataColors.gradientOfGreenDark else dataColors.gradientOfGreen
                        CaseColor.Blue -> if (darkFilter) dataColors.gradientOfBlueDark else dataColors.gradientOfBlue
                        else -> if (darkFilter) dataColors.gradientOfGrayDark else dataColors.gradientOfGray
                    }
                ),
                radius = data.radiusIn,
            )
                drawCircle(
                    brush = Brush.radialGradient(
                        when (colorInto) {
                            CaseColor.Red -> listOf(
                                red9,
                                red9,
                                red9,
                                red9,
                                red9,
                                red9,
                                Color.Black,
                            )
                            CaseColor.Green -> listOf(
                                green9,
                                green9,
                                green9,
                                green9,
                                green9,
                                green9,
                                Color.Black,
                            )
                            CaseColor.Blue -> listOf(
                                blue9,
                                blue9,
                                blue9,
                                blue9,
                                blue9,
                                blue9,
                                Color.Black,
                            )
                            CaseColor.Gray -> listOf(
                                gray9,
                                gray9,
                                gray9,
                                gray9,
                                gray9,
                                gray9,
                                Color.Black,
                            )
                            else -> listOf(Color.Transparent, Color.Transparent)
                        }
                    ),
                    radius = data.radiusIn,
                )
            drawCircle(
                brush = Brush.radialGradient(
                    listOf(
                        dataColors.coloringCaseIconBorder,
                        dataColors.coloringCaseIconBorder,
                        dataColors.coloringCaseIconBorder,
                        dataColors.coloringCaseIconBorder,
                        dataColors.coloringCaseIconBorder,
                        dataColors.coloringCaseIconBorder,
                        dataColors.coloringCaseIconBorder,
                        Color.Transparent,
                    )
                ),
                radius = data.radiusForBorder,
                style = Stroke(data.borderStroke),
                center = center
            )
        }
    }
}