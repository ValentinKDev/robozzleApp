package com.mobilegame.robozzle.domain.model.Screen.Tuto

sealed class Tuto(val step: Int, val description: String) {
    object ClickOnProfile: Tuto(1, "Please tap on Profile button to create an account")
    object ClickOnDifficultyOne: Tuto(2, "Please tap on Difficulty 1 button to access the list of levels")
    object ClickOnTutoLevel: Tuto(3, "Please tap on the Tuto level to start playing")
    object ClickOnFirstInstructionCase: Tuto(4, "Please tap on the first case of the first line to select an instruction")
    object ClickOnFirstInstructionFromMenu: Tuto(5, "Please tap on the instruction moving forward on blue case")
    object ClickOnPlayFirstTime: Tuto(6, "Gray instructions are exectuted in any colors, please tap on Play button")
//    object ClickOnPlayFirstTime: Tuto(6, "Gray instructions are exectuted in any colors")

    object End: Tuto(7, "Tutorial finished, good luck !")

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
