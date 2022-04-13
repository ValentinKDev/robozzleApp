package com.mobilegame.robozzle.presentation.ui.Screen.Profil.userInfoScreen

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mobilegame.robozzle.domain.model.User.UserInfosScreenViewModel
import com.mobilegame.robozzle.presentation.res.grayDark4
import com.mobilegame.robozzle.presentation.res.whiteDark4

@Composable
fun UserInfoScreenSecondPart(vm: UserInfosScreenViewModel) {
    Row( modifier = Modifier ) {
        Card(
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
//                .padding(
//                    bottom = 6.dp,
//                    top = 6.dp,
//                    start = 6.dp,
//                    end = 6.dp,
//                )
//                .fillMaxWidth()
//                .height(150.dp)
            ,
            elevation = vm.uiData.filterListButtonElevation,
            backgroundColor = grayDark4,
        ) {
            Text(text = "${vm.logic.levelWinList.size} level resolved", color = whiteDark4)
        }
    }
}

