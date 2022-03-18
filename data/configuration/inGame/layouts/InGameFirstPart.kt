package com.mobilegame.robozzle.data.configuration.inGame.layouts

import android.content.Context
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.model.level.Level
import com.mobilegame.robozzle.utils.Extensions.toDp

object InGameFirstPart {
    val ratios = Ratios
    val size = Sizes
    val star = StarIcon
    val player = PlayerIcon

    object Ratios {
        const val height = 4F
        const val mapHeight = 0.95F
        const val mapWidht = 0.95F

        const val playerIcon = 0.65F
        const val playerCanvas = 0.9F

        const val starIcon = 0.65F
        const val starCanvas = 0.78F
//        const val starIcon = 0.70F
//        const val starCanvas = 0.8F
    }
    object Sizes {
        var width: Int = 0
        var widthDp: Int = 0
        var height: Int = 0
        var heightDp: Int = 0
        var mapHeightDp: Int = 0
        var mapWidth: Int = 0
        var mapWidthDp: Int = 0
        var mapCaseDp: Int = 0
        var mapCase: Int = 0

        var playerIconDp: Int = 0
        var playerIcon: Float = 0F
        var playerCanvasDp: Int = 0
        var playerCanvas: Float = 0F
        var playerCanvasWidth: Float = 0F

        var starIconDp: Int = 0
        var starCanvasDp: Int = 0
        var starCanvasWidth: Float = 0F
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

    fun initPlayerIcon() {
        val sizeCanvas = if (size.playerCanvasWidth != 0F) size.playerCanvasWidth else 55F
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
        infoLog(" sizeCanvas ", "${size.playerCanvasWidth}")
        infoLog(" pBottomCenter ", "${player.pBottomCenter}")
//        player.decenterLeft = Offset(x = center.x - (0.05F * sizeCanvas), y = center.y)
    }

    fun init(context: Context, level: Level) {
        val widthFull = context.resources.displayMetrics.widthPixels
        val heightFull = context.resources.displayMetrics.heightPixels
        val density = context.resources.displayMetrics.density

        size.width = (widthFull).toInt()
        size.widthDp = (size.width / density).toInt()
        size.height = (heightFull * Ratios.height).toInt()
        size.heightDp = (size.height / density).toInt()

        size.mapWidth = (size.width * ratios.mapWidht).toInt()
        size.mapWidthDp = (size.widthDp * ratios.mapWidht).toInt()
        size.mapHeightDp = (size.heightDp * ratios.mapHeight).toInt()
        size.mapCaseDp = (size.mapWidthDp / level.map.size)
        size.mapCase = (size.mapWidth / level.map.size)

        size.playerIcon = size.mapCase * ratios.playerIcon
        size.playerIconDp = size.playerIcon.toDp(density)
//        size.playerCanvasDp = (size.playerIconDp * ratios.playerCanvas).toInt()
//        size.playerCanvasWidth = size.playerCanvasDp * density
        size.playerCanvas = size.playerIcon * ratios.playerCanvas
        size.playerCanvasDp = size.playerCanvas.toDp(density)
        size.playerCanvasWidth = size.playerCanvasDp * density

        size.starIconDp = (size.mapCaseDp * ratios.starIcon).toInt()
        size.starCanvasDp = (size.starIconDp * ratios.starCanvas).toInt()
        size.starCanvasWidth = size.starCanvasDp * density

        infoLog(" density ", "${density}")
        infoLog(" width ", "${size.width}")

        infoLog(" widthDp ", "${size.widthDp}")
        infoLog(" mapWidth ", "${size.mapWidth}")
        infoLog(" height ", "${size.height}")
        infoLog(" heightDp ", "${size.heightDp}")
        infoLog(" mapHeight ", "${size.mapHeightDp}")
        infoLog(" mapCaseDp ", "${size.mapCaseDp}")
        infoLog(" mapCase ", "${size.mapCase}")

        infoLog(" playerIcon ", "${size.playerIcon}")
        infoLog(" playerIconDp ", "${size.playerIconDp}")
        infoLog(" playerCanvas ", "${size.playerCanvas}")
        infoLog(" playerCanvasDp ", "${size.playerCanvasDp}")
        infoLog(" playerCanvasWidth ", "${size.playerCanvasWidth}")

        infoLog(" starIconDp ", "${size.starIconDp}")
        infoLog(" starCanvasDp ", "${size.starCanvasDp}")
        infoLog(" starCanvasWidth ", "${size.starCanvasWidth}")

        initStarCanvasData()
        initPlayerIcon()

    }


    fun initStarCanvasData() {
        val sizeCanvas = if (size.starCanvasWidth != 0F) size.starCanvasWidth else 55F
        val center = Offset(x = sizeCanvas / 2F, y = sizeCanvas / 2F)
        val vertical1 = (0.022F * sizeCanvas)
        val vertical2 = center.x - (0.118F * sizeCanvas)
        val vertical3 = center.x + (0.118F * sizeCanvas)
        val horizontal = 0.343F * sizeCanvas
        star.center = center
        star.decenter = Offset(x = center.x + (0.01F * size.starCanvasWidth), y = center.x + (0.01F * size.starCanvasWidth))
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