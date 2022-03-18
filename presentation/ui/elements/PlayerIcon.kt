package com.mobilegame.robozzle.presentation.ui.elements

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.data.configuration.inGame.layouts.InGameFirstPart
import com.mobilegame.robozzle.domain.InGame.Direction
import com.mobilegame.robozzle.presentation.res.*
import com.mobilegame.robozzle.presentation.ui.myleveltest
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposable

@Preview
@Composable
fun test() {
    val ctxt = LocalContext.current
    val data = InGameFirstPart
    data.init(ctxt, myleveltest)

    Canvas(Modifier.size(data.size.playerBox.dp)) {
        drawRect(
            brush = Brush.linearGradient(
                listOf(
                    Color(0xff000078),
                    Color(0xff000098),
                    Color(0xff0000ba)
                )
            )
        )
    }
//    data.size.playerBoxDp = s
//    data.size.playerBox = s * 2.75F
//    data.size.playerCanvasDp = (data.size.playerBoxDp * data.ratios.playerCanvas).toInt()
//    data.initPlayerIcon()
//    PlayerIcon(direction = Direction(1,0), data = data)
    PlayerIcon(direction = Direction(1,0), data)
}

@Composable
fun PlayerIcon(direction: Direction, data: InGameFirstPart) {
    Box(modifier = Modifier
//        .sizeBy(Size(data.size.playerIcon, data.size.playerIcon))
//        .size(data.size.playerIconDp.dp)
        .size(data.size.playerBox.dp)
    ){
        CenterComposable {
            Canvas(modifier = Modifier.size(data.size.playerCanvasDp)) {
//            Canvas(modifier = Modifier.size(data.size.playerCanvasDp.dp)) {
//            Canvas(modifier = Modifier.sizeBy(Size(data.size.playerCanvas, data.size.playerCanvas))) {
//            Canvas(modifier = Modifier.size(data.size.playerCanvas.dp)) {
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
                errorLog("info", ".")
                errorLog("data.size.playerIconDp", "${data.size.playerBoxDp}")
                errorLog("data.size.playerCanvasDp", "${data.size.playerCanvasDp}")
                errorLog("size Canvas", "${size.width}")
                errorLog("size Canvas", "${size.height}")


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
                        brush = Brush.radialGradient(listOf(
                            Color(0xFFAF7000),
                            Color(0xFFFFB020),
                            Color(0xBBFFB020),
                            Color(0x99FFB020),
                            Color(0x22FFB020),
                        )),
                        useCenter = true,
                        topLeft = Offset.Zero,
                        startAngle = 148F,
                        sweepAngle = 64F,
                    )

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
   }
}