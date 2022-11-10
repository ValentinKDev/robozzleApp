package com.mobilegame.robozzle.presentation.ui.Screen.Profil.userInfoScreen

import androidx.compose.animation.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.domain.model.User.UserInfosScreenViewModel
import com.mobilegame.robozzle.presentation.ui.Navigation.Navigator

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UserInfoScreenThirdPart(vm: UserInfosScreenViewModel, navigator: Navigator) {
    val levelWinList by remember { vm.logic.levelWinList }.collectAsState()

    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        contentPadding = PaddingValues(
            start = 1.dp,
            top =  1.dp,
            end = 1.dp,
            bottom = 1.dp
        ),
        content = {
            items(levelWinList.size) {_index ->
                DisplayWinOverView(
                    levelWin = levelWinList[_index],
                    navigator = navigator,
                    levelMap = vm.logic.maps[_index],
                    vm = vm
                )
            }
        }
    )
}

