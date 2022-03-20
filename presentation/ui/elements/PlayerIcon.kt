package com.mobilegame.robozzle.presentation.ui.elements

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.data.configuration.inGame.layouts.InGameFirstPart
import com.mobilegame.robozzle.domain.InGame.Direction
import com.mobilegame.robozzle.presentation.res.*
import com.mobilegame.robozzle.presentation.ui.myleveltest
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposable
import com.mobilegame.robozzle.utils.Extensions.toCaseColor

@Preview
@Composable
fun test() {
    val ctxt = LocalContext.current
    val data = InGameFirstPart
    data.init(ctxt, myleveltest)

//    val color = 'B'
    val color = 'G'
    Canvas(Modifier.size(data.size.playerBoxDp)) {
        drawRect(
            brush = Brush.linearGradient(
                mapCaseColorList(color)
            )
        )
    }
    PlayerIcon(direction = Direction(1,0), data, color.toCaseColor())
}

@Composable
fun PlayerIcon(direction: Direction, data: InGameFirstPart, caseColor: CaseColor) {
    Box(modifier = Modifier
        .size(data.size.playerBoxDp)
    ) {
        PlayerGlowingEffect(direction = direction, data, caseColor)
        Player(direction = direction, data)
    }
}

@Composable
fun PlayerGlowingEffect(direction: Direction, data: InGameFirstPart, caseColor: CaseColor) {
    CenterComposable {
        val transition = rememberInfiniteTransition()
        val ratioAnim by transition.animateFloat(
            initialValue = 0.85F,
//            initialValue = 0.88F,
            targetValue = 0.92F,
            animationSpec = infiniteRepeatable(
                tween(
                    durationMillis = 1200,
                    easing = CubicBezierEasing(0.0f, 0.4f, 0.9f, 0.3f),
//                    easing = FastOutSlowInEasing
//                easing = FastOutLinearInEasing
//                        easing = LinearEasing
                ),
                repeatMode = RepeatMode.Reverse
            )
        )

//        Box(Modifier.size(data.size.playerBoxDp * (5F/6F)) )
        Box(Modifier.size(data.size.playerBoxDp * ratioAnim))
        {
            Canvas(modifier = Modifier.fillMaxSize()) {
                Color
                rotate(
                    pivot = center,
                    degrees = when (direction.ToChar()) {
                        'r' -> 0F
                        'd' -> 90F
                        'l' -> 180F
                        'u' -> 270F
                        else -> 45F
                    }
                ) {
                    drawArc(
                        brush = Brush.radialGradient(
                            when (caseColor) {
                                CaseColor.Blue ->
                                    listOf(
                                        Color(0xFF9F7000),
                                        Color(0xFFFFB020),
                                        Color(0xBBFFB020),
                                        Color(0x99FFB020),
                                        Color.Transparent
                                    )
                                CaseColor.Green ->
                                    listOf(
                                        Color(0xFF9F7000),
                                        Color(0xFFFFB020),
                                        Color(0xBBFFB020),
                                        Color(0x99FFB020),
                                        Color.Transparent
                                    )
                                else ->
                                    listOf(
                                        Color(0xFF9F7000),
                                        Color(0xFFFFB020),
                                        Color(0xBBFFB020),
                                        Color(0x99FFB020),
                                        Color.Transparent
                                    )
                            }
                        ),
                        size = Size(size.maxDimension, size.maxDimension),
                        useCenter = true,
                        topLeft = Offset.Zero,
                        startAngle = 142F,
                        sweepAngle = 76F,
                    )
                }
            }
        }
    }
}

