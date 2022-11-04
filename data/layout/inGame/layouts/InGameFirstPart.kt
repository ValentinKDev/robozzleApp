package com.mobilegame.robozzle.data.layout.inGame.layouts

import android.content.Context
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.Dp
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.model.level.Level
import com.mobilegame.robozzle.utils.Extensions.getSmallerFloat
import com.mobilegame.robozzle.utils.Extensions.toDp

object InGameFirstPart {
    val ratios = Ratios
    val sizes = Sizes
    val star = StarIcon
    val player = PlayerIcon
    var initiated = false

    object Ratios {
        const val height = 4F
        const val mapHeight = 0.98F
        const val mapWidth = 0.98F
        const val mapCasePadding = 0.004F
        const val mapCaseStroke = 0.07F

        const val playerBox = 0.9F
        const val playerCanvas = 0.82F

        const val starBox = 0.75F
        const val starCanvas = 0.78F
        const val starBoxNeon = 0.63F
    }
    object Sizes {
        var width: Float = 0F
        var widthDp: Dp = Dp.Unspecified
        var height: Float = 0F
        var heightDp: Dp = Dp.Unspecified

        var mapLayoutWidth: Float = 0F
        var mapLayoutWidthDp: Dp = Dp.Unspecified
        var mapLayoutHeight: Float = 0F
        var mapLayoutHeightDp: Dp = Dp.Unspecified
        var mapHeight: Float = 0F
        var mapHeightDp: Dp = Dp.Unspecified
        var mapWidth: Float = 0F
        var mapWidthDp: Dp = Dp.Unspecified
        var mapCaseDp: Dp = Dp.Unspecified
        var mapCase: Float = 0F
        var mapCaseStroke: Float = 0F
        var mapCasePadding: Float = 0F
        var mapCasePaddingDp: Dp = Dp.Unspecified

        var playerBox: Float = 0F
        var playerBoxDp: Dp = Dp.Unspecified
        var playerCanvas: Float = 0F
        var playerCanvasDp: Dp = Dp.Unspecified
//        var playerCanvasWidth: Float = 0F

        var starBox: Float = 0F
        var starBoxDp: Dp = Dp.Unspecified
        var starCanvas: Float = 0F
        var starCanvasDp: Dp = Dp.Unspecified
        var starCanvasBigger: Float = 0F
        var starCanvasBiggerDp: Dp = Dp.Unspecified
    }


    object PlayerIcon {
        var width = 0F

        var pCenter: Offset = Offset.Zero
        var pBottomCenter: Offset = Offset.Zero
        var pBottomLeft: Offset = Offset.Zero
        var pBottomRight: Offset = Offset.Zero
        var pTop: Offset = Offset.Zero
        var pNeonLeftEnd: Offset = Offset.Zero
        var pNeonRightEnd: Offset = Offset.Zero
        var strokeWidth: Float = 0F
        var strokeWidthSmall: Float = 0F
    }

    object StarIcon {
        var width = 0F
        var starNeonBoxFront: Float = 0F
        var starNeonBoxFrontDp: Dp = Dp.Unspecified
        var center: Offset = Offset.Zero
        var decenterRight: Offset = Offset.Zero
        var decenterLeft: Offset = Offset.Zero
        var pTop: Offset = Offset.Zero
        var pLeft: Offset = Offset.Zero
        var pRight: Offset = Offset.Zero
        var stroke: Float = 0F

        var centerBigger: Offset = Offset.Zero
        var decenterRightBigger: Offset = Offset.Zero
        var decenterLeftBigger: Offset = Offset.Zero
        var pTopBigger: Offset = Offset.Zero
        var pLeftBigger: Offset = Offset.Zero
        var pRightBigger: Offset = Offset.Zero
    }

