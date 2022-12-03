package com.mobilegame.robozzle.presentation.ui.Screen.donation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.model.Screen.donation.DonationScreenViewModel
import com.mobilegame.robozzle.presentation.res.MyColor
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.grayDark4
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.red4
import com.mobilegame.robozzle.presentation.ui.Screen.donation.secondPart.NetworkAndNameInput
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposableVertically
import com.mobilegame.robozzle.presentation.ui.utils.padding.PaddingComposable

@Composable
fun DonationScreenSecondPart(vm: DonationScreenViewModel) {
    PaddingComposable(
            topPaddingRatio = vm.ui.selector.ratios.topPadding,
            startPaddingRatio = vm.ui.selector.ratios.sidePadding,
            endPaddingRatio = vm.ui.selector.ratios.sidePadding,
            bottomPaddingRatio = vm.ui.selector.ratios.bottomPadding,
//            enableColor = true
    ) {
        Column {
            Box(
                Modifier
                    .weight(vm.ui.selector.list.ratios.heightWeight)
                    .onGloballyPositioned { _layoutCoordinates ->
                        vm.ui.setListSize(_layoutCoordinates)
                        infoLog(
                            "DonationScreenLayout::setListSize",
                            "list layout Size ${vm.ui.selector.list.sizes.bottomHeightCoordinates}"
                        )
                    }
            ) {
                FoldableScrollingBar(vm = vm)
            }
            Box( Modifier.weight(vm.data.ratios.selectedAddressBarHeightWeight) ) {
                foldedBar(vm)
            }
        }
    }
    Row(Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(vm.ui.selector.keyboardSpace.heightDp)
                .background(MyColor.applicationBackground)
                .align(Alignment.Bottom)
            ,
        ) { }
    }
}