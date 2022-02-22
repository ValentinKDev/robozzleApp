package com.mobilegame.robozzle.presentation.ui.Screen.Creator

import android.content.Context
import android.graphics.Canvas
import android.os.NetworkOnMainThreadException
import android.util.AttributeSet
import android.view.View
import androidx.compose.animation.*
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobilegame.robozzle.analyse.infoLog
import androidx.compose.foundation.Canvas
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.drawscope.Stroke
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.domain.model.Screen.NavViewModel
import com.mobilegame.robozzle.presentation.res.*
import com.mobilegame.robozzle.presentation.ui.Navigator
import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.ButtonId
import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.goingTopSizeButton
import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.goingTopTiming
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import com.mobilegame.robozzle.presentation.ui.elements.RankingIcon
import kotlinx.coroutines.flow.*


@ExperimentalAnimationApi
@Composable
fun CreatorScreen(navigator: Navigator, testShared: TestShared = viewModel()) {
    infoLog("launch", "CreatorScreen()")

    Column {
        Neon()
        RankingIcon(100)
        RankingIcon(50)
        RankingIcon(25)
    }
}


@Composable
fun Neon() {
    Canvas(
        modifier = Modifier.size(100.dp),
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(green0, green9)
            ),
            radius = (canvasWidth / 2) - 5,
            center = center,
            style = Stroke(width = canvasWidth * 0.075f)
        )
        drawCircle(
            brush = Brush.radialGradient(
//                colors = listOf(gray0, gray9)
                colors = listOf(green0, green9)
            ),
            radius = canvasWidth / 2,
            center = center,
            style = Stroke(width = canvasWidth * 0.075f)
        )
    }
}

class MyDrawView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
): View(context, attrs, defStyle) {
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //Draw here
//        drawCircle()
    }
}

class TestShared(): ViewModel() {

}