package com.mobilegame.robozzle.Extensions

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.presentation.ui.utils.Dimensions
import java.lang.Math.*
import kotlin.math.pow
import kotlin.math.sqrt

fun Modifier.gradientBackground(colors: List<Color>, angle: Float) = this.then(
    Modifier.drawBehind {
        val angleRad = angle / 180f * PI
        val x = cos(angleRad).toFloat() //Fractional x
        val y = sin(angleRad).toFloat() //Fractional y

        val radius = sqrt(size.width.pow(2) + size.height.pow(2)) / 2f
        val offset = center + Offset(x * radius, y * radius)

        val exactOffset = Offset(
            x = min(offset.x.coerceAtLeast(0f), size.width),
            y = size.height - min(offset.y.coerceAtLeast(0f), size.height)
        )

        drawRect(
            brush = Brush.linearGradient(
                colors = colors,
                start = Offset(size.width, size.height) - exactOffset,
                end = exactOffset
            ),
            size = size
        )
    }
)

fun Modifier.backColor(color: Color) = this.then(
    Modifier.drawBehind {
        drawRect(
            color = color,
            size = size
        )
    }
)

@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.mediaQuery(
    comparator: Dimensions.DimensionComparator,
    modifier: Modifier
): Modifier = composed {

    val screenWidth = LocalContext.current.resources.displayMetrics.widthPixels.dp / LocalDensity.current.density
    val screenHeight = LocalContext.current.resources.displayMetrics.heightPixels.dp / LocalDensity.current.density

    if (comparator.compare(screenWidth, screenHeight)) {
        this.then(modifier)
    } else this
}

fun Modifier.widthRatioTotalWidth(ratio: Float): Modifier = composed {
    val screenWidthPixel = LocalContext.current.resources.displayMetrics.widthPixels
    this.then(Modifier.width(((ratio * screenWidthPixel).dp / LocalDensity.current.density)))
}

fun Modifier.heightRatio(ratio: Float): Modifier = composed {
    val screenHeightPixel = LocalContext.current.resources.displayMetrics.heightPixels
    this.then(Modifier.height(((ratio * screenHeightPixel).dp / LocalDensity.current.density)))
}

fun Modifier.sizeBy(size: Size): Modifier = composed {
    val modifier = Modifier.size(width = size.width.dp, height = size.width.dp)
    this.then(Modifier.size(width = size.width.dp, height = size.height.dp))
}