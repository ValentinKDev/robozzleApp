package com.mobilegame.robozzle.presentation.ui.Screen.Tuto

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.presentation.res.MyColor

object TutoObj {
    val colors = ColorsTuto
    val popup = PopupTuto

    object ColorsTuto {
        val filter = MyColor.black3
        val transparent = Color.Transparent
        val enlightAnyInit = MyColor.gray1
        val enlightAnyTarget = MyColor.white8
//        val enlightAnyTarget = MyColor.white4
        val enlighteningButtonInitial = MyColor.gray3
        val enlighteningButtonTarget = MyColor.white4
        val enlighteningTextInitial = MyColor.whiteDark2
        val enlighteningTextTarget = MyColor.whiteDark0
        val popupBackground = MyColor.grayDark3
        val popupText = MyColor.whiteDark3
    }

    object PopupTuto {
        var topPadding = 0.8F
        var startPadding = 0.1F
        var endPadding = 0.1F
        var bottomPadding = 0.05F
        val shadow = 15.dp
    }

    fun create(
        topPadd: Float = 0.8F,
        startPadd: Float = 0.1F,
        endPadding: Float = 0.1F,
        bottomPadd: Float = 0.05F
    ): TutoObj {
        PopupTuto.startPadding = startPadd
        PopupTuto.endPadding = endPadding
        PopupTuto.topPadding = topPadd
        PopupTuto.bottomPadding = bottomPadd

        return this
    }
}
