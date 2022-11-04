package com.mobilegame.robozzle.presentation.ui.Screen.donation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.domain.model.Screen.donation.DonationScreenViewModel
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.grayDark4
import com.mobilegame.robozzle.presentation.ui.Screen.donation.secondPart.NetworkAndNameInput
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposableVertically
import com.mobilegame.robozzle.presentation.ui.utils.padding.PaddingComposable

@Composable
fun DonationScreenSecondPart(vm: DonationScreenViewModel) {
    val ctxt = LocalContext.current

    Column( modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
    ) {
        PaddingComposable(
            topPaddingRatio = 0.08F,
            bottomPaddingRatio = vm.data.ratios.firstPartBottomPadding - vm.data.ratios.introVerticalPadding,
            startPaddingRatio = vm.data.ratios.selectionBarAndIconColumnHorizontalPadd,
            endPaddingRatio = vm.data.ratios.selectionBarAndIconColumnHorizontalPadd,
        ) {
            Column( modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
            ) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .weight(vm.data.ratios.foldableListHeightWeight)
                        .onGloballyPositioned { _layoutCoordinates ->
                            vm.data.setListSize(
                                _layoutCoordinates,
                                ctxt
                            )
                        }
                ) {
                    FoldableScrollingBar(vm = vm)
                }
                Box(
                    Modifier
                        .fillMaxWidth()
                        .weight(vm.data.ratios.selectedAddressBarHeightWeight)
                ) {
                    SelectedAddressAndCopyIconBar(vm = vm)
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SelectedAddressAndCopyIconBar(vm: DonationScreenViewModel) {
    val ctxt = LocalContext.current

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 25.dp,
        shape = RoundedCornerShape(corner = CornerSize(5.dp)),
        backgroundColor = grayDark4
    ) {
        Row( Modifier
            .clickable {
                vm.logic.foldUnfold()
            }
        ) {
            CenterComposableVertically {
                NetworkAndNameInput(vm)
            }
//            Row(
//                Modifier
//                    .fillMaxHeight()
//                    .weight(vm.data.ratios.selectedAddressStartPadd), horizontalArrangement = Arrangement.Start) {}
//            Row( modifier = Modifier
//                .fillMaxHeight()
//                .weight(vm.data.ratios.selectedAddressWeight)
//            ) {
//                CenterComposableVertically {
//                    NetworkAndNameInput(vm)
//                }
//            }
//            Row( modifier = Modifier
//                .fillMaxHeight()
//                .weight(vm.data.ratios.IconWeight)
//            ,
//                horizontalArrangement = Arrangement.End
//            ) {
//                PaddingComposable(
//                    verticalPadding = 0.1F,
//                    horizontalPadding = 0.1F,
//                ) {
//                    IconButton(modifier = Modifier
//                        .height(40.dp)
//                        .fillMaxWidth(),
//                        onClick = {
//                            vm.logic.clipAndToast(ctxt)
//                        },
//                        content = {
//                            Icon(
//                                imageVector = Icons.Default.ContentCopy,
//                                contentDescription = "copy icone",
//                                tint = vm.data.color.iconColor
//                            )
//                        }
//                    )
//                }
//            }
        }
    }
}