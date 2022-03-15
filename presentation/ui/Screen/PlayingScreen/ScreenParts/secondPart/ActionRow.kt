package com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.ScreenParts.secondPart

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import backColor
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.analyse.logAnimLayoutSecondPart
import com.mobilegame.robozzle.analyse.logLayoutSecondPart
import com.mobilegame.robozzle.analyse.verbalLog
import com.mobilegame.robozzle.domain.RobuzzleLevel.FunctionInstruction
import com.mobilegame.robozzle.domain.model.Screen.InGame.GameDataViewModel
import com.mobilegame.robozzle.domain.model.data.animation.MainMenuAnimationViewModel
import com.mobilegame.robozzle.presentation.res.ColorsList
import com.mobilegame.robozzle.presentation.ui.Screen.PlayingScreen.InstructionIconsActionRow
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposable
import com.mobilegame.robozzle.presentation.ui.utils.spacer.HorizontalSpace
import com.mobilegame.robozzle.presentation.ui.utils.spacer.VerticalSpace
import com.mobilegame.robozzle.utils.Extensions.Is
import com.mobilegame.robozzle.utils.Extensions.Not
import com.mobilegame.robozzle.utils.Extensions.subListIfPossible
import gradientBackground

//Todo: replace all this logic based on the breadCrumb in an viewModel or an other entity to unload the screen ??? should it be really on the main thread ?
//Todo: replace fonction calls by little spaces
//Todo: delay the disparition of the last action in actionList
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun DisplayActionsRow(vm: GameDataViewModel) {

    val actionList: List<FunctionInstruction> by vm.animData.actionRowList.collectAsState()
    val displayInstructionMenu: Boolean by vm.displayInstructionsMenu.collectAsState()

    val actionPair: Boolean by remember (vm) {vm.animData.pair}.collectAsState()
    val animDelay: Long by vm.animData.animationDelay.collectAsState()

    val visibleAnimDelay: Int = (animDelay / 7).toInt()

    logLayoutSecondPart?.let {
        infoLog("action row case size", "${vm.data.layout.secondPart.size.actionRowCase}")
        infoLog("action row case border size", "${vm.data.layout.secondPart.size.actionRowCaseBorder}")
        verbalLog("action to read", vm.animData.actionToRead.value.toString())
        verbalLog("Display vm.ActionsList ", "${vm.animData.actionRowList.value}")
        verbalLog("number of action to display", "${vm.data.layout.secondPart.actionToDisplayNumber}")
    }

    Row() {
        Row( Modifier
            .fillMaxHeight()
            .weight(vm.data.layout.secondPart.ratios.actionRowFirstPart)
            ,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (actionList.isNotEmpty())
                ActionRowCase(vm = vm, case = actionList.first(), filter = displayInstructionMenu, bigger = true)
        }
        Row( Modifier
            .fillMaxHeight()
//            .backColor(Color.Yellow)
            .weight(vm.data.layout.secondPart.ratios.actionRowSecondPart)
            ,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(Modifier
                .wrapContentSize()
//                        .weight(3F)
//                        .background(Color.Magenta)
                ,
                verticalAlignment = Alignment.CenterVertically
                ,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                actionList.subListIfPossible(fromIndex = 1, toIndex = 8)
                    .forEachIndexed { index, functionInstruction ->
                        logAnimLayoutSecondPart?.let {
                            verbalLog(
                                "Display Action",
                                "index $index : $functionInstruction"
                            )
                        }
                        ActionRowCase(
                            vm = vm,
                            case = functionInstruction,
                            filter = displayInstructionMenu
                        )
//                                HorizontalSpace(widthDp = vm.data.layout.secondPart.size.widthDp/70)
                    }
            }
            Row(Modifier
//                        .weight(1F)
//                        .background(Color.Yellow)
            ) {
                AnimatedVisibility(
                    visible = actionPair Is true,
//                    enter = slideInHorizontally(
//                        initialOffsetX = {vm.data.layout.secondPart.size.width + it},
//                        animationSpec = tween(visibleAnimDelay)
//                    )
                    enter = expandHorizontally(
                        expandFrom = Alignment.End,
//                        initialOffsetX = {vm.data.layout.secondPart.size.width + it},
                        animationSpec = tween(visibleAnimDelay)
                    )
//                                    + fadeIn(animationSpec = tween(visibleAnimDelay)),
//                        exit = slideOutHorizontally(
//                            animationSpec = tween(visibleAnimDelay)
//                        ),
                ) {
                    ActionRowCase(vm = vm, case = actionList[8], filter = displayInstructionMenu)
                }
//                    } else {
                AnimatedVisibility(
                    visible = actionPair Not true,
                    enter = expandHorizontally(
                        expandFrom = Alignment.End,
//                        initialOffsetX = {vm.data.layout.secondPart.size.width + it},
                        animationSpec = tween(visibleAnimDelay)
                    )
//                    enter = slideInHorizontally(
//                        initialOffsetX = {vm.data.layout.secondPart.size.width + it},
//                        animationSpec = tween(visibleAnimDelay)
//                    )
//                                    + fadeIn(animationSpec = tween(visibleAnimDelay)),
//                        exit = slideOutHorizontally(
//                            animationSpec = tween(visibleAnimDelay)
//                        ),
                ) {
                    ActionRowCase(vm = vm, case = actionList[8], filter = displayInstructionMenu)
                }
            }
        }
    }
}

@Composable
fun ActionRowCase(vm: GameDataViewModel, case: FunctionInstruction, filter: Boolean, bigger: Boolean = false) {
    Card(
        Modifier
            .size(
                if (bigger)
                    vm.data.layout.secondPart.size.actionRowCaseBigger.dp
                else
                    vm.data.layout.secondPart.size.actionRowCase.dp
            )
            .border(
                border = BorderStroke(
                    width = 2.dp,
                    color = Color.Black
                ),
                shape = RoundedCornerShape(corner = CornerSize(5.dp))
            )
            ,
            shape = RoundedCornerShape(corner= CornerSize(5.dp)),
            elevation = if (bigger)
                vm.data.colors.actionRowCaseElevation
            else
                vm.data.colors.actionRowCaseBiggerElevation
        ) {
            Box( Modifier.gradientBackground(
                colors = ColorsList(
                    case.color,
                    filter
                ),
                angle = 175F
            )
            ) {
                InstructionIconsActionRow(case.instruction, vm)
            }
        }
}
@Composable
fun ActionRowSurronder(vm: GameDataViewModel) {
    Column() {
        Surface(
            elevation = 5.dp,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .weight(vm.data.layout.secondPart.ratios.actionRowSurronderBlackLineHeight)
        ) { }
        Row(modifier = Modifier
            .fillMaxWidth()
            .weight(vm.data.layout.secondPart.ratios.actionRowSurronderEmptyLineHeight)
            .background(Color.Transparent)
        ){}
        Surface(
            elevation = 5.dp,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .weight(vm.data.layout.secondPart.ratios.actionRowSurronderBlackLineHeight)
        ) { }
    }
}
