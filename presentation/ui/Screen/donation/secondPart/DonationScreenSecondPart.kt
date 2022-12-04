package com.mobilegame.robozzle.presentation.ui.Screen.donation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import com.mobilegame.robozzle.domain.model.Screen.donation.DonationScreenViewModel
import com.mobilegame.robozzle.presentation.res.MyColor
import com.mobilegame.robozzle.presentation.ui.utils.BottomComposable
import com.mobilegame.robozzle.presentation.ui.utils.padding.PaddingComposable

@Composable
fun DonationScreenSecondPart(vm: DonationScreenViewModel) {
    PaddingComposable(
        topPaddingRatio = vm.ui.header.ratios.heightPercentage,
        startPaddingRatio = vm.ui.list.ratios.sidePadding,
        endPaddingRatio = vm.ui.list.ratios.sidePadding,
        bottomPaddingRatio = vm.ui.keyboardSpace.ratios.heightPercentage,
//        enableColor = true
    ) {
        Column {
            Box(
                Modifier
                    .weight(vm.ui.presentation.ratios.heightWeight)
                    .onGloballyPositioned { _layoutCoordinates ->
                        vm.ui.setListSize(_layoutCoordinates)
                    }
            ) {
                BottomComposable {
                    FoldableScrollingList(vm = vm)
                }
            }
            Row( Modifier.weight(vm.ui.selector.ratios.heightWeight) ) {
                centralBar(vm)
            }
        }
    }
    BottomComposable {
        Box(
            Modifier
                .fillMaxWidth()
                .height(vm.ui.keyboardSpace.sizes.heightDp)
                .background(MyColor.applicationBackground)
        )
    }
}