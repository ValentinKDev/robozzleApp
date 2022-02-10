package com.mobilegame.robozzle.presentation.ui.Screen.Creator

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material.Button
import androidx.compose.material.Shapes
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.domain.model.Screen.NavViewModel
import com.mobilegame.robozzle.presentation.ui.Navigator
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun TestScreen(navigator: Navigator) {
    Column( modifier = Modifier
        .fillMaxSize()
    ) {
        Column( modifier = Modifier
            .align(CenterHorizontally)
        ) {
            Button(
                modifier = Modifier
                    .width(450.dp)
                    .height(50.dp)
                ,
                onClick = {
                    NavViewModel(navigator).navigateTo(Screens.Creator)
                }
            ) {
                Text("button 1")
            }
            Spacer(modifier = Modifier.height(100.dp))
            val myShape = GenericShape { size, layoutDirection ->
                moveTo(size.width / 2f, 0f)
                lineTo(size.width, size.height)
                lineTo(0F, size.height)
            }

        }
    }
}

class CustomShape: Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            moveTo(size.width / 2f, 0f)
            lineTo(size.width, size.height)
            lineTo(0f, size.height)
            close()
        }
        return Outline.Generic(path)
    }
}
class PolyShape(private val sides: Int, private val radius: Float) : Shape {
    override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline {
        return Outline.Generic(path = Path().apply { polygon(sides, radius, size.center) })
    }
}

fun Path.polygon(sides: Int, radius: Float, center: Offset) {
    val angle = 2.0 * Math.PI / sides
    moveTo(
        x = center.x + (radius * cos(0.0)).toFloat(),
        y = center.y + (radius * sin(0.0)).toFloat()
    )
    for (i in 1 until sides) {
        lineTo(
            x = center.x + (radius * cos(angle * i)).toFloat(),
            y = center.y + (radius * sin(angle * i)).toFloat()
        )
    }
    close()
}