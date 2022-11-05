package com.mobilegame.robozzle.data.layout.userStats

import android.content.Context
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.grayDark4
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.whiteDark4
import com.mobilegame.robozzle.utils.Extensions.toDp
import com.mobilegame.robozzle.utils.Extensions.toSp

object userStatsSecondPart {
    val dimensions = Dimensions
    val buttons = Button
    val allLevel = Button
    var density = 0F

    object Dimensions {
        const val heightWeight = 0.05f
        var height = 0F
        var width = 0F
    }


    object Button {
        val ratios = Ratios
        val sizes = Sizes
        val colors = Colors
        val padidng = Padding

        object Ratios {
            const val height = 0.5F
            const val text = 0.45F
//            const val width = 0.1F
        }
        object Sizes {
            var height = 0F
            var heightDp = Dp.Unspecified
            val elevation = 10.dp
            var text = 0F
            var textSp = 0.sp
        }
        object Colors {
            val text = whiteDark4
            val background = grayDark4
        }
        object Padding {
            val borders = 0.015F
            var bordersDp = Dp.Unspecified
            var bordersVertical = Dp.Unspecified
        }
    }

    fun create(context: Context): userStatsSecondPart {
        density = context.resources.displayMetrics.density

        initDimension(context)
        initButton(context)
        initAllLevel(context)
        return this
    }
    private fun initAllLevel(context: Context) {
        allLevel.padidng.bordersDp = (dimensions.width * allLevel.padidng.borders).toDp(density)
        allLevel.padidng.bordersVertical = allLevel.padidng.bordersDp / 2
        allLevel.sizes.text = (dimensions.height * allLevel.ratios.height) * allLevel.ratios.text
        allLevel.sizes.textSp = allLevel.sizes.text.toSp(density)
    }
    private fun initButton(context: Context) {
        val widthFull = context.resources.displayMetrics.widthPixels
        val heightFull = context.resources.displayMetrics.heightPixels
        val density = context.resources.displayMetrics.density


    }

    private fun initDimension(context: Context) {
        val widthFull = context.resources.displayMetrics.widthPixels
        val heightFull = context.resources.displayMetrics.heightPixels
        val density = context.resources.displayMetrics.density

        dimensions.height = heightFull * (userStatsFirstPart.dimensions.heightWeight1 /
                (userStatsFirstPart.dimensions.heightWeight1 + dimensions.heightWeight + userStatsThirdPart.dimensions.heightWeight) )
        dimensions.width = widthFull.toFloat()
    }
}