package com.mobilegame.robozzle.presentation.ui.Screen.donation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobilegame.robozzle.domain.model.Screen.DonationScreenViewModel
import com.mobilegame.robozzle.presentation.ui.DisplayLevelOverView
import com.mobilegame.robozzle.presentation.ui.Screen.Creator.TestShared
import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.MainScreenButtonStyle
import com.mobilegame.robozzle.presentation.ui.Screen.NavigationDestination
import kotlinx.coroutines.flow.*

@Composable
fun DonationScreen() {

    Column( Modifier
            .fillMaxSize()
    ) {
        //Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(3F)
            ,
        ) {
            DonationScreenFirstPart()
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(5F)
            ,
        ) {
            DonationScreenSecondPart()
        }
    }
}


@Composable
fun FoldableScrollingBar(screenVM: DonationScreenViewModel, foldState: Boolean) {
    Column(
        modifier = Modifier
            .height(if (foldState) 400.dp else 40.dp)
            .fillMaxWidth()
            .background(Color.Red)
            .clickable { screenVM.foldUnfold() }
    ) {
        Row(
            modifier = Modifier
                .height(40.dp)
                .fillMaxWidth()
            ,
        ) { Text(text = screenVM.textSelected.value) }
        if (foldState) {
            Row(
                modifier = Modifier
                    .height(400.dp)
                    .width(300.dp)
                    .clickable { screenVM.foldUnfold() }
                    .background(Color.DarkGray)
            ) {
                if (foldState) {
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

@Composable
fun DonationScreenSecondPart(screenVM: DonationScreenViewModel = viewModel()) {
    val scrollingBar by screenVM.scrolingBar.collectAsState()
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
//                .background(Color.Green)
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
                    FoldableScrollingBar(screenVM = screenVM, foldState = scrollingBar)
                }
                /** Column to place the copy button */
                Column( modifier = Modifier
                    .fillMaxHeight()
                    .weight(1F)
                ) {
//                    Button(
                    IconButton(
                        modifier = Modifier
                            .height(40.dp)
                            .fillMaxWidth()
//                            .background(Color.Yellow)
                        ,
                        onClick = {
                            screenVM.clipAndToast(ctxt)
                        },
//                        enabled =
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