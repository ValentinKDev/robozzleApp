package com.mobilegame.robozzle.data.layout.userStats

import android.content.Context
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.analyse.verbalLog
import com.mobilegame.robozzle.presentation.ui.Navigation.displayUIData
import com.mobilegame.robozzle.utils.Extensions.toDp

object userStatsThirdPart {
    val dimensions = Dimensions
    val grid = Grid
    val winOverView = WinOverView

    object Dimensions {
        const val heightWeight = 0.80f
        var height: Float = 0F
        var heightDp: Dp = 0.dp
        var width: Float = 0F
        var widthDp: Dp = 0.dp
    }

    object Grid {
        const val numberOfRows = 3
        const val numberOfColumn = 2

        var cardPlaceWidth = 0F
        var cardPlaceWidthDp = 0.dp
        var cardPlaceHeight = 0F
        var cardPlaceHeightDp = 0.dp
    }

    object WinOverView {
        var topPaddingRatio = 0.1F
        var bottomPaddingRatio = 0.1F
        var startPaddingRatio = 0.1F
        var endPaddingRatio = 0.1F

        var topPadding = 0F
        var topPaddingDp = 0.dp
        var bottomPadding = 0F
        var bottomPaddingDp = 0.dp
        var startPadding = 0F
        var startPaddingDp = 0.dp
        var endPadding = 0F
        var endPaddingDp = 0.dp

//        var spaceWithPadd = 0F
        var height = 0F
        var heightDp = 0.dp
        var width = 0F
        var widthDp = 0.dp

//        var level
        var mapWidth = 0F
        var mapWidthInt = 0
//        var
    }

    private fun initWinOverview(context: Context) {
        val density = context.resources.displayMetrics.density

        winOverView.topPadding = grid.cardPlaceHeight * winOverView.topPaddingRatio
        winOverView.topPaddingDp = winOverView.topPadding.toDp(density)
        winOverView.bottomPadding = grid.cardPlaceHeight * winOverView.bottomPaddingRatio
        winOverView.bottomPaddingDp = winOverView.bottomPadding.toDp(density)
        winOverView.startPadding = grid.cardPlaceWidth * winOverView.startPaddingRatio
        winOverView.startPaddingDp = winOverView.startPadding.toDp(density)
        winOverView.endPadding = grid.cardPlaceWidth * winOverView.endPaddingRatio
        winOverView.endPaddingDp = winOverView.endPadding.toDp(density)

        winOverView.height = grid.cardPlaceHeight - winOverView.topPadding - winOverView.bottomPadding
        winOverView.heightDp = winOverView.height.toDp(density)
        winOverView.width = grid.cardPlaceWidth - winOverView.startPadding - winOverView.endPadding
        winOverView.widthDp = winOverView.width.toDp(density)

        winOverView.mapWidth = 0.3F * winOverView.width
        winOverView.mapWidthInt = (winOverView.mapWidth / density).toInt()

        displayUIData?.let {
            verbalLog("init", "initWinOverview")
            infoLog("topPadding", "${winOverView.topPadding}")
            infoLog("bottomPadding", "${winOverView.bottomPadding}")
            infoLog("startPadding", "${winOverView.startPadding}")
            infoLog("endPadding", "${winOverView.endPadding}")
            infoLog("topPaddingDp", "${winOverView.topPaddingDp}")
            infoLog("bottomPaddingDp", "${winOverView.bottomPaddingDp}")
            infoLog("startPaddingDp", "${winOverView.startPaddingDp}")
            infoLog("endPaddingDp", "${winOverView.endPaddingDp}")
            infoLog("winOvView height", "${winOverView.height}")
            infoLog("winOvView width", "${winOverView.width}")
            infoLog("winOvView heightDp", "${winOverView.heightDp}")
            infoLog("winOvView widthDp", "${winOverView.widthDp}")
            infoLog("map widthInt", "${winOverView.mapWidthInt}")
        }
    }

    private fun initGrid(context: Context) {
        val density = context.resources.displayMetrics.density

        grid.cardPlaceHeight = dimensions.height / grid.numberOfRows
        grid.cardPlaceHeightDp = grid.cardPlaceHeight.toDp(density)

        grid.cardPlaceWidth = dimensions.width / grid.numberOfColumn
        grid.cardPlaceWidthDp = grid.cardPlaceWidth.toDp(density)

        displayUIData?.let {
            verbalLog("init", "initGrid")
            infoLog("card Place Height", "${grid.cardPlaceHeight}")
            infoLog("card Place Width", "${grid.cardPlaceWidth}")
        }
    }

    private fun initDimensions(context: Context) {
        val widthFull = context.resources.displayMetrics.widthPixels
        val heightFull = context.resources.displayMetrics.heightPixels
        val density = context.resources.displayMetrics.density

        dimensions.height = (dimensions.heightWeight /
                (userStatsFirstPart.dimensions.heightWeight1 + userStatsSecondPart.dimensions.heightWeight + dimensions.heightWeight) ) * heightFull
        dimensions.heightDp = userStatsFirstPart.dimensions.height.toDp(density)
        dimensions.width = widthFull.toFloat()
        dimensions.widthDp = dimensions.width.toDp(density)

        displayUIData?.let {
            verbalLog("init", "initDimensions")
            infoLog("height", "${dimensions.height}")
            infoLog("width", "${dimensions.width}")
        }
    }

    fun create(context: Context): userStatsThirdPart {
        infoLog("create", "userThirdPart UI data")
        initDimensions(context)
        initGrid(context)
        initWinOverview(context)

        return this
    }


}