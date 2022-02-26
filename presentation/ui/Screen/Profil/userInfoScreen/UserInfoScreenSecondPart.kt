package com.mobilegame.robozzle.presentation.ui.Screen.Profil.userInfoScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.Player.LevelWin
import com.mobilegame.robozzle.domain.model.Screen.NavViewModel
import com.mobilegame.robozzle.domain.model.Screen.utils.RankingIconViewModel
import com.mobilegame.robozzle.domain.model.User.UserInfosScreenViewModel
import com.mobilegame.robozzle.presentation.res.grayDark3
import com.mobilegame.robozzle.presentation.res.whiteDark4
import com.mobilegame.robozzle.presentation.ui.Navigator
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import com.mobilegame.robozzle.presentation.ui.elements.MapView
import com.mobilegame.robozzle.presentation.ui.elements.RankingIconBouncing

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
            elevation = vm.dimension.filterListButtonElevation,
            backgroundColor = vm.data.buttonListFilterColor
        ) {
            Text(text = "${vm.data.levelList.value.size} level resolved", color = vm.data.textColor)
        }
    }
}

