package com.mobilegame.robozzle.data.server

sealed class ServerRet(val ret: String, val value: Int) {
    object Positiv: ServerRet("positiv_ret", -1)
    object Error200: ServerRet("error200_ret", -200)
    object Error300: ServerRet("error300_ret", -300)
    object Error400: ServerRet("error400_ret", -400)
    object Error500: ServerRet("error500_ret", -500)
    object Exception: ServerRet("exception_ret", -42)
    object NotAttribution: ServerRet("not_received_ret", 0)
    object Conflict: ServerRet("conflict_ret", 409)
}

sealed class UserState(val status: String) {
    object LOGGED: UserState("is_logged_to_server")
    object NOTLOGGED: UserState("is_not_logged_to_server")
    object NOUSER: UserState("is_not_existing")
}
