package com.mobilegame.robozzle.presentation.ui.Screen.RanksLevel

import android.content.Context
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.analyse.verbalLog
import com.mobilegame.robozzle.presentation.res.MyColor
import com.mobilegame.robozzle.presentation.ui.Navigation.displayUIData
import com.mobilegame.robozzle.utils.Extensions.toDp
import com.mobilegame.robozzle.utils.Extensions.toSp

object RanksLevelLayout {
    val header = Header
    val map = Map
    val list = ListPlayer

    object Header {
        val ratios = RatiosHeader
        val sizes = SizesHeader
        val colors = ColorsHeader
        val text = TextHeader

        object RatiosHeader {
            const val height = 0.8F
            const val text = 0.4F
        }
        object SizesHeader {
            var height = 0F
            var heightDp = Dp.Unspecified
            var width = 0F
            var widthDp = Dp.Unspecified
            var text = 0F
            var textDp = Dp.Unspecified
            var textSp = 0.sp
        }
        object ColorsHeader {
            val text = MyColor.whiteDark4
        }
        object TextHeader {
            val str = "Level : "
        }
    }

    object Map {
        val ratios = RatiosMap
        val sizes = SizesMap
        val padding = PaddingMap
        val colors = ColorMap

        object RatiosMap {
            const val height = 3F
            const val paddingTop = 0.1F
            const val paddingBottom = 0.3F
//            const val width = F
        }
        object SizesMap {
            var height = 0F
            var heightDp = Dp.Unspecified
            var width = 0F
            var widthDp = Dp.Unspecified
            var widhtInt = 0
        }
        object PaddingMap{
            var top = 0F
            var topDp = Dp.Unspecified
            var bottom = 0F
            var bottomDp = Dp.Unspecified
        }
        object ColorMap{
            var background = MyColor.grayDark6
        }
    }

    object ListPlayer {
        val ratios = RatiosListPlayer
        val sizes = SizesListPlayer
        val colors = ColorsListPlayer
        val text = TextListPlayer
        val padding = PaddingListPlayer

        object RatiosListPlayer {
            const val height = 10F
//            const val width = F
        }
        object SizesListPlayer {
            var height = 0F
            var heightDp = Dp.Unspecified
            var width = 0F
            var widthDp = Dp.Unspecified
        }
        object PaddingListPlayer {
            var topDp = 5.dp
            var bottomDp = 5.dp
            var startDp = 5.dp
        }
        object ColorsListPlayer {
            val pair = MyColor.grayDark4
            val notPair = MyColor.grayDark5
            val text = MyColor.whiteDark7
        }
        object TextListPlayer {
            val firstRow = "Player : "
            val secondRow = "Instruction : "
            val thirdRow = "Points : "
            val errorMessage = "Can't access the server for ranks"
        }
    }

    var widthFull = 0
    var heightFull = 0
    var density = 0F

    private fun initMap() {
        map.sizes.height = heightFull * (map.ratios.height / (map.ratios.height + header.ratios.height + list.ratios.height))
        map.sizes.heightDp = map.sizes.height.toDp(density)
        map.padding.top = map.sizes.height * map.ratios.paddingTop
        map.padding.topDp = map.padding.top.toDp(density)
        map.padding.bottom = map.sizes.height * map.ratios.paddingBottom
        map.padding.bottomDp = map.padding.bottom.toDp(density)
        map.sizes.widhtInt = (map.sizes.height - map.padding.bottom - map.padding.top).toDp(density).value.toInt()
//            map.sizes.heightDp.value.toInt()

        displayUIData?.let {
            verbalLog("RanksLevelLayout::initMap", "Start")
            infoLog("RanksLevelLayout::initMap", "height ${map.sizes.height}")
            infoLog("RanksLevelLayout::initMap", "heightDp ${map.sizes.heightDp}")
            infoLog("RanksLevelLayout::initMap", "paddingTop ${map.padding.top}")
            infoLog("RanksLevelLayout::initMap", "paddingTopDp ${map.padding.topDp}")
            infoLog("RanksLevelLayout::initMap", "paddingBottom ${map.padding.bottom}")
            infoLog("RanksLevelLayout::initMap", "paddingBottomDp ${map.padding.bottomDp}")
        }
    }

    private fun initHeader() {
        header.sizes.height = heightFull * (header.ratios.height / (map.ratios.height + header.ratios.height + list.ratios.height))
        header.sizes.heightDp = header.sizes.height.toDp(density)
        header.sizes.text = header.sizes.height * header.ratios.text
        header.sizes.textDp = header.sizes.text.toDp(density)
        header.sizes.textSp = header.sizes.text.toSp(density)

        displayUIData?.let {
            verbalLog("RanksLevelLayout::initHeader", "Start")
            infoLog("RanksLevelLayout::initHeader", "height ${header.sizes.height}")
            infoLog("RanksLevelLayout::initHeader", "heightDp ${header.sizes.heightDp}")
            infoLog("RanksLevelLayout::initHeader", "text ${header.sizes.text}")
            infoLog("RanksLevelLayout::initHeader", "textDp ${header.sizes.textDp}")
            infoLog("RanksLevelLayout::initHeader", "textSp ${header.sizes.textSp}")
        }
    }

    fun create(context: Context): RanksLevelLayout {
        widthFull = context.resources.displayMetrics.widthPixels
        heightFull = context.resources.displayMetrics.heightPixels
        density = context.resources.displayMetrics.density

        displayUIData?.let {
            verbalLog("RanksLevelLayout::create", "Start")
            infoLog("RanksLevelLayout::create", "heightFull ${heightFull}")
            infoLog("RanksLevelLayout::create", "widthFull ${widthFull}")
            infoLog("RanksLevelLayout::create", "density ${density}")
        }

        initHeader()
        initMap()

        return this
    }
}