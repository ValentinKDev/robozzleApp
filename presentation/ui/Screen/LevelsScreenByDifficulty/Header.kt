package com.mobilegame.robozzle.presentation.ui.Screen.LevelsScreenByDifficulty

import androidx.compose.animation.core.animateSize
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.domain.model.Screen.LevelsScreenByDiff.LevelsScreenByDifficultyViewModel
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.grayDark3
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.whiteDark4
import com.mobilegame.robozzle.presentation.ui.Navigation.Navigator
import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.MainScreenWindowsInfos
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposable

@Composable
fun LevelsScreenByDifficultyHeader(navigator: Navigator, levelDifficulty: Int, vm: LevelsScreenByDifficultyViewModel) {

    val context = LocalContext.current
    val density = LocalDensity.current

    val initialHeaderSize = remember{mutableStateOf( MainScreenWindowsInfos().getButtonSizeTarget(Screens.Difficulty1.key, context, density))}
    val finalHeaderSize = remember{mutableStateOf(Size(0F,0F))}

    val animStart = remember {vm.returnToMainMenuState}.collectAsState()

    LaunchedEffect(key1 = true) {
        initialHeaderSize.value = MainScreenWindowsInfos().getButtonSizeTarget(Screens.Difficulty1.key, context, density)
        finalHeaderSize.value = MainScreenWindowsInfos().getButtonSize(Screens.Difficulty1.key, context, density)
    }

    val transition = updateTransition(targetState = true, label = "")
    val animSize by transition.animateSize(
        label = "animSizeExit",
    ) {
        if (animStart.value && vm.listAnimationEnd()) finalHeaderSize.value
        else initialHeaderSize.value
    }

    Column(Modifier.fillMaxWidth()) {
//        todo: Use MainScreenButton ?
        Card(
            modifier = Modifier
                .align(androidx.compose.ui.Alignment.CenterHorizontally)
                .height(animSize.height.dp)
                .width(animSize.width.dp)
                .fillMaxWidth()
                .clickable {
                    vm.startExitAnimationAndPressBack()
                }
            ,
            shape = MaterialTheme.shapes.large,
            elevation = 50.dp,
            backgroundColor = grayDark3,
        ) {
            CenterComposable {
                Text(text = "difficulty $levelDifficulty", color = whiteDark4)
            }
        }
    }
}
