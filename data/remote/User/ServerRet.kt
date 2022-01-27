package com.mobilegame.robozzle.data.remote.User

sealed class ServerRet(val ret: String) {
    object POSITIV: ServerRet("positiv_ret")
    object ERROR200: ServerRet("error200_ret")
    object ERROR300: ServerRet("error300_ret")
    object ERROR400: ServerRet("error400_ret")
    object ERROR500: ServerRet("error500_ret")
    object EXCETPTION: ServerRet("exception_ret")
    object NOTRECEIVED: ServerRet("not_received_ret")
}

sealed class UserState(val status: String) {
    object LOGGED: UserState("is_logged_to_server")
    object NOTLOGGED: UserState("is_not_logged_to_server")
    object NOUSER: UserState("is_not_existing")
}
