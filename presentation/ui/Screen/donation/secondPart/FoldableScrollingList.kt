package com.mobilegame.robozzle.presentation.ui.Screen.donation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.mobilegame.robozzle.domain.model.Screen.donation.DonationScreenViewModel

@Composable
fun FoldableScrollingList(vm: DonationScreenViewModel) {
    val unfold by remember(vm) {vm.logic.unfold}.collectAsState()
    val list by remember { vm.logic.list }.collectAsState()

    Column( modifier = Modifier
//        .height(400.dp)
        .fillMaxWidth()
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
                Modifier.background(vm.ui.list.colors.background)
            ) {
                itemsIndexed(list) { _, _element ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(vm.ui.list.sizes.elementHeightDp)
                            .clickable {
                                vm.logic.handleItemClick(_element)
                            }
                    ) { Text(text = _element) }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
//                            .height(2.dp)
                            .height(vm.ui.list.sizes.elementDelimiter)
                            .background(Color.Black)
                    ) { }
                }
            }
        }
    }
}

