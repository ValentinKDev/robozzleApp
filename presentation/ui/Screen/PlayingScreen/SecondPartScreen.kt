package com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.Extensions.gradientBackground
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.InGame.AnimationLogic
import com.mobilegame.robozzle.domain.model.GameDataViewModel
import com.mobilegame.robozzle.domain.RobuzzleLevel.Position
import com.mobilegame.robozzle.domain.RobuzzleLevel.RobuzzleLevel
import com.mobilegame.robozzle.presentation.res.*
import com.mobilegame.robozzle.presentation.ui.*
import kotlinx.coroutines.*

@DelicateCoroutinesApi
@Composable
fun SecondScreenPart(lvl: RobuzzleLevel, gameDataViewModel: GameDataViewModel, screenConfig: ScreenConfig) {
    Log.i("" , "call SecondScreenPart")
    infoLog("->${lvl.breadcrumb.actionList}", "actionList.IsEmpty() ${lvl.breadcrumb.actionList.isEmpty()}")

    val recomposeSecondPart: Boolean by gameDataViewModel.triggerRecompostion.collectAsState(false)
    if (recomposeSecondPart) gameDataViewModel.triggerRecompostionToFalse()


    Box(
    ) {
        Box(
            modifier = Modifier
//                .background(Color(0xdd080808))
                .fillMaxSize()
        ) {
            Column {
                //todo: simplify this shit
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .height((screenConfig.heightDp / 200).dp)
                    .background(Color.Black)){}
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .height((screenConfig.heightDp / 240).dp)
                    .background(Color.Transparent)){}
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .height((screenConfig.heightDp / 200).dp)
                    .background(Color.Black)){}
                Row(modifier = Modifier .weight(1.0f)) {
//                    if (lvl.guideline.actionList.isNotEmpty()) DisplayActionsRow(lvl, gameDataViewModel, screenConfig)
                    if (lvl.breadcrumb.actionList.isNotEmpty())
                        DisplayActionsRow(lvl, gameDataViewModel, screenConfig)
                }
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .height((screenConfig.heightDp / 200).dp)
                    .background(Color.Black)){}
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .height((screenConfig.heightDp / 240).dp)
                    .background(Color.Transparent)){}
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .height((screenConfig.heightDp / 200).dp)
                    .background(Color.Black)){}
                Row(modifier = Modifier.weight(5.0f, false)) {
                    DisplayFunctionsPart(lvl = lvl, gameDataViewModel, screenConfig)
                }
                Row(modifier = Modifier.weight(1.0f, false)) {
                    GameButtons(lvl, gameDataViewModel, screenConfig)
                }
            }
        }
    }
}

//Todo: replace all this logic based on the breadCrumb in an viewModel or an other entity to unload the screen ??? should it be really on the main thread ?
//Todo: replace fonction calls by little spaces
@Composable
fun DisplayActionsRow(lvl: RobuzzleLevel, gameDataViewModel: GameDataViewModel, screenConfig: ScreenConfig) {
    val currentAction: Int by gameDataViewModel.actionToRead.observeAsState(0)
    var actionDisplayed: Int


//    errorLog("$currentAction", "")

    Row(modifier = Modifier.padding(5.dp)) {
//Todo: delay the disparition of the last action in actionList
//        if (currentAction != lvl.guideline.actionList.length - 1) {
        if (currentAction != lvl.breadcrumb.actionList.length - 1) {
//            errorLog("check", "")
            DisplayActionRowCase(currentAction, lvl, gameDataViewModel, screenConfig)
        }
        var nextAction = 1
//        errorLog("check $nextAction / ${screenConfig.maxActionDisplayedActionRow}", "")
//        errorLog("check $currentAction + $nextAction / ${lvl.breadcrumb.actionList.length}", "")
        while (nextAction < screenConfig.maxActionDisplayedActionRow &&
            nextAction + currentAction < lvl.breadcrumb.actionList.length) {
            actionDisplayed = CalculateActionToDiplay(lvl, currentAction, nextAction)

//            errorLog("check", "")
            DisplayActionRowCase(actionDisplayed, lvl, gameDataViewModel, screenConfig)
            nextAction++
        }
    }
}

//todo : est ce que la barre d'action s'affiche uniquement lors du lancement ? est ce que je peux animer les action qui se stack au fur et a mesure que le joueur selectionne des instructions
@Composable
fun DisplayActionRowCase(action: Int, lvl: RobuzzleLevel, gameDataViewModel: GameDataViewModel, screenConfig: ScreenConfig) {
    Box(
        modifier = Modifier
            .size(screenConfig.instructionActionRowCase.dp)
            .gradientBackground(
                ColorsList(
//                    lvl.funInstructionsList[lvl.guideline.currentInstructionList[action].line].colors[lvl.guideline.currentInstructionList[action].column].toString(),
                    lvl.funInstructionsList[lvl.breadcrumb.currentInstructionList[action].line].colors[lvl.breadcrumb.currentInstructionList[action].column].toString(),
                    gameDataViewModel.displayInstructionsMenu.value == true
                ), 45f
            )
            .border(width = 2.dp, color = Color.Black)
    ) {
//        verbalLog("check", "")
        Box(Modifier.align(Alignment.Center)) {
//            InstructionIconsActionRow(lvl.guideline.actionList[action], gameDataViewModel, screenConfig )
            InstructionIconsActionRow(lvl.breadcrumb.actionList[action], gameDataViewModel, screenConfig )
        }
    }
}

