package com.mobilegame.robozzle.presentation.ui.button

sealed class UserInfosButton(val key: Int) {

    object None: UserInfosButton(key = 0)
    object NameCard: UserInfosButton(key = 1)
    object LogOut: UserInfosButton(key = 2)
}