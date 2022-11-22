package com.mobilegame.robozzle.domain.model.Screen.Tuto

sealed class Tuto(val step: Int, val description: String) {
    object ClickOnProfile: Tuto(1, "Please tap on Profile button to create an account")
    object ClickOnLevel1: Tuto(2, "Please tap on Difficulty 1 to find the levels")
    object ClickOnTutoLevel: Tuto(3, "Please tap on the Tuto level to start playing")

    companion object {
        fun findTutoByStep(number: Int): Tuto? {
            return Tuto::class.sealedSubclasses
                .firstOrNull {
                    it.objectInstance?.step == number
                }?.objectInstance
        }
    }
}