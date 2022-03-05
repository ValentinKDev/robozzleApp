package com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.domain.RobuzzleLevel.FunctionInstructions
import com.mobilegame.robozzle.presentation.ui.utils.extensions.gradientBackground
import com.mobilegame.robozzle.domain.model.Screen.InGame.GameDataViewModel
import com.mobilegame.robozzle.domain.RobuzzleLevel.RobuzzleLevel
import com.mobilegame.robozzle.presentation.res.ColorsList

//todo : use the navigation to avoid the recall of the function DisplayGameScreen 3 times
//@DelicateCoroutinesApi
@Composable
fun DisplayGameScreen(level: RobuzzleLevel, gameDataViewModel: GameDataViewModel, screenConfig: ScreenConfig) {
    Column(modifier = Modifier
        .fillMaxSize()
    ) {
        Row(Modifier.weight(2F)){
            MapLayout(level, gameDataViewModel, screenConfig)
        }
        Row(modifier = Modifier
            .weight(3F)
        ) {
            SecondScreenPart(level, gameDataViewModel, screenConfig,)
        }
    }
}

//todo : highlight in background only the instruction you selected ?
//todo : il manque la touche supprimer l'instruction selectionnée
@Composable
fun DisplayInstuctionMenu(level: RobuzzleLevel, gameDataViewModel: GameDataViewModel, screenConfig: ScreenConfig ) {

    Column(
        modifier = Modifier
            .height(300.dp)
        ,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        level.instructionsMenu.forEachIndexed { instructionLine, instructions ->
            Row(
                modifier = Modifier
                    .width(800.dp)
                ,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                instructions.instructions.toList().forEachIndexed { index, c ->
                    //todo: InstructionMenuCase()
                    Box(
                        modifier = Modifier
                            .gradientBackground(ColorsList(instructions.colors, false), 175f)
                            .clickable {
//                                level.replaceCaseColor(
//                                    level.selected,
//                                    instructions.colors.first().toString()
//                                )
//                                level.replaceCaseInstruction(level.selected, c.toString())
//                                level.breadcrumb.CreateNewBeadcrumb(0, level.funInstructionsList)
                                level.replaceInstruction(
                                    level.selected,
                                    FunctionInstructions(instructions = c.toString(), colors = instructions.colors)
                                )
                                Log.i("ON CLICK actionList", level.breadcrumb.actionList)
                                gameDataViewModel.ChangeInstructionMenuState()
                            }
//                            .size(screenConfig.instructionMenuCase.dp)
                            .size(screenConfig.instructionMenuCaseWidth.dp)
                            .border(width = screenConfig.instructionMenuCaseBorder.dp, color = Color.Black)
                    ) {
                        InstructionIconsMenu(c, gameDataViewModel, screenConfig)
                    }
                }
            }
        }
    }
}
