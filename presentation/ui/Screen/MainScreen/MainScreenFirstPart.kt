package com.mobilegame.robozzle.presentation.ui.Screen.MainScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import backColor
import com.mobilegame.robozzle.domain.model.Screen.mainScreen.MainScreenViewModel
import com.mobilegame.robozzle.domain.model.data.store.UserDataStoreViewModel
import com.mobilegame.robozzle.presentation.res.red0
import com.mobilegame.robozzle.presentation.res.redDark3
import com.mobilegame.robozzle.presentation.res.whiteDark2
import com.mobilegame.robozzle.presentation.ui.Navigator
import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.button.MainScreenButton
import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.button.MainScreenButtonStyle
import com.mobilegame.robozzle.presentation.ui.button.MainMenuButton

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainScreenFirstPart(
    navigator: Navigator,
    fromButton: Int = MainMenuButton.None.key,
    w: MainScreenWindowsInfos,
    vm: MainScreenViewModel
) {
    val visibleElements by remember(vm) {vm.visibleElements}.collectAsState(false)

        Row( Modifier .fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
        ) {
            Row(
                modifier = Modifier
                    .align(CenterVertically)
//                    .backColor(redDark3)
                ,
            ) {
                AnimatedVisibility(visible = visibleElements) {
                    Text(
                        text = vm.getName(),
                        color = whiteDark2
                    )
                }
            }
            Box( modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(vertical = 10.dp, horizontal = 10.dp)
            ) {
                MainScreenButton( navigator,
//                    UserDataStoreViewModel(LocalContext.current).getName()?.let {
//                        MainScreenButtonStyle.UserInfos.type
//                    } ?: MainScreenButtonStyle.RegisterLogin.type,
                    vm.getNavInfoToUser(),
                    fromButton,
                    vm,
                    w
                )
            }
        }
//    }
}
