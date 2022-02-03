package com.mobilegame.robozzle.presentation.ui

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.model.data.store.UserDataStoreViewModel
import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.MainScreenButton
import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.MainScreenButtonStyle
import com.mobilegame.robozzle.presentation.ui.spacer.VerticalSpace
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
@Composable
fun MainScreen(navController: NavController) {
    infoLog("MainScreen", "launch")
    Column(
        modifier = Modifier
            .fillMaxSize()
        ,
    ){
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.2F)
                .padding(vertical = 10.dp, horizontal = 10.dp)
        ) {
            Box( modifier = Modifier .align(CenterVertically)
            ) {
                UserDataStoreViewModel(LocalContext.current).getName()?.let {
                    Text(text = "player : $it")
                }?: Text(text = "Not registered")
            }

            Box( modifier = Modifier .align(CenterVertically),
            ) { MainScreenButton(navController, MainScreenButtonStyle.Profil.type) }
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .weight(0.6F)
                .fillMaxWidth()
//                .background(Color.DarkGray)
        ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .width(MainScreenButtonStyle.LevelDifficulty1.type.width.dp)
//                    .background(Color.Blue)
                ,
            ) {
                MainScreenButton(navController, info = MainScreenButtonStyle.LevelDifficulty1.type)
                VerticalSpace(10)
                MainScreenButton(navController, info = MainScreenButtonStyle.LevelDifficulty2.type)
                VerticalSpace(10)
                MainScreenButton(navController, info = MainScreenButtonStyle.LevelDifficulty3.type)
                VerticalSpace(10)
                MainScreenButton(navController, info = MainScreenButtonStyle.LevelDifficulty4.type)
                VerticalSpace(10)
                MainScreenButton(navController, info = MainScreenButtonStyle.LevelDifficulty5.type)
                VerticalSpace(10)
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 10.dp,
                    vertical = 15.dp
                )
                .weight(0.2F)
//                .background(Color.Yellow)
        ) {
            Row(horizontalArrangement = Arrangement.Start, modifier = Modifier.weight(0.3F).align(CenterVertically)) {
                MainScreenButton(navController, info = MainScreenButtonStyle.Creator.type)
            }
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.align(CenterVertically)) {
                MainScreenButton(navController, info = MainScreenButtonStyle.Donation.type)
            }
            Row(horizontalArrangement = Arrangement.End, modifier = Modifier.weight(0.3F).align(CenterVertically)) {
                MainScreenButton(navController, info = MainScreenButtonStyle.Config.type)
            }
        }
    }
}


