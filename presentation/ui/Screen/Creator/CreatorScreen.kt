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
import androidx.compose.foundation.background
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
import com.mobilegame.robozzle.presentation.ui.elements.MapView
import com.mobilegame.robozzle.presentation.ui.elements.RankingIconBouncing
import com.mobilegame.robozzle.presentation.ui.utils.spacer.HorizontalSpace
import com.mobilegame.robozzle.presentation.ui.utils.spacer.VerticalSpace
import kotlinx.coroutines.flow.*


@ExperimentalAnimationApi
@Composable
fun CreatorScreen(navigator: Navigator, testShared: TestShared = viewModel()) {
    infoLog("launch", "CreatorScreen()")

    Row(Modifier.background(gray6)) {
        HorizontalSpace(widthDp = 20)
        Column(modifier = Modifier.weight(1F)) {
            VerticalSpace(height = 50)
//            RankingIconBouncing(sizeAtt = 120)
            VerticalSpace(height = 50)
            MapView(widthInt = 200, map = mapTest)
            VerticalSpace(height = 50)
            MapView(widthInt = 150, map = mapTest)
            VerticalSpace(height = 50)
            MapView(widthInt = 100, map = mapTest)
//            RankingIcon(75)
//            VerticalSpace(height = 50)
//            RankingIcon(sizeAtt = 120)
//            VerticalSpace(height = 50)
//            Neon()
//            VerticalSpace(height = 50)
//            RankingIconBouncing(sizeAtt = 50)

        }
        HorizontalSpace(widthDp = 40)
        Column(modifier = Modifier.weight(1F)) {
            VerticalSpace(height = 50)
//            RankingIconBouncing(sizeAtt = 50)
            VerticalSpace(height = 50)
            MapView(widthInt = 150, map = mapTest2)
            VerticalSpace(height = 50)
            MapView(widthInt = 100, map = mapTest2)
//            VerticalSpace(height = 50)
//            VerticalSpace(height = 50)
        }
    }
}

@Composable
fun mapDraw(map: List<String>) {
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
val mapTest2 = listOf(
    /*                               1 1 1 1 1 1      */
    /*           0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5      */
    /*0*/". . . . . . . . . . . R R R . G".replace(" ", "") , /*0*/
    ". R G G G R . . . . . R R R B G".replace(" ", "") ,
    /*2*/". R G B G R G R G R . R R R B G".replace(" ", "") , /*2*/
    ". R G B R B B B B . . . B B B G".replace(" ", "") ,
    /*4*/". R G B B B B B B . . . B B B G".replace(" ", "") , /*4*/
    ". R G B G B B B B R B B B B B G".replace(" ", "") ,
    /*6*/". R R B R B B B B G B . . . B G".replace(" ", "") , /*6*/
    ". B B B G R G R G R B . . . B G".replace(" ", "") ,
    /*8*/". B B B B B B B B B B B B B B G".replace(" ", "") , /*8*/
    "B B B B B B B B B G B G B G B G".replace(" ", "") ,
    /*10*/"B R R R R R R R R R B R R R B R".replace(" ", "") , /*10*/
    ". . . . . . . . . . . . . . . .".replace(" ", "") ,
)
val mapTest = listOf(
/*                               1 1 1 1 1 1      */
/*            0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5      */
        /*0*/". . . . . . . . . . . . . . . .".replace(" ", "") , /*0*/
             ". . . . . . . . . . . . . . . .".replace(" ", "") ,
        /*2*/". . . . . . . . . . . . . . . .".replace(" ", "") , /*2*/
             ". . . . . B . . B B B . . . . .".replace(" ", "") ,
        /*4*/". . . . B R B . B B B B . . . .".replace(" ", "") , /*4*/
             ". . . . B B R . B B B B . . . .".replace(" ", "") ,
        /*6*/". . . . B B R B B B R B . . . .".replace(" ", "") , /*6*/
             ". . . . B R B . B B B B . . . .".replace(" ", "") ,
        /*8*/". . . . . B R . B B B . . . . .".replace(" ", "") , /*8*/
             ". . . . . . . . . . . . . . . .".replace(" ", "") ,
       /*10*/". . . . . . . . . . . . . . . .".replace(" ", "") , /*10*/
             ". . . . . . . . . . . . . . . .".replace(" ", "") ,
)
