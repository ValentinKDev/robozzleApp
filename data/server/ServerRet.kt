package com.mobilegame.robozzle.data.server

sealed class ServerRet(val ret: String) {
    object Positiv: ServerRet("positiv_ret")
    object Error200: ServerRet("error200_ret")
    object Error300: ServerRet("error300_ret")
    object Error400: ServerRet("error400_ret")
    object Error500: ServerRet("error500_ret")
    object Exception: ServerRet("exception_ret")
    object NotAttribution: ServerRet("not_received_ret")
}

sealed class UserState(val status: String) {
    object LOGGED: UserState("is_logged_to_server")
    object NOTLOGGED: UserState("is_not_logged_to_server")
    object NOUSER: UserState("is_not_existing")
}
