package com.mobilegame.robozzle.presentation.ui.Screen.Profil.userInfoScreen

import androidx.compose.animation.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.domain.model.User.UserInfosScreenViewModel
import com.mobilegame.robozzle.presentation.ui.Navigation.Navigator

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UserInfoScreenThirdPart(vm: UserInfosScreenViewModel, navigator: Navigator) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        contentPadding = PaddingValues(
            start = 1.dp,
            top =  1.dp,
            end = 1.dp,
            bottom = 1.dp
        ),
        content = {
            items(vm.logic.levelWinList.size) {_index ->
                DisplayWinOverView(
                    levelWin = vm.logic.levelWinList[_index],
                    navigator = navigator,
                    levelMap = vm.logic.maps[_index],
                    vm = vm
                )
            }
        }
    )
}