@Composable
fun Player(direction: Direction, data: InGameFirstPart) {
    CenterComposable {
        Canvas(modifier = Modifier.size(data.size.playerCanvasDp)) {
            val armorColor = Color.Black
            val cap = StrokeCap.Round

            val strokeWidthSmall = data.player.strokeWidth

            val pBottomCenter = data.player.pBottomCenter
            val pBottomLeft = data.player.pBottomLeft
                val pBottomRight = data.player.pBottomRight
                val pTop = data.player.pTop

                val trianglePathLeftHalf = Path().apply {
                    moveTo(pTop.x, pTop.y)
                    lineTo(pBottomLeft.x, pBottomLeft.y)
                    lineTo(pBottomCenter.x, pBottomCenter.y)
                }
                val trianglePathRightHalf = Path().apply {
                    moveTo(pTop.x, pTop.y)
                    lineTo(pBottomRight.x, pBottomRight.y)
                    lineTo(pBottomCenter.x, pBottomCenter.y)
                }

                val pNeonLeftEnd = data.player.pNeonLeftEnd
                val pNeonRightEnd = data.player.pNeonRightEnd
//                errorLog("info", ".")
//                errorLog("data.size.playerIconDp", "${data.size.playerBoxDp}")
//                errorLog("data.size.playerCanvasDp", "${data.size.playerCanvasDp}")
//                errorLog("size Canvas", "${size.width}")
//                errorLog("size Canvas", "${size.height}")


                rotate(
                    pivot = center,
                    degrees = when (direction.ToChar()) {
                        'r' -> 0F
                        'd' -> 90F
                        'l' -> 180F
                        'u' -> 270F
                        else -> 45F
                    }
                ) {
//                    drawArc(
////                        brush = Brush.radialGradient(listOf(
////                            Color(0xFFAF7000),
////                            Color(0xFFFFB020),
////                            Color(0xBBFFB020),
////                            Color(0x99FFB020),
////                            Color(0x22FFB020),
////                        )),
//                        color = green0,
//                        size = Size(size.width, size.height),
//                        useCenter = true,
//                        topLeft = Offset.Zero,
//                        startAngle = 148F,
//                        sweepAngle = 64F,
//                    )

                    //ext right
                    drawLine(
//                        start = Offset(width, yCenter),
                        start = data.player.pCenter,
                        end = pBottomRight,
                        color = armorColor,
                        cap = cap,
                        strokeWidth = strokeWidthSmall
                    )

                    //ext left
                    drawLine(
                        start = data.player.pCenter,
                        end = pBottomLeft,
//                        end = Offset(width, yCenter),
                        color = armorColor,
                        cap = cap,
                        strokeWidth = strokeWidthSmall
                    )

                    //bottom right
                    drawLine(
                        start = pNeonRightEnd,
                        end = pBottomRight,
                        color = armorColor,
                        cap = cap,
                        strokeWidth = strokeWidthSmall
                    )

                    //bottom left
                    drawLine(
                        start = pNeonLeftEnd,
                        end = pBottomLeft,
                        color = armorColor,
                        cap = cap,
                        strokeWidth = strokeWidthSmall
                    )

                    drawPath(
                        trianglePathLeftHalf,
                        brush = Brush.verticalGradient(
                            listOf(
                                Color(0xFFA23807),
                                Color(0xFFDD5412),
                                Color(0xFFEE6615),
                            )
                        )
                    )
                    drawPath(
                        trianglePathRightHalf,
                        brush = Brush.verticalGradient(
                            listOf(
                                Color(0xFFEE6615),
                                Color(0xFFDD5412),
                                Color(0xFFBD4409),
                                Color(0xFFA23807),
                            )
                        )
                    )

                    val bottomBase = Path().apply {
                        moveTo(data.player.pCenter.x, data.player.pCenter.y)
                        lineTo(pNeonLeftEnd.x, pNeonLeftEnd.y)
                        lineTo(pBottomCenter.x, pBottomCenter.y)
                        lineTo(pNeonRightEnd.x, pNeonRightEnd.y)
                        lineTo(data.player.pCenter.x, data.player.pCenter.y)
                    }
                    drawPath(
                        brush = Brush.radialGradient(
                            listOf(
                                Color(0x88FFB020),
                                Color(0xDDFFB020),
                                Color(0xFFFFB020),
                            )
                        ),
                        path = bottomBase
                    )
                }
            }
       }
//   }
}