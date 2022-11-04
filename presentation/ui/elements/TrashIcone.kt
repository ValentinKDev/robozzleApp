package com.mobilegame.robozzle.presentation.ui.elements

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.rotate
import com.mobilegame.robozzle.data.layout.inGame.elements.Trash
import com.mobilegame.robozzle.domain.model.Screen.InGame.GameDataViewModel
import com.mobilegame.robozzle.presentation.res.*
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.red3
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.red4
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.red6
import com.mobilegame.robozzle.utils.Extensions.isOnLeft
import com.mobilegame.robozzle.utils.Extensions.isOnRight

@Composable
fun TrashIcone(
    alignment: Alignment,
    trash: Trash,
    vm: GameDataViewModel
) {
    val rightHighlight by vm.dragAndDropCase.elements.rightTrashHighlight.collectAsState()
    val leftHighlight by vm.dragAndDropCase.elements.leftTrashHighLight.collectAsState()

    val trashColor by animateColorAsState(
        targetValue = if (rightHighlight && alignment.isOnRight() || leftHighlight && alignment.isOnLeft()) red3
        else red6
    )

    Box( modifier = Modifier
        .height(trash.heightTrashDp)
        .width(trash.widthTrashDp)
    ) {
        Icon(
            imageVector = Icons.Outlined.Close,
            tint = red4,
            contentDescription = "cross neon",
            modifier = Modifier
                .align(Alignment.Center)
                .size(trash.crossSizeDp)
        )
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(trash.heightTrashDp)
        ) {
            val trapezoid = Path().apply {
                moveTo(trash.pTop.x, trash.pTop.y)
                lineTo(trash.pTopSide.x, trash.pTopSide.y)
                lineTo(trash.pBottomSide.x, trash.pBottomSide.y)
                lineTo(trash.pBottom.x, trash.pBottom.y)
                lineTo(trash.pTop.x, trash.pTop.y)
            }

//            Color.Black
            rotate(
                pivot = center,
                degrees = if (alignment.isOnLeft()) 0F else 180F,
            ) {
                drawPath(
//                    color = red6,
                    color = trashColor,
                    alpha = 0.25F,
                    path = trapezoid
                )
            }
        }
    }
}