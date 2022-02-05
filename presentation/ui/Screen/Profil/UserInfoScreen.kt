package com.mobilegame.robozzle.presentation.ui.Screen.Profil

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.Player.LevelWin
import com.mobilegame.robozzle.domain.model.Screen.NavViewModel
import com.mobilegame.robozzle.domain.model.User.UserInfosScreenViewModel
import com.mobilegame.robozzle.domain.model.data.room.LevelWins.LevelWinRoomViewModel
import com.mobilegame.robozzle.domain.model.data.room.level.LevelRoomViewModel
import com.mobilegame.robozzle.domain.model.data.store.UserDataStoreViewModel
import com.mobilegame.robozzle.domain.model.level.Level
import com.mobilegame.robozzle.domain.model.level.LevelOverView
import com.mobilegame.robozzle.presentation.ui.*
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import kotlinx.coroutines.*

@SuppressLint("CoroutineCreationDuringComposition")
@InternalCoroutinesApi
@Composable
fun UserInfoScreen(navigator: Navigator) {
    infoLog("Launch", "UserInfoScreen()")

    val context = LocalContext.current
    val name = UserDataStoreViewModel(context)
    val levelWinList = LevelWinRoomViewModel(context).getAllLevelWins()
    val levelList: List<LevelOverView> = LevelRoomViewModel(context).getLevelOverViewInList(levelWinList)

    Column() {
        Text(text = "UserInfoScreen")
        Spacer(modifier = Modifier.height(50.dp))
        Text(text = "user name ${name}")
    }
    LazyColumn {
        itemsIndexed(levelWinList) { index, level ->
            DisplayWinOverView(levelWinList[index])
        }
    }
}

@Composable
fun DisplayWinOverView(levelWin: LevelWin) {
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .padding(
                bottom = 6.dp,
                top = 6.dp,
                start = 6.dp,
                end = 6.dp,
            )
            .fillMaxWidth()
            .height(100.dp)
            .clickable {
            }
        ,
        elevation = 8.dp,
    ) {
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .clickable {
                }
        ) {
            Box(Modifier.weight(1.0f)) {
                DisplayLevelImage()
            }
            Box(Modifier.weight(1.0f)) {
                Column() {
                    Text("level ${levelWin.levelId}")
                    Text("${levelWin.points}")
                }
            }
        }
    }
}