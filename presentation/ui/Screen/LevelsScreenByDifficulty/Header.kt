package com.mobilegame.robozzle.presentation.ui.Screen.LevelsScreenByDifficulty

import androidx.compose.animation.core.animateSize
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.domain.model.Screen.LevelsScreenByDiff.LevelsScreenByDifficultyViewModel
import com.mobilegame.robozzle.domain.model.Screen.Tuto.Tuto.Companion.isLevelsScreenByDifficultyOn
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.grayDark3
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.whiteDark4
import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.MainScreenWindowsInfos
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposable
import com.mobilegame.robozzle.utils.Extensions.toDp

@Composable
fun LevelsScreenByDifficultyHeader(levelDifficulty: Int, vm: LevelsScreenByDifficultyViewModel) {
    val visibleHeaderState by remember {vm.visibleHeaderState}.collectAsState()

    val context = LocalContext.current
    val densityF = context.resources.displayMetrics.density
    val density = LocalDensity.current

    val initialHeaderSize = remember{mutableStateOf( MainScreenWindowsInfos().getButtonSizeTarget(Screens.Difficulty1.key, context, density))}
    val exitHeaderSize = remember{mutableStateOf(Size(0F,0F))}

    val animStart by remember {vm.returnToMainMenuState}.collectAsState()

    LaunchedEffect(key1 = true) {
        initialHeaderSize.value = MainScreenWindowsInfos().getButtonSizeTarget(Screens.Difficulty1.key, context, density)
//        finalHeaderSize.value = MainScreenWindowsInfos().getButtonSize(Screens.Difficulty1.key, context, density)
        exitHeaderSize.value = MainScreenWindowsInfos().getButtonSize(Screens.Difficulty1.key, context, density)
    }

    errorLog("LevelsScreenByDifficulty::Header", "visible header current ${visibleHeaderState.currentState} target ${visibleHeaderState.targetState}")
    val transition = updateTransition(targetState = visibleHeaderState.targetState, label = "")
    val animSize by transition.animateSize(
        label = "animSizeExit",
    ) {
        if (animStart && vm.listAnimationEnd()) exitHeaderSize.value
        else initialHeaderSize.value
    }

    Column(Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clip(RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp, bottomStart = 5.dp, bottomEnd = 5.dp))
                .height( animSize.height.toDp(densityF) )
                .width( animSize.width.toDp(densityF) )
                .shadow(50.dp)
                .clickable {
                    vm.startExitAnimationAndPressBack()
                }
                .background(grayDark3)
            ,
        ) {
            CenterComposable {
                Text(text = "Difficulty $levelDifficulty", color = whiteDark4)
            }
            if (vm.tutoVM.tuto.value.isLevelsScreenByDifficultyOn(levelDifficulty))
                Box(Modifier.fillMaxSize().background(vm.ui.tuto.colors.filter))
        }
    }
}
