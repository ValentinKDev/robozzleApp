package com.mobilegame.robozzle.presentation.ui.Screen.donation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandVertically
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobilegame.robozzle.domain.model.Screen.donation.DonationScreenViewModel
import com.mobilegame.robozzle.presentation.res.gray5

@ExperimentalAnimationApi
@Composable
fun FoldableScrollingBar(vm: DonationScreenViewModel) {
    val unfold by remember(vm) {vm.logic.unfold}.collectAsState(false)
    val transition = updateTransition(targetState = unfold, label = "")
    val sizeList by transition.animateDp( label = "") { _unfold ->
        when (_unfold) {
            false -> 0.dp
            true -> 400.dp
        }
    }

    Column( modifier = Modifier
        .height(400.dp)
        .fillMaxWidth()
        .background(gray5)
    ) {
        Row( modifier = Modifier
            .height(40.dp)
            .fillMaxWidth()
            .background(Color.Red)
            .clickable { vm.logic.foldUnfold() }
            ,
        ) { Text(text = vm.logic.textSelected.value) }
        AnimatedVisibility(
            visible = unfold,
            enter = expandVertically(),
        ) {
            Box( modifier = androidx.compose.ui.Modifier
                .height(sizeList)
                .width(300.dp)
                .clickable { vm.logic.foldUnfold() }
                .background(Color.DarkGray)
            ) {
                if (unfold) {
                    LazyColumn {
                        itemsIndexed(vm.data.list) { _, _element ->
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
}

