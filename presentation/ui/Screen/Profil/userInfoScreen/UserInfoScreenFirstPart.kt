package com.mobilegame.robozzle.presentation.ui.Screen.Profil.userInfoScreen

import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mobilegame.robozzle.domain.model.Screen.NavViewModel
import com.mobilegame.robozzle.domain.model.User.UserInfosScreenViewModel
import com.mobilegame.robozzle.domain.model.window.UserInfoScreenData
import com.mobilegame.robozzle.presentation.res.whiteDark4
import com.mobilegame.robozzle.presentation.ui.Navigator
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposable
import com.mobilegame.robozzle.presentation.ui.utils.padding.PaddingComposable

@Composable
fun UserInfoScreenFirstPart(screenVM: UserInfosScreenViewModel, w: UserInfoScreenData, navigator: Navigator) {
    PaddingComposable(
        topPaddingRatio = 1f/3f,
        bottomPaddingRatio = 1f/3f,
        startPaddingRatio = 0.8f,
        endPaddingRatio = 0.1f,
    ) {
        Button(
            onClick = {
                screenVM.logingOut()
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
            backgroundColor = w.cardNameColor,
            elevation = w.cardNameElevation,
            modifier = Modifier
            ,
            content = { CenterComposable { Text(text = "${screenVM.name}", color = whiteDark4) } }
        )
    }
}