    fun initStarCanvasData(density: Float) {
        sizes.starBox = sizes.mapCase * ratios.starBox
        sizes.starBoxDp = sizes.starBox.toDp(density)
        sizes.starCanvas = sizes.starBox * ratios.starCanvas
        sizes.starCanvasDp = sizes.starCanvas.toDp(density)
        sizes.starCanvasBigger = sizes.starBox * 1.2F
        sizes.starCanvasBiggerDp = sizes.starCanvas.toDp(density)

        star.starNeonBoxFront = sizes.starBox * ratios.starBoxNeon
        star.starNeonBoxFrontDp = star.starNeonBoxFront.toDp(density)
        val sizeCanvas = if (sizes.starCanvas != 0F) sizes.starCanvas else 55F
        val center = Offset(x = sizeCanvas / 2F, y = sizeCanvas / 2F)
        val vertical1 = (0.022F * sizeCanvas)
        val vertical2 = center.x - (0.118F * sizeCanvas)
        val vertical3 = center.x + (0.118F * sizeCanvas)
        val horizontal = 0.343F * sizeCanvas
        star.center = center
        star.decenterRight = Offset(x = center.x + (0.01F * sizes.starCanvas), y = center.x + (0.01F * sizes.starCanvas))
        star.decenterLeft = Offset(x = center.x - (0.01F * sizes.starCanvas), y = center.x + (0.01F * sizes.starCanvas))
        star.pTop = Offset(x = center.x, y = vertical1)
        star.pLeft = Offset(x = vertical2, y =  horizontal)
        star.pRight = Offset(x = vertical3, y = horizontal)
        star.stroke = sizeCanvas / 50F

        star.centerBigger = center
        star.decenterRightBigger = Offset(x = center.x + (0.01F * sizes.starCanvasBigger), y = center.x + (0.01F * sizes.starCanvasBigger))
        star.decenterLeftBigger = Offset(x = center.x - (0.01F * sizes.starCanvasBigger), y = center.x + (0.01F * sizes.starCanvasBigger))
        star.pTopBigger = Offset(x = center.x, y = vertical1)
        star.pLeftBigger = Offset(x = vertical2, y =  horizontal)
        star.pRightBigger = Offset(x = vertical3, y = horizontal)

        infoLog(" ", "\t init star canvas")
        infoLog(" starCanvasCenterOfferset ", "${star.center}")
        infoLog(" starCanvasDecenterOfferset ", "${star.decenterRight}")
        infoLog(" vertical1 ", "$vertical1")
        infoLog(" vertical2 ", "$vertical2")
        infoLog(" vertical3 ", "$vertical3")
        infoLog(" pTop ", "${star.pTop}")
        infoLog(" pLeft ", "${star.pLeft}")
        infoLog(" pRight ", "${star.pRight}")
    }

