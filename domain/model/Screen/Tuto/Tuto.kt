package com.mobilegame.robozzle.domain.model.Screen.Tuto

import com.mobilegame.robozzle.analyse.verbalLog

sealed class Tuto(val step: Int, val description: String) {
    object ClickOnProfile: Tuto(1, "Please tap on Profile button to create an account or log to an old account")
    object ClickOnDifficultyOne: Tuto(2, "Please tap on Difficulty 1 button to access the list of levels")

    object ClickOnRankingIconFirst: Tuto(3, "Please press the ranking icon to look at other players scores")
    object ClickOnRankingIconSecond: Tuto(4, "Please press the ranking icon to look at other players scores")

    object ClickOnTutoLevel: Tuto(5, "Please tap on the Tuto level to start playing")
    object QuickPresentation: Tuto(6, "Robozzle is a puzzle about colors conditions, the purpose will be to give instructions to the orange ship and helping him to get all stars, the first line of instructions will always be read at least once")
    object ClickOnFirstInstructionCase: Tuto(7, "Please tap on the first case of the first line to select an instruction")
    object ClickOnFirstInstructionFromMenu: Tuto(8, "Please tap on the instruction moving forward on blue case")
    object ClickOnSecondInstructionCase: Tuto(9, "Please tap on the second case of the first line to select an instruction")
    object ClickOnSecondInstructionFromMenu: Tuto(10, "Please tap on the gray instruction moving forward")
    object ClickOnPlayFirstTime: Tuto(11, "Gray instructions works on any colors, please tap on Play button")
    object ClickOnRestFirstTime: Tuto(12, "Please tap on Reset button to get back to your initial place")
    object ClickOnThirdInstructionCase: Tuto(13, "Please tap on the third case of the first line to select an instruction")
    object ClickOnRepeatingFirstLineGray: Tuto(14, "To repeat the first line please tap on the one instruction")
    object ClickOnPlaySecondTime: Tuto(15, "The first line now repeat itself as you will see when tapping the play button")
    object ClickOnResetSecondTime: Tuto(16, "Please tap on Reset button to get back to your initial place")
    object ClickOnMapCaseFirst: Tuto(17, "Please tap on the map to place a stop mark")
    object ClickOnPlayThirdTime: Tuto(18, "Your player will stop on the mark so you will see why you going out of map. Please tap the play button")
    object ClickAndDragActionBar: Tuto(19, "You will see in the actions bar which instructions your character will execute. Please slide the bar, to go forward and backward")
    object ClickDragAndDropThirdCase: Tuto(20, "Please drag the third instruction to the end of the line")
    object ClickOnFirstInstructionCaseSecondLine: Tuto(21, "Please tap on the first case of the second line to select an instruction")
    object ClickOnTurnRight: Tuto(22, "Please select the turning right instruction")
    object ClickOnThirdInstructionCaseAgain: Tuto(23, "Please tap on the third case of the first line to select an instruction")
    object ClickOnCallSecondLine: Tuto(24, "Please call the second line on red cases only")
    object ClickOnPlayFourthTime: Tuto(25, "The first line will call the second line on the red cases. Please press the play button")
    object ClickOnResetThirdTime: Tuto(26, "Please tap on Reset button to get back to your initial place")
    object DragInstructionToTrash: Tuto(27, "Please Drag the turning right instruction and place it in the red trash")
    object ClickOnFirstInstructionCaseSecondLineAgain: Tuto(28, "Please tap again on the first case of the second line to select an instruction")
    object SelectMovingForward: Tuto(29, "Please select the gray moving forward instruction")
    object ClickOnSecondInstructionCaseSecondLine: Tuto(30, "Please tap on the second case of the second line to select an instruction")
    object SelectTurnLeft: Tuto(31, "Please select the gray left turn instruction")
    object LetsGo: Tuto(32, "Let's try this")
    object OneLastTime: Tuto(33, "Press Play button one last time")
    object End: Tuto(34, "Tutorial finished, good luck !")

    companion object {
        fun findTutoByStep(number: Int): Tuto? {
            return Tuto::class.sealedSubclasses
                .firstOrNull {
                    it.objectInstance?.step == number
                }?.objectInstance
        }

        fun Tuto.isMainScreenTutoOn(): Boolean {
            verbalLog("Tuto::isMainScreenTutoOn", "Tuto = ${this.step}")
            return this != Tuto.End
        }
        fun Tuto.isLevelsScreenByDifficultyOn(levelDifficulty: Int): Boolean = levelDifficulty == 1 && ( this.step in Tuto.ClickOnRankingIconFirst.step until Tuto.End.step )
        fun Tuto.isTutoPresentation(): Boolean = this == QuickPresentation
    }
}

fun Tuto.matchStep(tuto: Tuto): Boolean = this.step == tuto.step
