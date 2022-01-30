package com.mobilegame.robozzle.domain.state

sealed class TokenState(val ret: String) {
    object Invalid: TokenState("invalid_token_ret")
    object NoToken: TokenState("no_token_token_ret")
    object Valid: TokenState("valid_token_ret")
}
