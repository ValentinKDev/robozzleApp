package com.mobilegame.robozzle.data.layout.userStats

import android.content.Context
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.utils.Extensions.toDp

object userStatsFirstPart {
    val dimensions = Dimensions

    object Dimensions {
        const val heightWeight1 = 0.1f
        var height: Float = 0F
        var heightDp: Dp = 0.dp
    }

    fun create(context: Context): userStatsFirstPart {
        val widthFull = context.resources.displayMetrics.widthPixels
        val heightFull = context.resources.displayMetrics.heightPixels
        val density = context.resources.displayMetrics.density

        initDimensions(context)
        return this
    }

    private fun initDimensions(context: Context) {
        val heightFull = context.resources.displayMetrics.heightPixels
        val widthFull = context.resources.displayMetrics.widthPixels
        val density = context.resources.displayMetrics.density

        dimensions.height = (dimensions.heightWeight1 /
                (dimensions.heightWeight1 + userStatsSecondPart.dimensions.heightWeight + userStatsThirdPart.dimensions.heightWeight)
                ) * heightFull
        dimensions.heightDp = dimensions.height.toDp(density)
    }
}