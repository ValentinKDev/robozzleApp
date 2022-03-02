package com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen

import android.content.Context
import com.mobilegame.robozzle.domain.RobuzzleLevel.RobuzzleLevel


class ScreenConfig(localContext: Context, level: RobuzzleLevel) {
//class ScreenConfig(level: RobuzzleLevel) {

//    get
    val density = localContext.resources.displayMetrics.density
    val dpi_valor = localContext.resources.displayMetrics.densityDpi

    val iconBarPx : Double = dpi_valor / (20.0 / 3.0)
    val iconBarDp : Double = iconBarPx / density

    val heightPx : Int = localContext.resources.displayMetrics.heightPixels
    val widhtPx : Int = localContext.resources.displayMetrics.widthPixels
    val heightDp : Double = (heightPx / density) - iconBarDp
    val widhtDp : Float = widhtPx / density

    val notif_icon_bar_size = 24

    val weightMap: Float =  (widhtPx.toFloat()/ heightPx) * 2
    val weight_second_part: Float = (2.0 - weightMap).toFloat()

    val numberColumns = level.map[0].length
    val numberRows = level.map.size
    val mapWidthPx: Int = widhtPx
    val mapDp: Float = widhtPx / density
    var mapHeightPx: Float = widhtPx * (numberRows.toFloat()/numberColumns.toFloat())
    var mapHeightDp: Float = mapHeightPx / density

//    val mapBoxSizePx: Float = mapPx.toFloat() / (numberCaseRow)
    val mapBoxSizePx: Float = widhtPx.toFloat() / (numberColumns)
    val mapBoxSize: Float = mapBoxSizePx / density
    val mapBoxPaddingPx: Float = mapBoxSizePx / (25)
//    val mapBoxPaddingPx: Float = mapBoxSizePx.toFloat() / (5)
    val mapBoxPadding: Float = mapBoxPaddingPx / density

    val functionBoxSize = 40
    val functionBoxPadd = 4
    val instructionFunctionCase = 40
    val instructionFunctionIcon = 40
    val directionIconSize = 30
    val playButtonsWidth = 40
    val playButtonsHeight = 30
    val maxActionDisplayedActionRow = 7
    val instructionActionRowCase = 45
    val instructionActionRowIcon = 30
    val instructionMenuCase = 35
    val instructionMenuCaseWidth = widhtDp/(level.instructionsMenu[0].instructions.length)
    val instructionMenuCaseBorder = instructionMenuCaseWidth/15
    val instructionMenuIcon = 40

    init {
        mapHeightPx += mapBoxPaddingPx
        mapHeightDp = mapHeightPx.toDp()
//        Log.v("", "widhtPx $widhtPx, heightPx $heightPx")
//        Log.v("", "numberRows $numberRows nubmerCols $numberColumns")
    }

    private fun Float.toDp(): Float {return (this / density)}
}