package com.mobilegame.robozzle.presentation.ui.Screen.Profil.register_login

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import backColor
import com.mobilegame.robozzle.domain.model.Screen.TabSelectionViewModel
import com.mobilegame.robozzle.domain.model.Screen.Tabs
import com.mobilegame.robozzle.presentation.res.MyColor
import com.mobilegame.robozzle.presentation.ui.utils.CenterText
import com.mobilegame.robozzle.presentation.ui.utils.padding.PaddingComposable
import noRippleClickable

@Composable
internal fun header(tabVM: TabSelectionViewModel) {
    Row(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box( Modifier.weight(1.0F) ) {
            headerTab(Tabs.Register, tabVM)
        }
        Box( Modifier.weight(1.0F) ) {
            headerTab(Tabs.Login, tabVM)
        }
    }
}

@Composable
private fun headerTab(tab: Tabs, tabVM: TabSelectionViewModel) {
    val tabSelected: Tabs by remember { tabVM.selected }.collectAsState()

    val transition = updateTransition(targetState = tabSelected, label = "tabTransition")
    val invertAnimBorderColors by transition.animateColor(
        label = "invertAnimBorderColors",
        transitionSpec = { if (tab == tabSelected) tween(0) else tween(100) }
    ) { if (tab == tabSelected) Color.Transparent else MyColor.greenSecondVariant }
    val animTextColor by transition.animateColor(
        label = "animTextColor",
        transitionSpec = {
            tween(180)
        }
    ) { if (tab == tabSelected) MyColor.whiteDark4 else MyColor.whiteDark7 }

    Box(
        Modifier
            .clip(
                RoundedCornerShape(
                    topStart = 3.dp,
                    topEnd = 3.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 0.dp,
                )
            )
            .fillMaxSize()
            .background(if (tab == tabSelected) MyColor.greenSecondVariant else MyColor.grayDark8)
            .noRippleClickable { tabVM.setSelecedTo(tab) }
    ) {
        PaddingComposable(
            startPaddingRatio = 0.018F ,
            endPaddingRatio = 0.018F ,
            topPaddingRatio = 0.09F,
            bottomPaddingRatio = 0F,
        ) {
            Box(
                Modifier
                    .clip(
                        RoundedCornerShape(
                            topStart = 3.dp,
                            topEnd = 3.dp,
                            bottomStart = 0.dp,
                            bottomEnd = 0.dp,
                        )
                    )
                    .fillMaxSize()
                    .background(if (tab == tabSelected) MyColor.grayDark6 else Color.Transparent)
            ) {
                CenterText(
                    modifier = Modifier.align(Alignment.Center),
                    color = animTextColor,
                    text = tab.name
                )
            }
        }
        Box(
            Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(3.dp)
                .backColor(invertAnimBorderColors)
        )
    }
}