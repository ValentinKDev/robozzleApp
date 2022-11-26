package com.mobilegame.robozzle.domain.model.Screen.Tuto

sealed class Tuto(val step: Int, val description: String) {
    object ClickOnProfile: Tuto(1, "Please tap on Profile button to create an account")
    object ClickOnDifficultyOne: Tuto(2, "Please tap on Difficulty 1 button to access the list of levels")
    object ClickOnTutoLevel: Tuto(3, "Please tap on the Tuto level to start playing")
    object ClickOnFirstInstructionCase: Tuto(4, "Please tap on the first case of the first line to select an instruction")
    object ClickOnFirstInstructionFromMenu: Tuto(5, "Please tap on the instruction moving forward on blue case")
    object ClickOnSecondInstructionCase: Tuto(6, "Please tap on the second case of the first line to select an instruction")
    object ClickOnSecondInstructionFromMenu: Tuto(7, "Please tap on the gray instruction moving forward")
    object ClickOnPlayFirstTime: Tuto(8, "Gray instructions works on any colors, please tap on Play button")
    object ClickOnRestFirstTime: Tuto(9, "Please tap on Reset button to get back to your initial place")
    object ClickOnThirdInstructionCase: Tuto(10, "Please tap on the third case of the first line to select an instruction")
    object ClickOnRepeatingFirstLineGray: Tuto(11, "To repeat the first line please tap on the one instruction")
    object ClickOnPlaySecondTime: Tuto(12, "The first line now repeat itself as you will see when tapping the play button")
    object ClickOnResetSecondTime: Tuto(13, "Please tap on Reset button to get back to your initial place")
    object ClickOnMapCaseFirst: Tuto(14, "Please tap on the map to place a stop mark")
    object ClickOnPlayThirdTime: Tuto(15, "Your player will stop on the mark so you will see why you going out of map. Please tap the play button")
    object ClickAndDragActionBar: Tuto(16, "You will see in the actions bar which instructions your character will execute. Please slide the bar, to go forward and backward")
    object ClickDragAndDropThirdCase: Tuto(17, "Please drag the third instruction to the end of the line")
    object ClickOnFirstInstructionCaseSecondLine: Tuto(18, "Please tap on the first case of the second line to select an instruction")
    object ClickOnTurnRight: Tuto(19, "Please select the turning right instruction")
    object ClickOnThirdInstructionCaseAgain: Tuto(20, "Please tap on the third case of the first line to select an instruction")
    object ClickOnCallSecondLine: Tuto(21, "Please call the second line on red cases only")
    object ClickOnPlayFourthTime: Tuto(22, "The first line will call the second line on the red cases. Please press the play button")
    object ClickOnResetThirdTime: Tuto(23, "Please tap on Reset button to get back to your initial place")
    object DragInstructionToTrash: Tuto(24, "Please Drag the turning right instruction and place it in the red trash")
//    object ClickOnFourthInstructionCase: Tuto(17, "Please tap on the fourth case of the first line to select an instruction")
//    object ClickOnRepeatingFirstLineGray: Tuto(11, "To repeat the first line please tap on the one instruction")

//    object ClickOnMovingForward1: Tuto(15, "Please tap on the button Next and look at your character")
//    object ClickOnMovingForward2: Tuto(16, "Please tap again on the button Next and look at your character")
//    object ClickOnMovingForward3: Tuto(17, "Please tap one last time on the button Next and look at your character")

    object End: Tuto(27, "Tutorial finished, good luck !")

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
