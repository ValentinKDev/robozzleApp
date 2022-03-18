package com.mobilegame.robozzle.data.configuration.inGame.layouts

import android.content.Context
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.Dp
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.model.level.Level
import com.mobilegame.robozzle.utils.Extensions.getSmallerFloat
import com.mobilegame.robozzle.utils.Extensions.getSmallerInt
import com.mobilegame.robozzle.utils.Extensions.toDp

object InGameFirstPart {
    val ratios = Ratios
    val size = Sizes
    val star = StarIcon
    val player = PlayerIcon

    object Ratios {
        const val height = 4F
        const val mapHeight = 0.95F
        const val mapWidth = 0.95F
        const val mapCasePadding = 0.004F

        const val playerBox = 0.8F
        const val playerCanvas = 0.7F

        const val starBox = 0.65F
        const val starCanvas = 0.78F
    }
    object Sizes {
        var width: Float = 0F
        var widthDp: Dp = Dp.Unspecified
        var height: Float = 0F
        var heightDp: Dp = Dp.Unspecified
        var mapHeight: Float = 0F
        var mapHeightDp: Dp = Dp.Unspecified
        var mapWidth: Float = 0F
        var mapWidthDp: Dp = Dp.Unspecified
        var mapCaseDp: Dp = Dp.Unspecified
        var mapCase: Float = 0F
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
    }


    object StarIcon {
        var width = 0F
        var center: Offset = Offset.Zero
        var decenter: Offset = Offset.Zero
        var pTop: Offset = Offset.Zero
        var pLeft: Offset = Offset.Zero
        var pRight: Offset = Offset.Zero
        var stroke: Float = 0F
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
    }

    fun init(context: Context, level: Level) {
        val widthFull = context.resources.displayMetrics.widthPixels
        val heightFull = context.resources.displayMetrics.heightPixels
        val density = context.resources.displayMetrics.density

        size.width = widthFull.toFloat()
        size.widthDp = size.width.toDp(density)
        size.height = heightFull * (Ratios.height / (Ratios.height + InGameSecondPart.Ratios.height + InGameThirdPart.Ratios.height))
        size.heightDp = size.height.toDp(density)

        size.mapWidth = size.width * ratios.mapWidth
        size.mapWidthDp = size.mapWidth.toDp(density)
        size.mapHeight = size.height * ratios.mapHeight
        size.mapHeightDp = size.mapHeight.toDp(density)

        size.mapCasePadding = size.mapWidth * ratios.mapCasePadding
        size.mapCasePaddingDp = size.mapCasePadding.toDp(density)
        val mapCaseByWidth = (size.mapWidth - ((1 + level.map.first().length ) * size.mapCasePadding)) / level.map.first().length
        val mapCaseByHeight = (size.mapHeight - ((1 + level.map.size ) * size.mapCasePadding)) / level.map.size
        size.mapCase = getSmallerFloat(mapCaseByWidth, mapCaseByHeight)
        size.mapCaseDp = size.mapCase.toDp(density)

        size.playerBox = size.mapCase * ratios.playerBox
        size.playerBoxDp = size.playerBox.toDp(density)
        size.playerCanvas = size.playerBox * ratios.playerCanvas
        size.playerCanvasDp = size.playerCanvas.toDp(density)

        size.starBox = size.mapCase * ratios.starBox
        size.starBoxDp = size.starBox.toDp(density)
        size.starCanvas = size.starBox * ratios.starCanvas
        size.starCanvasDp = size.starCanvas.toDp(density)

        infoLog(" density ", "${density}")
        infoLog(" width ", "${size.width}")

        infoLog(" widthDp ", "${size.widthDp}")
        infoLog(" mapWidth ", "${size.mapWidth}")
        infoLog(" height ", "${size.height}")
        infoLog(" heightDp ", "${size.heightDp}")
        infoLog(" mapHeight ", "${size.mapHeightDp}")
        infoLog(" mapCasePadding ", "${size.mapCasePadding}")
        infoLog(" mapCasePaddingDp ", "${size.mapCasePaddingDp}")
        infoLog(" mapCase ", "${size.mapCase}")
        infoLog(" mapCaseDp ", "${size.mapCaseDp}")

        infoLog(" playerBox ", "${size.playerBox}")
        infoLog(" playerBoxDp ", "${size.playerBoxDp}")
        infoLog(" playerCanvas ", "${size.playerCanvas}")
        infoLog(" playerCanvasDp ", "${size.playerCanvasDp}")
//        infoLog(" playerCanvasWidth ", "${size.}")

        infoLog(" starBox ", "${size.starBox}")
        infoLog(" starBoxDp ", "${size.starBoxDp}")
        infoLog(" starCanvas ", "${size.starCanvas}")
        infoLog(" starCanvasDp ", "${size.starCanvasDp}")

        initStarCanvasData()
        initPlayerIcon()

    }

    fun initPlayerIcon() {
        val sizeCanvas = if (size.playerCanvas != 0F) size.playerCanvas else 55F
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

        infoLog(" ", "\t init player canvas")
        infoLog(" sizeCanvas ", "${size.playerCanvas}")
        infoLog(" pBottomCenter ", "${player.pBottomCenter}")
//        player.decenterLeft = Offset(x = center.x - (0.05F * sizeCanvas), y = center.y)
    }

    fun initStarCanvasData() {
        val sizeCanvas = if (size.starCanvas != 0F) size.starCanvas else 55F
        val center = Offset(x = sizeCanvas / 2F, y = sizeCanvas / 2F)
        val vertical1 = (0.022F * sizeCanvas)
        val vertical2 = center.x - (0.118F * sizeCanvas)
        val vertical3 = center.x + (0.118F * sizeCanvas)
        val horizontal = 0.343F * sizeCanvas
        star.center = center
        star.decenter = Offset(x = center.x + (0.01F * size.starCanvas), y = center.x + (0.01F * size.starCanvas))
        star.pTop = Offset(x = center.x, y = vertical1)
        star.pLeft = Offset(x = vertical2, y =  horizontal)
        star.pRight = Offset(x = vertical3, y = horizontal)
        star.stroke = sizeCanvas / 50F

        infoLog(" ", "\t init star canvas")
        infoLog(" starCanvasCenterOfferset ", "${star.center}")
        infoLog(" starCanvasDecenterOfferset ", "${star.decenter}")
        infoLog(" vertical1 ", "$vertical1")
        infoLog(" vertical2 ", "$vertical2")
        infoLog(" vertical3 ", "$vertical3")
        infoLog(" pTop ", "${star.pTop}")
        infoLog(" pLeft ", "${star.pLeft}")
        infoLog(" pRight ", "${star.pRight}")
//        newOffset
//    }
    }
}