package com.mobilegame.robozzle.presentation.ui.InGameCompose

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.Extensions.gradientBackground
import com.mobilegame.robozzle.domain.InGame.GameDataViewModel
import com.mobilegame.robozzle.domain.RobuzzleLevel.RobuzzleLevel
import com.mobilegame.robozzle.presentation.ui.ColorsList
import com.mobilegame.robozzle.presentation.ui.RecognizeColor

@Composable
fun DisplayGameScreen(level: RobuzzleLevel, gameDataViewModel: GameDataViewModel, screenConfig: ScreenConfig) {
    Column(modifier = Modifier
        .fillMaxSize()
    ) {
        Row(){
            MapLayout(level, gameDataViewModel, screenConfig)
        }
        Row(modifier = Modifier
//            .weight(screenConfig.weight_second_part)
        ) {
            SecondScreenPart(level, gameDataViewModel, screenConfig,)
        }
    }
}

//todo : highlight in background only the instruction you selected ?
@Composable
fun DisplayInstuctionMenu(level: RobuzzleLevel, gameDataViewModel: GameDataViewModel, screenConfig: ScreenConfig ) {



    Column(
        modifier = Modifier
//            .fillMaxSize()
            .height(300.dp)
//            .width(800.dp)
        ,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        var instructionColor: Color
        level.instructionsMenu.forEachIndexed { instructionLine, instructions ->
            instructionColor = RecognizeColor(instructions.colors, false)
            Row(
                modifier = Modifier
//                    .fillMaxWidth()
                    .width(800.dp)
                ,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                instructions.instructions.toList().forEachIndexed { index, c ->
                    //todo: InstructionMenuCase()
                    Box(
                        modifier = Modifier
//                            .background(instructionColor)
                            .gradientBackground(ColorsList(instructions.colors, false), 175f)
                            .clickable {
                                // enlever level.selected des parametre ???
                                level.ReplaceInstructionCaseColor(
                                    level.selected,
                                    instructions.colors.first()
                                )
                                level.ReplaceInstructionInCase(level.selected, c)
                                level.breadcrumb.CreateNewBeadcrumb(0, level.funInstructionsList)
//                                level.guideline.CreateNewGuideline(0, level.funInstructionsList)
//                                Log.i("ON CLICK actionList", "${level.guideline.actionList}")
                                Log.i("ON CLICK actionList", "${level.breadcrumb.actionList}")
                                gameDataViewModel.ChangeInstructionMenuState()
                            }
//                            .size(screenConfig.instructionMenuCase.dp)
                            .size(screenConfig.instructionMenuCaseWidth.dp)
                            .border(width = screenConfig.instructionMenuCaseBorder.dp, color = Color.Black)
                    ) {
                        InstructionIconsMenu(c, gameDataViewModel, screenConfig)
                        /*

                        Box(modifier = Modifier.fillMaxSize()) {
                            Icon(
                                imageVector = when (c) {
                                    'r' -> Icons.Outlined.Redo
                                    'l' -> Icons.Outlined.Undo
                                    'U' -> Icons.Outlined.ArrowUpward
                                    '0' -> Icons.Outlined.ExposureZero
                                    '1' -> Icons.Outlined.LooksOne
                                    '2' -> Icons.Outlined.LooksTwo
                                    '3' -> Icons.Outlined.Looks3
                                    '4' -> Icons.Outlined.Looks4
                                    '5' -> Icons.Outlined.Looks5
                                    '6' -> Icons.Outlined.Looks6
                                    else -> Icons.Default.Home
                                },
                                contentDescription = "instruction",
                                modifier = Modifier
                                    .size(20.dp)
                                    .align(Alignment.Center)
                            )
                        }
//                        Text(text = "${c}")
                         */
                    }
                }
            }
        }
    }
}
