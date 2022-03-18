package com.mobilegame.robozzle.data.configuration.inGame.layouts

import android.content.Context
import androidx.compose.ui.geometry.Offset
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.model.level.Level

object InGameFirstPart {
    val ratios = Ratios
    val size = Sizes
    val star = StarIcon

    object Ratios {
        const val height = 4F
        const val mapHeight = 0.95F
        const val mapWidht = 0.95F

        const val playerIcon = 0.65F

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

        size.playerIconDp = (size.mapCaseDp.toFloat() * ratios.playerIcon).toInt()

        size.starIconDp = (size.mapCaseDp * ratios.starIcon).toInt()
        size.starCanvasDp = (size.starIconDp * ratios.starCanvas).toInt()
        size.starCanvasWidth = size.starCanvasDp * density

        initStarCanvasData()


        infoLog(" density ", "${density}")
        infoLog(" width ", "${size.width}")

        infoLog(" widthDp ", "${size.widthDp}")
        infoLog(" mapWidth ", "${size.mapWidth}")
        infoLog(" height ", "${size.height}")
        infoLog(" heightDp ", "${size.heightDp}")
        infoLog(" mapHeight ", "${size.mapHeightDp}")
        infoLog(" mapCaseDp ", "${size.mapCaseDp}")
        infoLog(" mapCase ", "${size.mapCase}")
        infoLog(" playerIconDp ", "${size.playerIconDp}")

        infoLog(" starIconDp ", "${size.starIconDp}")
        infoLog(" starCanvasDp ", "${size.starCanvasDp}")
        infoLog(" starCanvasWidth ", "${size.starCanvasWidth}")
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