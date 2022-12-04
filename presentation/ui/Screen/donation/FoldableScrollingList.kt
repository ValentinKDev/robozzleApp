package com.mobilegame.robozzle.presentation.ui.Screen.donation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.domain.model.Screen.donation.DonationScreenViewModel
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposable
import com.mobilegame.robozzle.presentation.ui.utils.CenterText
import com.mobilegame.robozzle.presentation.ui.utils.StartComposable

@Composable
fun FoldableScrollingList(vm: DonationScreenViewModel) {
    val unfold by remember(vm) {vm.logic.unfold}.collectAsState()
    val list by remember { vm.logic.list }.collectAsState()

    Column( modifier = Modifier
        .fillMaxWidth()
        .shadow(if (unfold) 20.dp else 0.dp)
    ) {
        AnimatedVisibility(
            visible = unfold,
            enter = expandVertically(
                initialHeight = { it + vm.ui.list.sizes.animInitialHeight },
                animationSpec = tween(200)
            )
            ,
            exit = shrinkVertically(
                targetHeight = { it + vm.ui.list.sizes.animTargetHeight },
                animationSpec = tween(200),
            )
            ,
        ) {
            LazyColumn(
                Modifier
                    .clip(RoundedCornerShape(topEnd = 15.dp, topStart = 15.dp))
                    .background(vm.ui.list.colors.background)
            ) {
                itemsIndexed(list) { _, _element ->
                    CenterComposable {
                        Row(
                            modifier = Modifier
                                .wrapContentSize()
                                .height(vm.ui.list.sizes.elementHeightDp)
                                .clickable {
                                    vm.logic.handleItemClick(_element)
                                }
                        ) {
                            CenterText(
                                text = _element,
                                color = vm.ui.list.colors.elementText,
                            )
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
//                            .height(2.dp)
                            .height(vm.ui.list.sizes.elementDelimiter)
                            .background(vm.ui.list.colors.elementDelimiter)
                    ) { }
                }
            }
        }
    }
}

