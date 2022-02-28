package com.mobilegame.robozzle.presentation.ui.Screen.donation

import androidx.compose.animation.*
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.model.Screen.donation.DonationScreenViewModel
import com.mobilegame.robozzle.presentation.res.gray5
import com.mobilegame.robozzle.presentation.res.grayDark3
import com.mobilegame.robozzle.presentation.ui.utils.padding.PaddingComposable

@ExperimentalAnimationApi
@Composable
fun FoldableScrollingBar(vm: DonationScreenViewModel) {
    val unfold by remember(vm) {vm.logic.unfold}.collectAsState(false)
    val transition = updateTransition(targetState = unfold, label = "")

    val input by vm.logic.input.collectAsState()
    infoLog("input", "${input}")

    val sizeList by transition.animateDp(
        label = "",
    ) { _unfold ->
        when (_unfold) {
            false -> 0.dp
            true -> 400.dp
        }
    }

    Column( modifier = Modifier
        .height(400.dp)
        .fillMaxWidth()
    ) {
        AnimatedVisibility(
//            AnimatedContent(
            visible = unfold,
            enter = expandVertically(
                initialHeight = { it + (vm.data.listSize!! * 0.5F).toInt() }
                ,
                animationSpec = tween(200)
            )
            ,
            exit = slideOutVertically(
                targetOffsetY = {
                    it + (vm.data.listSize!! * 0.5F).toInt()
                }
                ,
                tween(200)
            ),
        ) {
            if (unfold) {
                LazyColumn(Modifier.background(grayDark3)) {
//                    itemsIndexed(vm.data.text.listNetworkCoin) { _, _element ->
                    itemsIndexed(vm.logic.showList()) { _, _element ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(40.dp)
                                .clickable {
                                    vm.logic.foldUnfold()
                                    vm.logic.setTextSelectedTo(_element)
                                }
                        ) { Text(text = _element) }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(2.dp)
                                .background(Color.Black)
                        ) { }
                    }
                }
            }
        }
    }
}

