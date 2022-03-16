package com.mobilegame.robozzle.presentation.ui.elements

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.presentation.res.*
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposable

@Preview
@Composable
fun TestIt() {
    StarIcon(widhtDp = 100)
}

@Composable
//fun StarIcon(widhtDp: Int, vm: GameDataViewModel) {
fun StarIcon(widhtDp: Int) {
    DrawStarPart(
        widhtDp = widhtDp,
        rotation = 0F,
        colorLeftSide = listOf(yellowDark2, yellowDark2),
        colorRightSide = listOf(yellowDark2, yellowDark3, yellowDark5),
        colorBack = yellowDark2
    )
    DrawStarPart(
        widhtDp = widhtDp,
        rotation = 72F,
        colorLeftSide = listOf(yellowDark2, yellowDark2),
        colorRightSide = listOf(yellowDark2, yellowDark3, yellowDark5),
        colorBack = yellowDark2
    )
    DrawStarPart(
        widhtDp = widhtDp,
        rotation = 144F,
        colorLeftSide = listOf(yellowDark2, yellowDark2),
        colorRightSide = listOf(yellowDark2, yellowDark3, yellowDark5),
        colorBack = yellowDark2
    )
    DrawStarPart(
        widhtDp = widhtDp,
        rotation = 216F,
        colorLeftSide = listOf(yellowDark2, yellowDark2),
        colorRightSide = listOf(yellowDark2, yellowDark3, yellowDark5),
        colorBack = yellowDark2
    )
    DrawStarPart(
        widhtDp = widhtDp,
        rotation = 288F,
        colorLeftSide = listOf(yellowDark2, yellowDark2),
        colorRightSide = listOf(yellowDark2, yellowDark3, yellowDark5),
        colorBack = yellowDark2
    )
}
@Composable
fun DrawStarPart(
    widhtDp: Int,
    rotation: Float,
    colorLeftSide: List<Color>,
    colorRightSide: List<Color>,
    colorBack: Color,
//    colorLines: List<Color>
) {
    Box(modifier = Modifier
        .size(widhtDp.dp)
    ){
        CenterComposable {
            Box(modifier = Modifier
                .size((0.9F * widhtDp).dp)
                .drawBehind {
                    val width = size.width
                    val xCenter = size.width / 2F
                    val yCenter = size.width / 2F

                    val horizontal = 0.343F * size.height
                    val vertical3 = xCenter + (0.118F * size.width)
                    val vertical2 = xCenter - (0.118F * size.width)
                    val vertical1 = (0.022F * size.width)

                    val pTopx = xCenter
                    val pTopy = vertical1

                    val pLeftx = vertical2
                    val pLefty = horizontal

                    val pRightx = vertical3
                    val pRighty = horizontal

                    val decenterRightx = center.x + 0.01F * width
                    val decenterRighty = center.y + 0.01F * width

                    val partRight = Path().apply {
                        moveTo(pTopx, pTopy)
                        lineTo(pRightx, pRighty)
                        lineTo(xCenter, yCenter)
                    }
                    val partRight2 = Path().apply {
                        moveTo(pTopx, pTopy)
                        lineTo(pRightx, pRighty)
                        lineTo(decenterRightx, decenterRighty)
//                        lineTo(decenterLeftx, decenterLefty)
                    }
                    val partLeft2 = Path().apply {
                        moveTo(pTopx, pTopy)
                        lineTo(pLeftx,pLefty)
                        lineTo(decenterRightx, decenterRighty)
                    }
                    val partLeft = Path().apply {
                        moveTo(pTopx, pTopy)
                        lineTo(pLeftx,pLefty)
                        lineTo(xCenter, yCenter)
                    }
                    rotate(degrees = rotation) {
                        drawPath(partRight2, colorBack)
                        drawPath(partLeft2, colorBack)
                        drawPath(partRight, brush = Brush.linearGradient(colorRightSide))
                        drawPath(partLeft, brush = Brush.linearGradient(colorLeftSide))
                    }
                }
            )
        }
    }
}