package com.mobilegame.robozzle.data.layout.userStats

import android.content.Context

object userStatsSecondPart {
    val dimensions = Dimensions

    object Dimensions {
        const val heightWeight = 0.05f
    }

    fun create(context: Context): userStatsSecondPart {
        val widthFull = context.resources.displayMetrics.widthPixels
        val heightFull = context.resources.displayMetrics.heightPixels
        val density = context.resources.displayMetrics.density

        return this
    }
}