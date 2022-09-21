package com.mobilegame.robozzle.domain.model.data.general

import android.content.Context
import androidx.lifecycle.ViewModel
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.analyse.verbalLog
import com.mobilegame.robozzle.domain.model.data.server.token.TokenServerViewModel
import com.mobilegame.robozzle.domain.model.data.store.TokenDataStoreViewModel
//import com.mobilegame.robozzle.domain.state.TokenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class TokenVM(
    context: Context
): ViewModel() {
    init { verbalLog("TokenVM", "init") }

    private val tokenServerVM = TokenServerViewModel(context)
    private val tokenDataVM = TokenDataStoreViewModel(context)
    private val localToken: String? = tokenDataVM.getTokenData()

    fun getToken(): String = runBlocking(Dispatchers.IO) {
        val validity = localToken.validity()
        errorLog("validity", validity)
        when (validity) {
            TokenState.UnauthorizedBy.server -> {
                val newToken: String?
                newToken = tokenServerVM.getTokenServer() ?: TokenState.CanNotReach.server
                tokenDataVM.saveToken(newToken)
                newToken
            }
            TokenState.NoToken.server -> TokenState.NoToken.server
            else -> localToken!!
        }
    }

    private fun String?.validity(): String {
        return tokenServerVM.verifyTokenValidity(localToken)
    }
}

sealed class TokenState(val server: String) {
    object ValidateBy: TokenState("validate_from_server")
    object UnauthorizedBy: TokenState("unauthorized_from_server")
    object IssueWith: TokenState("unauthorized_from_server")
    object CanNotReach: TokenState("unauthorized_from_server")
    object NoToken: TokenState("no_token_present")
}
