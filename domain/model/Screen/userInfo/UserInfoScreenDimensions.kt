package com.mobilegame.robozzle.domain.model.Screen.userInfo

import android.content.Context
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilegame.robozzle.analyse.infoLog
import kotlinx.coroutines.launch

class UserInfoScreenDimensions(): ViewModel() {
    /** Parts */
    val firstPartScreenWeight = 0.1f
    val secondPartScreenWeight = 0.05f
    val thirdPartScreenWeight = 0.85f

    /** Elements */
    val logoutButtonRatioHeight = 0.05f

    val filterListButtonElevation = 8.dp

    val cardNameElevation = 15.dp
    //    val cardNamePadding = 0.07f
    //todo: test the name with the maximum name lenght
    val cardNameRatioWidth = 0.30f
    val cardNameRatioHeight = 0.075f

    var mapOverViewSize: Int = 0

    enum class WinOverViewDimensions { Width, Height, PaddingTop }
    var winOverViewDimensions: MutableMap<WinOverViewDimensions, Float> = mutableMapOf()
    fun setWinOverViewDimensions(layoutCoordinates: LayoutCoordinates, context: Context) {
        viewModelScope.launch {
            if (winOverViewDimensions[WinOverViewDimensions.Height] == null) {
                val density = context.resources.displayMetrics.density

                val height = layoutCoordinates.boundsInRoot().height
                winOverViewDimensions[WinOverViewDimensions.Height] = height * 0.25f

                val width = layoutCoordinates.boundsInRoot().width
                val widthDp = width / density
                infoLog("width", "${width}")
                winOverViewDimensions[WinOverViewDimensions.PaddingTop] = widthDp * 0.05f
                infoLog("paddingTop", "${winOverViewDimensions[WinOverViewDimensions.PaddingTop]}")
                winOverViewDimensions[WinOverViewDimensions.Width] = widthDp

                mapOverViewSize = (widthDp / 2.5F).toInt()
                infoLog("mapOverViewSize", "$mapOverViewSize")
            }
        }
    }


    fun initWinOverViewDimensions(): MutableMap<WinOverViewDimensions, Float?> = mutableMapOf(
        WinOverViewDimensions.Width to null,
        WinOverViewDimensions.Height to null,
        WinOverViewDimensions.PaddingTop to null,
    )
}