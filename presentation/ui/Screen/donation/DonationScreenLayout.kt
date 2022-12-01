package com.mobilegame.robozzle.presentation.ui.Screen.donation

import android.content.Context
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.analyse.verbalLog
import com.mobilegame.robozzle.presentation.res.MyColor
import com.mobilegame.robozzle.presentation.ui.Navigation.displayUIData
import com.mobilegame.robozzle.presentation.ui.Screen.RanksLevel.RanksLevelLayout

object DonationScreenLayout {
    val header = Header

//    object Header {
//        val sizes = SizesHeader
//        val ratios = RatiosHeader
//        val colors = ColorsHeader
//        val text = TextHeader
//
//        object RatiosHeader {
//            var height = 1F
//            var text = 0.35F
//        }
//        object SizesHeader {
//            var height = 0F
//            var text = 0F
//            var textDp = Dp.Unspecified
//            var textSp = 0.sp
//        }
//        object ColorsHeader {
//            val text = MyColor.whiteDark4
//        }
//        object TextHeader {
//            const val line = "Config"
//        }
//    }

    object Header {
        val ratios = RatiosHeader
        val sizes = SizesHeader
        val colors = ColorsHeader
        val text = TextHeader

        object RatiosHeader {
            const val heightWeight = 6F
        }
        object SizesHeader {
            var height = 0F
            var heightDp = Dp.Unspecified
            var width = 0F
            var widthDp = Dp.Unspecified
        }
        object TextHeader {
            const val str = "Donations"
        }
        object ColorsHeader {
            val text = MyColor.whiteDark4
        }
    }

    var widthFull = 0
    var heightFull = 0
    var density = 0F

    private fun initHeader() {

    }

    fun create(context: Context): DonationScreenLayout {
        widthFull = context.resources.displayMetrics.widthPixels
        heightFull = context.resources.displayMetrics.heightPixels
        density = context.resources.displayMetrics.density


        displayUIData?.let {
            verbalLog("RanksLevelLayout::create", "Start")
            infoLog("RanksLevelLayout::create", "heightFull ${RanksLevelLayout.heightFull}")
            infoLog("RanksLevelLayout::create", "widthFull ${RanksLevelLayout.widthFull}")
            infoLog("RanksLevelLayout::create", "density ${RanksLevelLayout.density}")
        }
        initHeader()
        return this
    }
}