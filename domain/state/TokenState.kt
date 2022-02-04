package com.mobilegame.robozzle.domain.state

//sealed class TokenState(val ret: String) {
//    object Invalid: TokenState("invalid_token_ret")
//    object NoToken: TokenState("no_token_token_ret")
//    object Valid: TokenState("valid_token_ret")
//}

sealed class TokenState(val server: String) {
    object ValidateBy: TokenState("validate_from_server")
    object UnauthorizedBy: TokenState("unauthorized_from_server")
    object IssueWith: TokenState("unauthorized_from_server")
    object CanNotReach: TokenState("unauthorized_from_server")
    object NoToken: TokenState("no_token_present")
}
