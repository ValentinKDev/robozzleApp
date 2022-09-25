package com.mobilegame.robozzle.presentation.ui.Screen.MainScreen

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import backColor
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.domain.model.Screen.mainScreen.MainScreenViewModel
import com.mobilegame.robozzle.domain.model.data.store.UserDataStoreViewModel
import com.mobilegame.robozzle.presentation.res.red0
import com.mobilegame.robozzle.presentation.res.redDark3
import com.mobilegame.robozzle.presentation.res.whiteDark2
import com.mobilegame.robozzle.presentation.ui.Navigator
import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.button.MainScreenButton
import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.button.MainScreenButtonStyle
import com.mobilegame.robozzle.presentation.ui.button.MainMenuButton
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainScreenFirstPart(
    navigator: Navigator,
    fromButton: Int = MainMenuButton.None.key,
    w: MainScreenWindowsInfos,
    vm: MainScreenViewModel
) {
    val visibleElements by remember(vm) {vm.visibleElements}.collectAsState(false)
//    val researchName by navigator.forceReload.collectAsState(initial = false)

//    LaunchedEffect("MainScreenFirstPart") {
//        navigator.forceReload.onEach {
//            errorLog("researchName", "${navigator.forceReload.toString()}")
//            errorLog("researchName", "${navigator.forceReload.equals(false)}")
//        }.launchIn(this)
//    }
//    if (researchName)
//        errorLog("researchName", "$researchName")

    var name = vm.getName()
    val context = LocalContext.current

//    LaunchedEffect(true) {
//        navigator.forceReload.collect {
//            name = vm.getName()
//        }
//    }
//    navigator.forceReload.collect { it ->
//        if (it)
//    }

    Row( Modifier .fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
    ) {
        Row(
            modifier = Modifier
                .align(CenterVertically)
            ,
        ) {
            AnimatedVisibility(visible = visibleElements) {
                Text(
                    text = name,
                    color = whiteDark2
                )
            }
        }
        Box( modifier = Modifier
            .align(Alignment.CenterVertically)
            .padding(vertical = 10.dp, horizontal = 10.dp)
        ) {
            MainScreenButton( navigator,
                vm.getNavInfoToUser(),
                fromButton,
                vm,
                w
            )
        }
    }
}
