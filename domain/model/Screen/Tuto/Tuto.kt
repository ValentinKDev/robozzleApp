package com.mobilegame.robozzle.domain.model.Screen.Tuto

sealed class Tuto(val step: Int, val description: String) {
    object ClickOnProfile: Tuto(1, "Please tap on Profile button to create an account")
    object ClickOnDifficultyOne: Tuto(2, "Please tap on Difficulty 1 button to access the list of levels")

    object ClickOnRankingIcon: Tuto(3, "Please press the ranking icon to look at other players scores")

    object ClickOnTutoLevel: Tuto(4, "Please tap on the Tuto level to start playing")
    object ClickOnFirstInstructionCase: Tuto(5, "Please tap on the first case of the first line to select an instruction")
    object ClickOnFirstInstructionFromMenu: Tuto(6, "Please tap on the instruction moving forward on blue case")
    object ClickOnSecondInstructionCase: Tuto(7, "Please tap on the second case of the first line to select an instruction")
    object ClickOnSecondInstructionFromMenu: Tuto(8, "Please tap on the gray instruction moving forward")
    object ClickOnPlayFirstTime: Tuto(9, "Gray instructions works on any colors, please tap on Play button")
    object ClickOnRestFirstTime: Tuto(10, "Please tap on Reset button to get back to your initial place")
    object ClickOnThirdInstructionCase: Tuto(11, "Please tap on the third case of the first line to select an instruction")
    object ClickOnRepeatingFirstLineGray: Tuto(12, "To repeat the first line please tap on the one instruction")
    object ClickOnPlaySecondTime: Tuto(13, "The first line now repeat itself as you will see when tapping the play button")
    object ClickOnResetSecondTime: Tuto(14, "Please tap on Reset button to get back to your initial place")
    object ClickOnMapCaseFirst: Tuto(15, "Please tap on the map to place a stop mark")
    object ClickOnPlayThirdTime: Tuto(16, "Your player will stop on the mark so you will see why you going out of map. Please tap the play button")
    object ClickAndDragActionBar: Tuto(17, "You will see in the actions bar which instructions your character will execute. Please slide the bar, to go forward and backward")
    object ClickDragAndDropThirdCase: Tuto(18, "Please drag the third instruction to the end of the line")
    object ClickOnFirstInstructionCaseSecondLine: Tuto(19, "Please tap on the first case of the second line to select an instruction")
    object ClickOnTurnRight: Tuto(20, "Please select the turning right instruction")
    object ClickOnThirdInstructionCaseAgain: Tuto(21, "Please tap on the third case of the first line to select an instruction")
    object ClickOnCallSecondLine: Tuto(22, "Please call the second line on red cases only")
    object ClickOnPlayFourthTime: Tuto(23, "The first line will call the second line on the red cases. Please press the play button")
    object ClickOnResetThirdTime: Tuto(24, "Please tap on Reset button to get back to your initial place")
    object DragInstructionToTrash: Tuto(25, "Please Drag the turning right instruction and place it in the red trash")
    object ClickOnFirstInstructionCaseSecondLineAgain: Tuto(26, "Please tap again on the first case of the second line to select an instruction")
    object SelectMovingForward: Tuto(27, "Please select the gray moving forward instruction")
    object ClickOnSecondInstructionCaseSecondLine: Tuto(28, "Please tap on the second case of the second line to select an instruction")
    object SelectTurnLeft: Tuto(29, "Please select the gray left turn instruction")
    object LetsGo: Tuto(30, "Now got get the star")
    object End: Tuto(33, "Tutorial finished, good luck !")

    companion object {
        fun findTutoByStep(number: Int): Tuto? {
            return Tuto::class.sealedSubclasses
                .firstOrNull {
                    it.objectInstance?.step == number
                }?.objectInstance
        }
    }
}

fun Tuto.matchStep(tuto: Tuto): Boolean = this.step == tuto.step
