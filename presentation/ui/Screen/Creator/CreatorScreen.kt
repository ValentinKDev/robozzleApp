package com.mobilegame.robozzle.presentation.ui.Screen.Creator

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.material.Text
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.mobilegame.robozzle.R
import com.mobilegame.robozzle.domain.model.Screen.Navigation.NavViewModel
import com.mobilegame.robozzle.domain.model.gesture.dragAndDropCase.DragAndDropCaseState
import com.mobilegame.robozzle.presentation.res.*
import com.mobilegame.robozzle.presentation.ui.Navigation.Navigator
import com.mobilegame.robozzle.presentation.ui.Screen.Screens


@Composable
fun CreatorScreen(navigator: Navigator, dragAndDropVM: DragAndDropCaseState = DragAndDropCaseState(), itemList: List<String> = itemListval) {
    BackHandler {
        NavViewModel(navigator).navigateToMainMenu(fromScreen = Screens.Donation.route)
    }

    Box(Modifier.fillMaxSize()) {
        val compo by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.toaster))
        LottieAnimation(
            compo,
            modifier = Modifier.size(100.dp),
            iterations = LottieConstants.IterateForever,
        )
    }
}


@Composable
fun Handle(rowIndex: Int, columnIndex: Int, s: String, dragAndDropVM: DragAndDropCaseState) {
    var selectColor: Color = remember { gray7 }

    Box(
        Modifier
            .wrapContentSize()
            .background(selectColor)
    ) {
        Text(text = s, color = whiteDark4)
    }
}



val itemListval = listOf(
    "Item 1",
    "Item 2",
    "Item 3",
    "Item 4",
    "Item 5",
    "Item 6",
    "Item 7",
    "Item 8",
    "Item 9",
    "Item 10",
    "Item 11",
    "Item 12",
    "Item 13",
    "Item 14",
    "Item 15",
    "Item 16",
    "Item 17",
    "Item 18",
    "Item 19",
    "Item 20"
).toMutableStateList()

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

val mapTest4 = listOf(
/*                               1 1 1 1 1 1      */
/*           0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5      */
/*0*/". . . . . . . . . . . . . . . .".replace(" ", "") , /*0*/
/*1*/". . . . . . . . . . . . . . . .".replace(" ", "") , /*0*/
     ". . . . . . . . . . . . . . . .".replace(" ", "") ,
/*3*/". . . R G G G R . B B B . . . .".replace(" ", "") , /*2*/
     ". . R G G R . G . B B B B . . .".replace(" ", "") ,
/*5*/". . G G . G . G . B . B B . . .".replace(" ", "") , /*4*/
     ". . G R G R . G . B B B B . . .".replace(" ", "") ,
/*7*/". . G . . . . G . . . . B . . .".replace(" ", "") , /*6*/
     ". . R . . . . B B B B B B . . .".replace(" ", "") ,
/*9*/". . . . . . . . . . . . . . . .".replace(" ", "") , /*8*/
     ". . . . . . . . . . . . . . . .".replace(" ", "") ,
/*11*/". . . . . . . . . . . . . . . .".replace(" ", "") , /*10*/
)


val mapTest3 = listOf(
/*                               1 1 1 1 1 1      */
/*           0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5      */
/*0*/". R . . . . . . . . . . . . . .".replace(" ", "") , /*0*/
    ". G B B B G . . . . . . . . . .".replace(" ", "") ,
/*2*/". G B B B B B G . . . . . . . .".replace(" ", "") , /*2*/
     ". G . . . . . . . . . . . . . .".replace(" ", "") ,
/*4*/". G B G . . . . . . . . . . . .".replace(" ", "") , /*4*/
     ". G . . . . . . . . . . . . . .".replace(" ", "") ,
/*6*/". G B B B B B B B B G . . . . .".replace(" ", "") , /*6*/
     ". G . . . . . . . . . . . . . .".replace(" ", "") ,
/*8*/". G B B B B B B B B B B B B B .".replace(" ", "") , /*8*/
     ". G B B B B B B B B B B G . . .".replace(" ", "") ,
/*10*/". G B B B B B B B G . . . . . .".replace(" ", "") , /*10*/
     ". G B B B B B B B B B G . . . .".replace(" ", "") ,
)
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
val mapTest1 = listOf(
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
val mapTest5 = listOf(
/*               0123456789012345     */
"................", /*0*/
".......RR.......",
".......BB.......", /*2*/
".......GG.......",
".......RR.......", /*4*/
".......BR.......",
".......RR.......", /*6*/
".......GG.......",
".......BB.......", /*8*/
".......RR.......",
"................", /*10*/
)