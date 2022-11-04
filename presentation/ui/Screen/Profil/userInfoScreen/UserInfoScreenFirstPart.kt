package com.mobilegame.robozzle.presentation.ui.Screen.Profil.userInfoScreen

import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.mobilegame.robozzle.domain.model.Screen.Navigation.NavViewModel
import com.mobilegame.robozzle.domain.model.User.UserInfosScreenViewModel
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.grayDark5
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.whiteDark4
import com.mobilegame.robozzle.presentation.ui.Navigation.Navigator
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposable
import com.mobilegame.robozzle.presentation.ui.utils.padding.PaddingComposable

@Composable
fun UserInfoScreenFirstPart(vm: UserInfosScreenViewModel, navigator: Navigator) {
    PaddingComposable(
        topPaddingRatio = 1f/3f,
        bottomPaddingRatio = 1f/3f,
        startPaddingRatio = 0.8f,
        endPaddingRatio = 0.1f,
    ) {
        Button(
            onClick = {
//                vm.logic.logingOut()
//                NavViewModel(navigator).navigateTo(Screens.MainMenu)
                NavViewModel(navigator).navigateToMainMenu(fromScreen = Screens.Profil.route)
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
            backgroundColor = grayDark5,
            elevation = vm.uiData.cardNameElevation,
            content = { CenterComposable { Text(text = "${vm.logic.name}", color = whiteDark4) } }
        )
    }
}