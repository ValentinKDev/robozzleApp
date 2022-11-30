package com.mobilegame.robozzle.presentation.ui.Screen.Profil

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import backColor
import com.mobilegame.robozzle.analyse.verbalLog
import com.mobilegame.robozzle.domain.model.Screen.Navigation.NavViewModel
import com.mobilegame.robozzle.domain.model.Screen.TabSelectionViewModel
import com.mobilegame.robozzle.domain.model.Screen.Tabs
import com.mobilegame.robozzle.presentation.res.MyColor
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.grayDark6
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.whiteDark4
import com.mobilegame.robozzle.presentation.ui.Navigation.Navigator
import com.mobilegame.robozzle.presentation.ui.Screen.Profil.register_login.LoginTab
import com.mobilegame.robozzle.presentation.ui.Screen.Profil.register_login.RegisterTab
import com.mobilegame.robozzle.presentation.ui.Screen.Profil.register_login.header
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import com.mobilegame.robozzle.presentation.ui.utils.CenterText
import com.mobilegame.robozzle.presentation.ui.utils.padding.PaddingComposable

@Composable
fun RegisterLoginScreen(navigator: Navigator, tabVM: TabSelectionViewModel = TabSelectionViewModel()) {
    val tabSelected: Tabs by tabVM.selected.collectAsState()

    LaunchedEffect(key1 = true) { verbalLog("Launch", "RegisterLoginScreen") }

    BackHandler {
        NavViewModel(navigator).navigateToMainMenu(fromScreen = Screens.RegisterLogin.route)
    }

    Column {
        Box(Modifier.weight(1F)) {
            header(tabVM)
//            RegisterLoginTabsHead(tab)
        }
        Box( Modifier .weight(17F)) {
            PaddingComposable(
                topPaddingRatio = 0.1F,
                startPaddingRatio = 0F,
                bottomPaddingRatio = 0F,
                endPaddingRatio = 0F
            ){
                when (tabSelected) {
                    Tabs.Register -> RegisterTab(navigator = navigator)
                    Tabs.Login -> LoginTab(navigator = navigator)
                }
            }
        }
    }
}
//todo: can't register as none cuz its a defaut variable/ or change the variable