    fun init(context: Context, level: Level) {
        val widthFull = context.resources.displayMetrics.widthPixels
        val heightFull = context.resources.displayMetrics.heightPixels
        val density = context.resources.displayMetrics.density

        sizes.width = widthFull.toFloat()
        sizes.widthDp = sizes.width.toDp(density)
        sizes.height = heightFull * (Ratios.height / (Ratios.height + InGameSecondPart.Ratios.height + InGameThirdPart.Ratios.height))
        sizes.heightDp = sizes.height.toDp(density)

        sizes.mapLayoutWidth = sizes.width * ratios.mapWidth
        sizes.mapLayoutWidthDp = sizes.mapLayoutWidth.toDp(density)
        sizes.mapLayoutHeight = sizes.height * ratios.mapHeight
        sizes.mapLayoutHeightDp = sizes.mapLayoutHeight.toDp(density)

        val casePaddingByHeight = sizes.mapLayoutHeight * ratios.mapCasePadding
        val casePaddingByWidth = sizes.mapLayoutWidth * ratios.mapCasePadding
        sizes.mapCasePadding = getSmallerFloat(casePaddingByHeight, casePaddingByWidth)
        sizes.mapCasePaddingDp = sizes.mapCasePadding.toDp(density)
        val mapCaseByWidth = (sizes.mapLayoutWidth - ((1 + level.map.first().length ) * sizes.mapCasePadding)) / level.map.first().length
        val mapCaseByHeight = (sizes.mapLayoutHeight - ((1 + level.map.size ) * sizes.mapCasePadding)) / level.map.size
        sizes.mapCase = getSmallerFloat(mapCaseByWidth, mapCaseByHeight)
        sizes.mapCaseDp = sizes.mapCase.toDp(density)
//        size.mapCaseStrokeDp = (size.mapCase * ratios.mapCaseStroke).toDp(density)
        sizes.mapCaseStroke = 3F

        sizes.mapWidth = ((sizes.mapCase + sizes.mapCasePadding) * level.map.first().length) - sizes.mapCasePadding
        sizes.mapWidthDp = sizes.mapWidth.toDp(density)
        sizes.mapHeight = ((sizes.mapCase + sizes.mapCasePadding) * level.map.size) - sizes.mapCasePadding
        sizes.mapHeightDp = sizes.mapHeight.toDp(density)

        sizes.playerBox = sizes.mapCase * ratios.playerBox
        sizes.playerBoxDp = sizes.playerBox.toDp(density)
        sizes.playerCanvas = sizes.playerBox * ratios.playerCanvas
        sizes.playerCanvasDp = sizes.playerCanvas.toDp(density)


        infoLog(" density ", "${density}")
        infoLog(" width ", "${sizes.width}")

        infoLog(" widthDp ", "${sizes.widthDp}")
        infoLog(" mapWidth ", "${sizes.mapWidth}")
        infoLog(" mapLayoutWidth ", "${sizes.mapLayoutWidth}")
        infoLog(" height ", "${sizes.height}")
        infoLog(" heightDp ", "${sizes.heightDp}")
        infoLog(" mapHeight ", "${sizes.mapHeightDp}")
        infoLog(" mapLayoutHeight ", "${sizes.mapLayoutHeight}")
        infoLog(" mapCasePadding ", "${sizes.mapCasePadding}")
        infoLog(" mapCasePaddingDp ", "${sizes.mapCasePaddingDp}")
        infoLog(" mapCase ", "${sizes.mapCase}")
        infoLog(" mapCaseDp ", "${sizes.mapCaseDp}")

        infoLog(" playerBox ", "${sizes.playerBox}")
        infoLog(" playerBoxDp ", "${sizes.playerBoxDp}")
        infoLog(" playerCanvas ", "${sizes.playerCanvas}")
        infoLog(" playerCanvasDp ", "${sizes.playerCanvasDp}")
//        infoLog(" playerCanvasWidth ", "${size.}")

        infoLog(" starBox ", "${sizes.starBox}")
        infoLog(" starBoxDp ", "${sizes.starBoxDp}")
        infoLog(" starCanvas ", "${sizes.starCanvas}")
        infoLog(" starCanvasDp ", "${sizes.starCanvasDp}")

        initStarCanvasData(density)
        initPlayerIcon()
        initiated = true
    }

    fun initPlayerIcon() {
        val sizeCanvas = if (sizes.playerCanvas != 0F) sizes.playerCanvas else 55F
        val center = Offset(x = sizeCanvas / 2F, y = sizeCanvas / 2F)

        player.pCenter = center
        player.pBottomCenter = Offset( x = sizeCanvas / 5F, y = center.y)
        player.pBottomLeft = Offset(x = 0F,y = 0.07F * sizeCanvas)
        player.pBottomRight = Offset(x = 0F,y = 0.93F * sizeCanvas)
        player.pTop = Offset(sizeCanvas, center.y)

        val baseOffsetLeft = player.pBottomCenter.minus(player.pBottomLeft)
        player.pNeonLeftEnd = Offset(player.pBottomCenter.x - (baseOffsetLeft.x * 0.5F) , player.pBottomCenter.y - (baseOffsetLeft.y * 0.5F))
        player.pNeonRightEnd = Offset(player.pBottomCenter.x - (baseOffsetLeft.x * 0.5F) , player.pBottomCenter.y + (baseOffsetLeft.y * 0.5F))
        player.strokeWidth = sizeCanvas * 0.04F
        player.strokeWidthSmall = sizeCanvas * 0.02F

        infoLog(" ", "\t init player canvas")
        infoLog(" sizeCanvas ", "${sizes.playerCanvas}")
        infoLog(" pBottomCenter ", "${player.pBottomCenter}")
//        player.decenterLeft = Offset(x = center.x - (0.05F * sizeCanvas), y = center.y)
    }

}