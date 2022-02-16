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
import com.mobilegame.robozzle.domain.model.Screen.DonationScreenViewModel
import com.mobilegame.robozzle.presentation.res.gray5

@ExperimentalAnimationApi
@Composable
fun DonationScreen() {
    Column( Modifier.fillMaxSize() )
    {
        Box( modifier = Modifier.fillMaxWidth().weight(3F) )
        {
            DonationScreenFirstPart()
        }
        Box( modifier = Modifier.fillMaxWidth().weight(5F) )
        {
            DonationScreenSecondPart()
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun FoldableScrollingBar(screenVM: DonationScreenViewModel) {
    val unfold by remember(screenVM) {screenVM.unfold}.collectAsState(false)
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
            .clickable { screenVM.foldUnfold() }
            ,
        ) { Text(text = screenVM.textSelected.value) }
        AnimatedVisibility(
            visible = unfold,
            enter = expandVertically(),
        ) {
            Box( modifier = Modifier
                .height(sizeList)
                .width(300.dp)
                .clickable { screenVM.foldUnfold() }
                .background(Color.DarkGray)
            ) {
                if (unfold) {
                    LazyColumn {
                        itemsIndexed(screenVM.list) { _, _element ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(40.dp)
                                    .clickable {
                                        screenVM.foldUnfold()
                                        screenVM.setTextSelectedTo(_element)
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

@ExperimentalAnimationApi
@Composable
fun DonationScreenSecondPart(screenVM: DonationScreenViewModel = viewModel()) {
    val ctxt = LocalContext.current

    Column( modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .background(Color.Gray)
        .padding(top = 15.dp)
        ,
    ) {
        Column( modifier = Modifier
            .fillMaxHeight()
            .width(300.dp)
            .align(CenterHorizontally)
        ) {
            Row( modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
            ) {
                /** Column to place scrolling bar */
                Column( modifier = Modifier
                    .fillMaxHeight()
                    .weight(8F)
                ) {
                    FoldableScrollingBar(screenVM = screenVM)
                }
                /** Column to place the copy button */
                Column( modifier = Modifier
                    .fillMaxHeight()
                    .weight(1F)
                ) {
                    IconButton(
                        modifier = Modifier
                            .height(40.dp)
                            .fillMaxWidth()
                        ,
                        onClick = {
                            screenVM.clipAndToast(ctxt)
                        },
                    ) {
                        Icon(imageVector = Icons.Default.ContentCopy, contentDescription = "copy icone", tint = Color.Black)
                    }
                }
            }
        }
    }
}

@Composable
fun DonationScreenFirstPart() {
    Row(
        Modifier
            .fillMaxWidth()
    ) {
        Column( Modifier
            .fillMaxWidth()
        ) {
            Box(Modifier.align(CenterHorizontally)) {
                Text( text = "donation screen")
            }
            Box(
                Modifier.padding(
                    top = 20.dp,
                    bottom = 10.dp,
                    start = 5.dp,
                    end = 5.dp,
                )
            ) {
                Text (DonationScreenViewModel().loremIpsum)
            }
        }
    }
}