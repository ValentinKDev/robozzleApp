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
import com.mobilegame.robozzle.data.layout.inGame.InGameColors
import com.mobilegame.robozzle.data.layout.inGame.layouts.InGameFirstPart
import com.mobilegame.robozzle.domain.InGame.Direction
import com.mobilegame.robozzle.presentation.res.*
import com.mobilegame.robozzle.presentation.ui.Navigation.myleveltest
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
    PlayerIcon(direction = Direction(1,0), data, InGameColors, color.toCaseColor())
}

@Composable
fun PlayerIcon(direction: Direction, data: InGameFirstPart, colors: InGameColors, caseColor: CaseColor) {
    Box(modifier = Modifier
        .size(data.size.playerBoxDp)
    ) {
        PlayerGlowingEffect(direction = direction, data, colors, caseColor)
        Player(direction = direction, data, colors)
    }
}

@Composable
fun PlayerGlowingEffect(direction: Direction, data: InGameFirstPart, colors: InGameColors, caseColor: CaseColor, isMoving: Boolean = false) {
    CenterComposable {
        val transition = rememberInfiniteTransition()
        val ratioAnim by transition.animateFloat(
            initialValue = 0.85F,
            targetValue = 0.92F,
            animationSpec = infiniteRepeatable(
                tween(
                    durationMillis = 1200,
                    easing = CubicBezierEasing(0.0f, 0.4f, 0.9f, 0.3f),
                ),
                repeatMode = RepeatMode.Reverse
            )
        )

        Box(Modifier.size( if (isMoving) data.size.playerBoxDp else data.size.playerBoxDp * ratioAnim ))
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
                                    colors.player.glowGradient
                                CaseColor.Green ->
                                    colors.player.glowGradient
                                else ->
                                    colors.player.glowGradient
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
fun Player(direction: Direction, data: InGameFirstPart, colors: InGameColors) {
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

                drawLine(
                    start = pBottomCenter,
                    end = pTop,
                    brush = Brush.horizontalGradient(
                        listOf(
                            Color.Transparent,
                            Color.Transparent,
                            armorColor,
                            colors.player.lineCenter
                        )
                    ),
                    cap = cap,
                    strokeWidth = data.player.strokeWidthSmall
                )
                //ext right
                drawLine(
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
                    brush = Brush.verticalGradient( colors.player.leftPart )
                )
                drawPath(
                    trianglePathRightHalf,
                    brush = Brush.verticalGradient( colors.player.rightPart )
                )

                val bottomBase = Path().apply {
                    moveTo(data.player.pCenter.x, data.player.pCenter.y)
                    lineTo(pNeonLeftEnd.x, pNeonLeftEnd.y)
                    lineTo(pBottomCenter.x, pBottomCenter.y)
                    lineTo(pNeonRightEnd.x, pNeonRightEnd.y)
                    lineTo(data.player.pCenter.x, data.player.pCenter.y)
                }
                drawPath(
                    brush = Brush.radialGradient( colors.player.bottomPart ),
                    path = bottomBase
                )
                drawPath(
                    brush = Brush.horizontalGradient( colors.player.neonGradient ),
                    path = bottomBase
                )
            }
        }
    }
}