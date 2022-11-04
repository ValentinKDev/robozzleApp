package com.mobilegame.robozzle.data.layout.inGame

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.presentation.res.*
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.grayDark
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.grayDark2
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.grayDark3
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.grayDark7
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.whiteDark3
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.whiteDark6
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.whiteDark6Plus
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.whiteDark8
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.yellow1
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.yellow2
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.yellow3
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.yellow4
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.yellow5
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.yellow6
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.yellow7
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.yellowDark2

object InGameColors {
    val darkerBackground = grayDark7

    val popupTextColor = whiteDark6
    val popupMainColor = grayDark2
    val popupElevation = 15.dp

    val actionRowCaseBorder = Color.Black
    val actionRowCaseElevation = 5.dp
    val actionRowCaseBiggerElevation = 5.dp
    val functionText = whiteDark6Plus
    val functionTextDark = whiteDark8
    val functionBorder = Color.Black
    val functionCaseSelection = whiteDark3
    val coloringCaseIconBorder = Color.Black

    val buttonGameBackground = grayDark3
    val buttonElevation = 15.dp

    val menuCaseBorder = Color.Black

    private val darkgray1 = grayDark(4, 1)
    private val darkgray2 = grayDark(6, 1)
    private val darkgray3 = grayDark(10, 3)

    val gradientOfRed = listOf(Color(0xff750000), Color(0xff920000), Color(0xffad0000))
    val gradientOfRedDark = listOf(Color(0xff110000), Color(0xff650000), Color(0xff990000))

    val gradientOfBlue = listOf(Color(0xff000078), Color(0xff000098), Color(0xff0000ba))
    val gradientOfBlueDark =  listOf(Color(0xff000011), Color(0xff000065), Color(0xff000099))

    val gradientOfGreen = listOf(Color(0xff005500), Color(0xff008000), Color(0xff00a900))
    val gradientOfGreenDark = listOf(Color(0xff001100), Color(0xff006500), Color(0xff008800))

    val gradientOfGray = listOf(grayDark(1,2), grayDark(2,3), grayDark(3,3))
    val gradientOfGrayDark = listOf(darkgray3, darkgray2, darkgray1)

    val star = Star
    object Star {
        val backGlowingEffect = listOf( yellow1, yellow2, yellow3, yellow4, yellow5, yellow6, yellow7, Color.Transparent )
        val inColor = yellowDark2
        val neongradient = listOf( Color(0xFFFFFFFF), Color(0x9FFFFFFF), Color(0x5FFFFFFF), Color(0x0FFFFFFF), Color.Transparent, Color.Transparent, )
    }
    val player = Player
    object Player {
        val neonGradient = listOf( Color(0x9FFFFFFF), Color(0x5FFFFFFF), Color(0x0FFFFFFF), Color.Transparent, Color.Transparent, Color.Transparent, )
        val bottomPart = listOf( Color(0x88FFB020), Color(0xDDFFB020), Color(0xFFFFB020), )
        val rightPart = listOf( Color(0xFFEE6615), Color(0xFFDD5412), Color(0xFFBD4409), Color(0xFFA23807), )
        val leftPart = listOf( Color(0xFFA23807), Color(0xFFDD5412), Color(0xFFEE6615), )
        val lineCenter = Color(0xFFA23807)
        val glowGradient = listOf( Color(0xFF9F7000), Color(0xFFFFB020), Color(0xBBFFB020), Color(0x99FFB020), Color.Transparent )
    }
    val emptySquare = EmptySquare
    object EmptySquare {
        val shimmerColorShades = listOf(
            Color.LightGray.copy(0.9f),
            Color.LightGray.copy(0.2f),
            Color.LightGray.copy(0.9f)
        )
    }
}