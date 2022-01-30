package com.mobilegame.robozzle.domain.state

sealed class LoginConnection(state: String) {
    object InProcess: LoginConnection("in_process_state")
    object InvalidToken: LoginConnection("invalid_token_state")
    object InvalidUser: LoginConnection("invalid_user_state")
}
