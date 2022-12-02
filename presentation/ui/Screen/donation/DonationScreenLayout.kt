package com.mobilegame.robozzle.presentation.ui.Screen.donation

import android.content.Context
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.analyse.verbalLog
import com.mobilegame.robozzle.presentation.res.MyColor
import com.mobilegame.robozzle.presentation.ui.Navigation.displayUIData
import com.mobilegame.robozzle.presentation.ui.Screen.RanksLevel.RanksLevelLayout
import com.mobilegame.robozzle.utils.Extensions.toDp
import com.mobilegame.robozzle.utils.Extensions.toSp

object DonationScreenLayout {
    val header = Header
    val presentation = PresentationDonation
    val selector = SelectorDonation

    object SelectorDonation {
        val ratios = RatiosSelector
        object RatiosSelector {
            const val heightWeight = 7F
        }
    }
    object PresentationDonation {
        val ratios = RatiosPresentation
        val paddings = PaddingsPresentation
        object RatiosPresentation {
            const val heightWeight = 3F
        }
        object PaddingsPresentation {
            const val horizontalPadding = 0.05F
        }
    }
    object Header {
        val sizes = SizesHeader
        val ratios = RatiosHeader
        val colors = ColorsHeader
        val text = TextHeader

        object RatiosHeader {
            var height = 1F
            var text = 0.35F
        }
        object SizesHeader {
            var height = 0F
            var text = 0F
            var textDp = Dp.Unspecified
            var textSp = 0.sp
        }
        object ColorsHeader {
            val text = MyColor.whiteDark4
        }
        object TextHeader {
            const val line = "Donation"
        }
    }

//    object Header {
//        val ratios = RatiosHeader
//        val sizes = SizesHeader
//        val colors = ColorsHeader
//        val text = TextHeader
//
//        object RatiosHeader {
//            const val heightWeight = 6F
//        }
//        object SizesHeader {
//            var height = 0F
//            var heightDp = Dp.Unspecified
//            var width = 0F
//            var widthDp = Dp.Unspecified
//        }
//        object TextHeader {
//            const val str = "Donations"
//        }
//        object ColorsHeader {
//            val text = MyColor.whiteDark4
//        }
//    }

    var widthFull = 0
    var heightFull = 0
    var density = 0F

    private fun initHeader() {
        header.sizes.height = heightFull * (header.ratios.height / (header.ratios.height + presentation.ratios.heightWeight + selector.ratios.heightWeight))
        header.sizes.text = header.sizes.height * header.ratios.text
        header.sizes.textDp = header.sizes.text.toDp(density)
        header.sizes.textSp = header.sizes.text.toSp(density)

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