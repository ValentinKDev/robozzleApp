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
    Column {
        Box(Modifier.weight(vm.ui.header.ratios.heightWeight))
        Box(
            Modifier
                .padding( start = vm.ui.selector.padding.sidePadding,
                    end = vm.ui.selector.padding.sidePadding, )
                .weight(vm.ui.presentation.ratios.heightWeight)
                .onGloballyPositioned { _layoutCoordinates ->
                    vm.ui.setListSize(_layoutCoordinates)
                }
        ) {
            BottomComposable {
                FoldableScrollingList(vm = vm)
            }
        }
        Box(Modifier.weight(vm.ui.selector.ratios.heightWeight))
        Box(Modifier.weight(vm.ui.keyboardSpace.ratios.heightWeight))
    }

    BottomComposable {
        Box(
            Modifier
                .fillMaxWidth()
                .height(vm.ui.keyboardSpace.sizes.maskDp)
                .background(MyColor.applicationBackground)
        )
    }

    Column {
        Box(Modifier.weight(vm.ui.header.ratios.heightWeight))
        Box(Modifier.weight(vm.ui.presentation.ratios.heightWeight))
        Row(
            Modifier
                .weight(vm.ui.selector.ratios.heightWeight)
                .padding( start = vm.ui.selector.padding.sidePadding,
                    end = vm.ui.selector.padding.sidePadding, )
        ) {
            centralBar(vm)
        }
        Box(Modifier.weight(vm.ui.keyboardSpace.ratios.heightWeight))
    }
}