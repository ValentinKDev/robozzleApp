package com.mobilegame.robozzle.data.layout.userStats

import android.content.Context
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.analyse.verbalLog
import com.mobilegame.robozzle.presentation.res.MyColor
import com.mobilegame.robozzle.utils.Extensions.toDp
import com.mobilegame.robozzle.utils.Extensions.toSp

object userStatsFirstPart {
    val dimensions = Dimensions
    val buttonLogOut = ButtonLogOut
    val header = Header

    object Dimensions {
        const val heightWeight1 = 0.1f
        var height: Float = 0F
        var heightDp: Dp = 0.dp
    }

    object Header {
        val ratios = Ratios
        val sizes = Sizes
        val colors = Colors

        object Ratios {
            const val height = 0.034F
            const val width = 0.6F
        }
        object Sizes {
            var height = 0F
            var heightDp = Dp.Unspecified
            var width = 0F
            var widthDp = Dp.Unspecified
            var elevation = 15.dp
        }
        object Colors {
            val text = MyColor.whiteDark4
            val background = MyColor.grayDark4
        }
    }

    object ButtonLogOut {
        val ratios = Ratios
        val sizes = Sizes
        val padding = Padding
        val colors = Colors
        val text = "Log Out"

        object Ratios {
            const val height = 0.03F
            const val width = 0.12F
            const val text = 0.6F
            const val textWidth = 0.24F
        }

        object Sizes {
            var height = 0F
            var width = 0F
            var heightDp = Dp.Unspecified
            var widthDp = Dp.Unspecified
            var text = 0F
            var textSp = 0.sp
            var elevation = 18.dp
        }

        object Padding {
            const val start = 0.83F
            const val end = 0.5F
            const val top = 0.33F
            const val bottom = 0.33F
        }
        object Colors {
            val background = MyColor.grayDark5
            val text = MyColor.redDark3
        }
    }

    fun create(context: Context): userStatsFirstPart {
        val widthFull = context.resources.displayMetrics.widthPixels
        val heightFull = context.resources.displayMetrics.heightPixels
        val density = context.resources.displayMetrics.density

        errorLog("create", "First Part")
        initDimensions(context)
        initButtonLogOut(context)
        initHeader(context)
        return this
    }

    private fun initHeader(context: Context){
        val heightFull = context.resources.displayMetrics.heightPixels
        val widthFull = context.resources.displayMetrics.widthPixels
        val density = context.resources.displayMetrics.density

        header.sizes.height = heightFull * header.ratios.height
        header.sizes.heightDp = header.sizes.height.toDp(density)
        header.sizes.width = widthFull * header.ratios.width
        header.sizes.widthDp = header.sizes.width.toDp(density)
    }

    private fun initButtonLogOut(context: Context) {
        val heightFull = context.resources.displayMetrics.heightPixels
        val widthFull = context.resources.displayMetrics.widthPixels
        val density = context.resources.displayMetrics.density

        buttonLogOut.sizes.height = heightFull * buttonLogOut.ratios.height
        buttonLogOut.sizes.heightDp = buttonLogOut.sizes.height.toDp(density)
        buttonLogOut.sizes.width = widthFull * buttonLogOut.ratios.width
        buttonLogOut.sizes.widthDp = buttonLogOut.sizes.width.toDp(density)

//        buttonLogOut.sizes.text = buttonLogOut.sizes.height * buttonLogOut.ratios.text
        buttonLogOut.sizes.text = buttonLogOut.sizes.width * buttonLogOut.ratios.textWidth
        buttonLogOut.sizes.textSp = buttonLogOut.sizes.text.toSp(density)
    }

    private fun initDimensions(context: Context) {
        val heightFull = context.resources.displayMetrics.heightPixels
        val widthFull = context.resources.displayMetrics.widthPixels
        val density = context.resources.displayMetrics.density

        dimensions.height = heightFull * (dimensions.heightWeight1 /
                (dimensions.heightWeight1 + userStatsSecondPart.dimensions.heightWeight + userStatsThirdPart.dimensions.heightWeight) )
        dimensions.heightDp = dimensions.height.toDp(density)
        verbalLog("init", "dimensions")
        infoLog("height", "${dimensions.height}")
        infoLog("heightDp", "${dimensions.heightDp}")
    }
}