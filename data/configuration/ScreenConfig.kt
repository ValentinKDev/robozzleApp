package com.mobilegame.robozzle.data.configuration

class ScreenConfig {
    var popUp: PopUpState = PopUpState.None

    //todo use this class to manage animation from - to

}

enum class PopUpState {
    Update, UnreachableServer, None
}