fun CalculateActionToDiplay(lvl: RobuzzleLevel, currentAction: Int, nextActions: Int): Int {
    val actionToDisplay: Int = currentAction + nextActions
//    val actionListSize: Int = lvl.guideline.actionList.length
    val actionListSize: Int = lvl.breadcrumb.actionList.length

    return (if (actionToDisplay >= actionListSize) actionListSize - 1 else actionToDisplay)
}

@Composable
fun DisplayFunctionsPart(lvl: RobuzzleLevel, gameDataViewModel: GameDataViewModel, screenConfig: ScreenConfig) {
    val currentAction: Int by gameDataViewModel.actionToRead.observeAsState(0)

    val animationIsPlaying: Boolean by gameDataViewModel.animationIsPlaying.observeAsState(false)
    val animationIsOnPause: Boolean by gameDataViewModel.animationIsOnPause.observeAsState(false)
    val animationRunningInBackground = animationIsPlaying || animationIsOnPause

    Column() {
        lvl.funInstructionsList.forEachIndexed { functionNumber, function ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.5f, true),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
//                horizontalArrangement = Alignment.Center
            ) {
                Text(text = "f${functionNumber}")
                Row(
//                    horizontalArrangement = Alignment.Center
                ) {
                    function.instructions.toList().forEachIndexed { index, c ->
                        val caseColor = function.colors[index].toString()
//                        var caseColor = function.colors[index]
                        Box(
                            modifier = Modifier
                                .background(Color.Black)
                                .size(40.dp)
                                .padding(4.dp)
                                .clickable {
                                    if (!animationRunningInBackground) {
                                        gameDataViewModel.ChangeInstructionMenuState()
//                                        gameDataViewModel.DisplayInstructionsMenuToTrue()
                                        lvl.SetSelectedFunctionCase(functionNumber, index)
                                    }
                                }
                        ) {
                            val instructionChar = function.instructions[index]
                            if (
//                                lvl.guideline.currentInstructionList.isNotEmpty() &&
                                lvl.breadcrumb.currentInstructionList.isNotEmpty() &&
                                ((currentAction == 0 && functionNumber == 0 && index == 0) ||
//                                        lvl.guideline.currentInstructionList[currentAction].Match( Position(functionNumber, index) ))
                                        lvl.breadcrumb.currentInstructionList[currentAction].Match( Position(functionNumber, index) ))
                                && animationRunningInBackground
//                                && lvl.guideline.currentInstructionList.isNotEmpty()
                            )
                                {
                                Box(
                                    modifier = Modifier
                                        .background(Color.White)
                                        .size(screenConfig.functionBoxSize.dp)
                                        .padding(screenConfig.functionBoxPadd.dp)
                                ) { }
                                Box(
                                    modifier = Modifier
                                        .testTag(TAG_FUNCTION_CASE(functionNumber, index))
                                        .align(Alignment.Center)
//                                        .background(RecognizeColor(caseColor, false))
                                        .gradientBackground(
                                            ColorsList(
                                                caseColor,
                                                gameDataViewModel.displayInstructionsMenu.value == true
                                            ),
                                            175f
//                                            45f
                                        )
                                        .size((screenConfig.functionBoxSize - 12).dp)
                                        .padding((screenConfig.functionBoxPadd).dp)
                                ){
//                                    val text = function.instructions[index].toString()
//                                    if (text.isNotBlank() && text != "."){
                                    if (instructionChar != '.'){
//                                        Text(text = text)
                                        InstructionsIconsFunction(instructionChar, gameDataViewModel, screenConfig)
                                    }
                                }
                            }
                            else {
                                Box() {
                                    FunctionCase(
//                                        RecognizeColor(caseColor, false),
                                        caseColor,
                                        gameDataViewModel,
                                        screenConfig,
//                                        function.instructions[index].toString()
                                    instructionChar
                                    )
                                }
                            }
                        }
                    }

                }
            }
        }
    }
}

@Composable
//fun FunctionCase(color: Color, gameDataViewModel: GameDataViewModel, screenConfig: ScreenConfig, instructionChar: Char) {
fun FunctionCase(color: String, gameDataViewModel: GameDataViewModel, screenConfig: ScreenConfig, instructionChar: Char) {
    Box(
        modifier = Modifier
//            .background(color)
            .gradientBackground(
                ColorsList(
                    color,
                    gameDataViewModel.displayInstructionsMenu.value == true
                ),
//                45f
                175f
            )
            .size(screenConfig.functionBoxSize.dp)
            .padding(screenConfig.functionBoxPadd.dp)
    ){
//        if (text.isNotBlank() && text != "."){
        if (instructionChar != '.'){
//            Text(text = text)
            InstructionsIconsFunction(instructionChar, gameDataViewModel, screenConfig)
        }
    }
}

