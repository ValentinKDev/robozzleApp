package com.mobilegame.robozzle.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color

val normalInGameBackGround = Color(0xdd080808)

val darkerGray =        Color(0xdf686868)
val darkerdarkerGray =  Color(0xbf353535)
val deepdarkGray =  Color(0xff050505)
val inGameBackground = Color(0xfe020202)
//val superDark =         Color(0xaf252525)

val niceGreen =         Color(0xdf00df00)
val darkerGreen =       Color(0xaf00af00)

val niceRed =           Color(0xdfdf0000)
val darkerRed =         Color(0xafaf0000)

val niceBlue =          Color(0xdf0000df)
val darkerBlue =        Color(0xaf0000af)

fun ColorsList(toRecognize: String, darkerFilter: Boolean): List<Color> {

    val red: List<Color> = if (darkerFilter) { listOf(Color(0xff110000), Color(0xff650000), Color(0xff990000))}
    else {listOf(Color(0xff750000), Color(0xff920000), Color(0xffad0000))}

    val blue: List<Color> = if (darkerFilter) { listOf(Color(0xff000011), Color(0xff000065), Color(0xff000099)) }
    else { listOf(Color(0xff000078), Color(0xff000098), Color(0xff0000ba)) }

    val green: List<Color> = if (darkerFilter) { listOf(Color(0xff001100), Color(0xff006500), Color(0xff009900)) }
    else { listOf(Color(0xff005500), Color(0xff008000), Color(0xff00a900)) }

    val transparent : List<Color> =  listOf(Color.Transparent, Color.Transparent)
    val errorColor: List<Color> = listOf(Color.Yellow, Color.Green, Color.Blue)
    val gray: List<Color> = listOf(Color.Gray, Color.Gray)

    return when (toRecognize) {
        "g" -> gray
        "R" -> red
        "B" -> blue
        "G" -> green
        "." -> transparent
        else -> errorColor
    }
}

fun RecognizeColor(toRecognize: String, darkerFilter: Boolean): Color {
    var color: Color

    val red = if (darkerFilter) { darkerRed } else { niceRed}
    val blue = if (darkerFilter) { darkerBlue } else { niceBlue}
    val green = if (darkerFilter) { darkerGreen } else { niceGreen}
    val gray = if (darkerFilter) { darkerGray } else { Color.Gray}

    when (toRecognize) {
        "G" -> color = gray
        "r" -> color = red
        "b" -> color = blue
        "g" -> color = green
        "." -> color = Color.Transparent
        else -> color = Color.Yellow
    }
    return color
}
