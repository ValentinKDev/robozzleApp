package com.mobilegame.robozzle.presentation.ui.Screen.Profil

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.model.Screen.utils.RankingIconViewModel
import com.mobilegame.robozzle.domain.model.User.UserInfosScreenViewModel
import com.mobilegame.robozzle.domain.model.Screen.userInfoScreen.UserInfoScreenData
import com.mobilegame.robozzle.presentation.ui.*
import com.mobilegame.robozzle.presentation.ui.Screen.LevelsScreenByDifficulty.LevelsScreenByDifficultyList
import com.mobilegame.robozzle.presentation.ui.Screen.Profil.userInfoScreen.UserInfoScreenFirstPart
import com.mobilegame.robozzle.presentation.ui.Screen.Profil.userInfoScreen.UserInfoScreenSecondPart
import com.mobilegame.robozzle.presentation.ui.Screen.Profil.userInfoScreen.UserInfoScreenThirdPart


@ExperimentalAnimationApi
@Composable
fun UserInfoScreen(navigator: Navigator, vm: UserInfosScreenViewModel = viewModel(), screenData: UserInfoScreenData = viewModel(), rankingIconVM: RankingIconViewModel = viewModel()) {
//    val listVisible by remember(vm){ vm.logic.doubleListVisible}.collectAsState()

    val ctxt = LocalContext.current
    val dens = LocalDensity.current

    LaunchedEffect(key1 = "Launch LevelsScreenByDifficulty") {
        vm.logic.setVisibilityAtLaunch()
        vm.data.setLevelsListsAtLaunch()
    }

    infoLog("Launch", "UserInfoScreen()")

    Column() {
        Box( modifier = Modifier
            .fillMaxWidth()
            .weight(vm.dimension.firstPartScreenWeight)
        ) {
            UserInfoScreenFirstPart(
                vm = vm,
                screenData = screenData,
                navigator = navigator
            )
        }
        Box( modifier = Modifier
            .fillMaxWidth()
            .weight(vm.dimension.secondPartScreenWeight)
        ) {
            UserInfoScreenSecondPart(vm = vm)
        }
        Column( Modifier
            .weight(vm.dimension.thirdPartScreenWeight)
            .fillMaxWidth()
        ) {
//            AnimatedVisibility(
//                visible = listVisible,
//                enter = slideInVertically(),
//                exit = slideOutVertically(animationSpec = tween(300)) + fadeOut(
//                    animationSpec = tween(
//                        300
//                    )
//                )
//            ) {
                UserInfoScreenThirdPart(
                    vm = vm,
                    navigator = navigator
                )
//            }
        }
    }
}