package com.mobilegame.robozzle.presentation.ui.Screen.LevelsScreenByDifficulty

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.domain.model.Screen.LevelsScreenByDifficultyViewModel
import com.mobilegame.robozzle.domain.model.Screen.NavViewModel
import com.mobilegame.robozzle.presentation.res.grayDark3
import com.mobilegame.robozzle.presentation.res.whiteDark4
import com.mobilegame.robozzle.presentation.ui.Navigator
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposable

@Composable
fun LevelsScreenByDifficultyHeader(navigator: Navigator, levelDifficulty: Int, vm: LevelsScreenByDifficultyViewModel) {
    Card(
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth()
            .clickable {
                vm.setVisiblityAtHeadClick()
                NavViewModel(navigator).navigateTo(
                    Screens.MainMenu,
                    argStr = levelDifficulty.toString()
                )
            }
        ,
        shape = MaterialTheme.shapes.large,
        elevation = 50.dp,
//        backgroundColor = Color.Gray
        backgroundColor = grayDark3
    ) {
//        CenterText(text = "difficulty $levelDifficulty", color = whiteDark4)
        CenterComposable {
            Text(text = "difficulty $levelDifficulty", color = whiteDark4)
        }
    }
}