@DelicateCoroutinesApi
@Composable
fun GameButtons(lvl: RobuzzleLevel, gameDataViewModel: GameDataViewModel, screenConfig: ScreenConfig) {
    val animationIsPlaying: Boolean by gameDataViewModel.animationIsPlaying.observeAsState(false)
    val animationIsOnPause: Boolean by gameDataViewModel.animationIsOnPause.observeAsState(false)
    val animationRunningInBackground = animationIsPlaying || animationIsOnPause

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .testTag(TAG_BUTTON_PLAY)
                    .height(screenConfig.playButtonsHeight.dp)
                    .width(screenConfig.playButtonsWidth.dp)
                    .background(Color.Gray)
                    .clickable {
                        gameDataViewModel.AnimationIsPlayingChangeStatus()
                        if (animationRunningInBackground) {
                            gameDataViewModel.AnimationIsOnPauseChangeStatus()
                        } else if (animationIsPlaying) {
//                        gameDataViewModel.StartAnimation()
                            StartAnimation(lvl, gameDataViewModel)
                        }
                    }
            ) {
                Box(Modifier.align(Alignment.Center)) {
                    PlayPauseIcon(animationIsPlaying)
                }
            }
            Box(
                modifier = Modifier
                    .testTag(TAG_BUTTON_RESET)
                    .background(Color.Gray)
                    .height(screenConfig.playButtonsHeight.dp)
                    .width(screenConfig.playButtonsWidth.dp)
                    .clickable {
                        gameDataViewModel.ResetAnimation(lvl)
                    }
            ) {
                //todo : define clearly steps so you can t have 2 different actions in the row while player still is the same position by clicking quickly on the play/pause button
                Box(Modifier.align(Alignment.Center)) {
                    Icon(imageVector = Icons.Default.Stop, contentDescription = "stop")
                }
            }
            BackButton(screenConfig, gameDataViewModel, animationIsOnPause)

            NextButton(screenConfig, lvl,  gameDataViewModel, animationIsOnPause)
        }
    }
}

@DelicateCoroutinesApi
@Composable
fun NextButton(screenConfig: ScreenConfig, lvl: RobuzzleLevel, gameDataViewModel: GameDataViewModel, animationIsOnPause: Boolean) {
    IconButton(
        onClick = {
            Log.e("NEXT", "clicked")
            if (animationIsOnPause) {
                infoLog("next", "animation is on pause ${animationIsOnPause}")
                infoLog("next", "animation going next change status")
                gameDataViewModel.AnimationGoingNextChangeStatus()
            }
            else {
                infoLog("next", "else")
                gameDataViewModel.AnimationIsOnPauseChangeStatus()
                StartAnimation(lvl, gameDataViewModel)
                gameDataViewModel.AnimationGoingNextChangeStatus()
            }
        },
        modifier = Modifier
            .testTag(TAG_BUTTON_PREV)
            .height(screenConfig.playButtonsHeight.dp)
            .width(screenConfig.playButtonsWidth.dp)
            .background(Color.Gray)
        ,
        //todo : make it possible to start and finish all the game whith forward and backward buttons
        enabled = animationIsOnPause,
    ) {
//                Icon(imageVector = Icons.Default.Redo, contentDescription = "redo", tint = Color.Black)
        Icon(imageVector = Icons.Default.SkipNext, contentDescription = "skipNext", tint = if (animationIsOnPause) Color.Black else Color.DarkGray)
    }
}

@Composable
fun BackButton(screenConfig: ScreenConfig, gameDataViewModel: GameDataViewModel, animationIsOnPause: Boolean) {
    IconButton(
        onClick = {
            Log.e("BACK", "clicked")
            gameDataViewModel.AnimationGoingBackChangeStatus()
        },
        modifier = Modifier
            .testTag(TAG_BUTTON_PREV)
            .height(screenConfig.playButtonsHeight.dp)
            .width(screenConfig.playButtonsWidth.dp)
            .background(Color.Gray)
        ,
        enabled = animationIsOnPause,
    ) {
//                Icon(imageVector = Icons.Default.Undo, contentDescription = "undo", tint = Color.Black)
        Icon(imageVector = Icons.Default.SkipPrevious, contentDescription = "skipPrevious", tint = if (animationIsOnPause) Color.Black else Color.DarkGray)
    }
}

@DelicateCoroutinesApi
fun StartAnimation(lvl: RobuzzleLevel, gameDataViewModel: GameDataViewModel) {
    gameDataViewModel.gameLogicJob = GlobalScope.launch( Dispatchers.Default + CoroutineName( GAME_LOGIC_COROUTINE ) )
    {
        Log.e("", "call StartAnimation")
        AnimationLogic(lvl.breadcrumb, gameDataViewModel).Start()
    }
}

