package com.mobilegame.robozzle.presentation.ui.Screen.donation

import androidx.compose.animation.*
import androidx.compose.animation.core.animateDp
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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobilegame.robozzle.domain.model.Screen.donation.DonationScreenRatios
import com.mobilegame.robozzle.domain.model.Screen.donation.DonationScreenViewModel
import com.mobilegame.robozzle.presentation.res.gray5
import com.mobilegame.robozzle.presentation.ui.utils.extensions.noRippleClickable
import com.mobilegame.robozzle.presentation.ui.utils.padding.PaddingComposable

@ExperimentalAnimationApi
@Composable
fun DonationScreen(vm: DonationScreenViewModel = viewModel()) {
    Box( Modifier.fillMaxSize()
        .noRippleClickable { vm.logic.fold() }
    ) {
        Column(Modifier.fillMaxSize()) {
            PaddingComposable(
                bottomPaddingRatio = vm.data.ratios.firstPartBottomPadding
            ) {
                Box( modifier = Modifier
                    .fillMaxWidth()
//                    .weight(vm.data.ratios.fistPartWeigth) )
                )
                {
                    DonationScreenFirstPart(vm)
                }
            }
        }
        Column(Modifier.fillMaxSize()) {
                Box( modifier = Modifier.fillMaxWidth() )
                {
                    DonationScreenSecondPart(vm)
                }
        }
    }
}

