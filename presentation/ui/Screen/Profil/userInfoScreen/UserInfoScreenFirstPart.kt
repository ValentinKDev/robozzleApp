package com.mobilegame.robozzle.presentation.ui.Screen.Profil.userInfoScreen

import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.mobilegame.robozzle.domain.model.Screen.NavViewModel
import com.mobilegame.robozzle.domain.model.User.UserInfosScreenViewModel
import com.mobilegame.robozzle.domain.model.Screen.userInfo.UserInfoScreenData
import com.mobilegame.robozzle.presentation.ui.Navigator
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposable
import com.mobilegame.robozzle.presentation.ui.utils.padding.PaddingComposable

@Composable
fun UserInfoScreenFirstPart(vm: UserInfosScreenViewModel, screenData: UserInfoScreenData, navigator: Navigator) {
    PaddingComposable(
        topPaddingRatio = 1f/3f,
        bottomPaddingRatio = 1f/3f,
        startPaddingRatio = 0.8f,
        endPaddingRatio = 0.1f,
    ) {
        Button(
            onClick = {
//                vm.logic.logingOut()
                NavViewModel(navigator).navigateTo(Screens.MainMenu)
            }
        ) { Text(text = "Log out") }
    }
    PaddingComposable(
        topPaddingRatio = 0.1f,
        bottomPaddingRatio = 0.1f,
        startPaddingRatio = 0.3f,
        endPaddingRatio = 0.3f,
//                enableColor = true
    ) {
        Card(
            shape = MaterialTheme.shapes.medium,
            backgroundColor = vm.data.cardNameColor,
            elevation = vm.dimension.cardNameElevation,
            content = { CenterComposable { Text(text = "${vm.data.name}", color = vm.data.textColor) } }
        )
    }
}