package com.mobilegame.robozzle.presentation.ui.Screen.LevelsScreenByDifficulty

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.domain.model.Screen.LevelsScreenByDiff.LevelsScreenByDifficultyViewModel
import com.mobilegame.robozzle.presentation.res.MyColor

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ListProgressBar(vm: LevelsScreenByDifficultyViewModel, scrollState: LazyListState) {
    val visibleProgressBar by remember { vm.visibleProgressBar }.collectAsState()
    val mProgress by animateFloatAsState(targetValue = scrollState.firstVisibleItemIndex.toFloat())
    val mProgress2 by animateFloatAsState(targetValue = vm.levelsNumber - scrollState.firstVisibleItemIndex.toFloat())

    when (scrollState.isScrollInProgress) {
        true -> {
            vm.setVisibleProgressBarAs(true)
            vm.updateProgress(scrollState)
        }
        false -> { vm.setVisibleProgressBarAs(false) }
    }
    AnimatedVisibility(
        visibleState = visibleProgressBar,
        enter = fadeIn(0F),
        exit = fadeOut(0F , tween(durationMillis = 500, delayMillis = 400))
    ) {
        Box( Modifier
            .fillMaxSize()
            .padding(top = 6.dp, bottom = 6.dp)
            ,
            contentAlignment = Alignment.CenterEnd
        ) {
            Column(
                Modifier
                    .width(6.dp)
                    .fillMaxHeight()
            ) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(mProgress/(mProgress + mProgress2))
                ) { }
                Box(
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    Column( modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .height(35.dp)
                        .fillMaxWidth()
                        .background(MyColor.white5)
                    ) { }
                }
            }
        }
    }
}