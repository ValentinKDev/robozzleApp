package com.mobilegame.robozzle.data.configuration.inGame

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.presentation.res.*

object InGameColors {
    val darkerBackground = grayDark7

    val popupTextColor = whiteDark6
    val popupMainColor = grayDark2
    val popupElevation = 15.dp

    val actionRowCaseBorder = Color.Black
    val actionRowCaseElevation = 10.dp
    val actionRowCaseBiggerElevation = 15.dp
    val functionText = whiteDark4
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
}