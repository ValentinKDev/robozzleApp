package com.mobilegame.robozzle.presentation.ui.Screen.LevelsScreenByDifficulty

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.domain.model.Screen.NavViewModel
import com.mobilegame.robozzle.presentation.ui.Navigator
import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.ButtonId
import com.mobilegame.robozzle.presentation.ui.Screen.Screens

@Composable
fun LevelsScreenByDifficultyHeader(navigator: Navigator, levelDifficulty: Int) {
    Card(
        modifier = Modifier

            .height(100.dp)
            .fillMaxWidth()
            .clickable {
                NavViewModel(navigator).navigateTo(
                    Screens.MainMenu,
                    argStr = levelDifficulty.toString()
                )
            }
        ,
        shape = MaterialTheme.shapes.large,
        elevation = 50.dp,
        backgroundColor = Color.Gray
    ) {
        Column( modifier = Modifier.fillMaxSize()
        ) {
            Row( modifier = Modifier.fillMaxHeight().align(CenterHorizontally)
            ) {
                Box( modifier = Modifier.align(CenterVertically)
                ) {
//                    when (levelDifficulty) {
//                        ButtonId.LevelDiff1.key -> Text("difficulty 1")
//                        ButtonId.LevelDiff2.key -> Text("difficulty 2")
//                        ButtonId.LevelDiff3.key -> Text("difficulty 3")
//                        ButtonId.LevelDiff4.key -> Text("difficulty 4")
//                        ButtonId.LevelDiff5.key -> Text("difficulty 5")
//                        else -> Text(text = "butt")
//                    }
                      Text(text = "button $levelDifficulty")
                }
            }
        }
    }
}
