package com.mobilegame.robozzle.presentation.ui.Screen.donation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
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
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.domain.model.Screen.donation.DonationScreenViewModel

@Composable
fun FoldableScrollingBar(vm: DonationScreenViewModel) {
    val unfold by remember(vm) {vm.logic.unfold}.collectAsState(false)
//    val unfold by remember(vm) {vm.logic.unfoldState}.collectAsState()
    val transition = updateTransition(targetState = unfold, label = "")

    Column( modifier = Modifier
        .height(400.dp)
        .fillMaxWidth()
    ) {
        AnimatedVisibility(
            visible = unfold,
            enter = expandVertically(
                initialHeight = { it + vm.ui.selector.list.sizes.animInitialHeight },
                animationSpec = tween(200)
            )
            ,
            exit = shrinkVertically(
                targetHeight = { it + vm.ui.selector.list.sizes.animTargetHeight },
                animationSpec = tween(200),
            )
            ,
        ) {
            LazyColumn(
                Modifier.background(vm.ui.selector.list.colors.background)
            ) {
                itemsIndexed(vm.logic.showList()) { _, _element ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(vm.ui.selector.list.sizes.elementHeightDp)
                            .clickable {
                                vm.logic.foldUnfold()
                                vm.logic.setTextSelectedTo(_element)
                            }
                    ) { Text(text = _element) }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
//                            .height(2.dp)
                            .height(vm.ui.selector.list.sizes.elementDelimiter)
                            .background(Color.Black)
                    ) { }
                }
            }
        }
    }
}

