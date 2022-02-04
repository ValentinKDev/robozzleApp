package com.mobilegame.robozzle.domain.model.data.general

import android.content.Context
import androidx.lifecycle.ViewModel
import com.mobilegame.robozzle.domain.model.data.server.token.TokenServerViewModel
import com.mobilegame.robozzle.domain.model.data.store.TokenDataStoreViewModel
//import com.mobilegame.robozzle.domain.state.TokenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class TokenVM(
    context: Context
): ViewModel() {
    private val tokenServerVM = TokenServerViewModel(context)
    private val tokenDataVM = TokenDataStoreViewModel(context)
    private val localToken: String? = tokenDataVM.getTokenData()

    fun getToken(): String = runBlocking(Dispatchers.IO) {
        when (localToken.validity()) {
            TokenState.ValidateBy.server -> localToken ?: tokenServerVM.getTokenServer() ?: TokenState.CanNotReach.server
            TokenState.UnauthorizedBy.server -> {
                val newToken: String?
                newToken = tokenServerVM.getTokenServer() ?: TokenState.CanNotReach.server
                tokenDataVM.saveToken(newToken)
                newToken
            }
            else -> TokenState.NoToken.server
        }
    }

    private fun String?.validity(): String? {
        return this?.let { tokenServerVM.verifyTokenValidity(it) }
    }
}

sealed class TokenState(val server: String) {
    object ValidateBy: TokenState("validate_from_server")
    object UnauthorizedBy: TokenState("unauthorized_from_server")
    object IssueWith: TokenState("unauthorized_from_server")
    object CanNotReach: TokenState("unauthorized_from_server")
    object NoToken: TokenState("no_token_present")
}
