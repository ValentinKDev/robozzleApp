package com.mobilegame.robozzle.data.layout.inGame.elements

import android.content.Context
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.Dp
import com.mobilegame.robozzle.data.layout.inGame.layouts.InGameFirstPart
import com.mobilegame.robozzle.data.layout.inGame.layouts.InGameSecondPart
import com.mobilegame.robozzle.data.layout.inGame.layouts.InGameThirdPart
import com.mobilegame.robozzle.utils.Extensions.toDp

data class Trash(
    val context: Context,
    val data: InGameSecondPart,
    val widthRatioTrash: Float = 0.05F,
) {
    val emptyWidthRatio = 1F - widthRatioTrash
    val width = context.resources.displayMetrics.widthPixels
    val heightTrash = data.sizes.functionPartHeight
    val heightTrashDp = data.sizes.functionPartHeightDp
    val widthTrash = width * widthRatioTrash
    val leftTrashEnd = widthTrash.toInt()
    val rightTrashStart = (width - widthTrash).toInt()
    val widthTrashDp = widthTrash.toDp(context.resources.displayMetrics.density)
    val crossSizeDp: Dp = widthTrashDp * 1.3F

    var pTop = Offset(0F,0F)
    var pBottom =  Offset(0F, heightTrash)
    var pTopSide = Offset(widthTrash, 0F + widthTrash)
    var pBottomSide = Offset(widthTrash, heightTrash - widthTrash)

    fun leftContains(offset: Offset): Boolean = offset.x < leftTrashEnd
    fun rightContains(offset: Offset): Boolean = rightTrashStart < offset.x
    fun contains(offset: Offset): Boolean = leftContains(offset) || rightContains(offset)
}
