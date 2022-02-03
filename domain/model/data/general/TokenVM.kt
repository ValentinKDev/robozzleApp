package com.mobilegame.robozzle.domain.model.data.general

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.mobilegame.robozzle.data.store.DataStoreService
import com.mobilegame.robozzle.domain.model.data.server.token.TokenServerViewModel
import com.mobilegame.robozzle.domain.model.data.store.TokenDataStoreViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class TokenVM(
    context: Context
): AndroidViewModel(context as Application) {
    val context = getApplication<Application>()

    fun getToken(): String? = runBlocking(Dispatchers.IO) {
        val localToken = TokenDataStoreViewModel(context).getTokenData()

        localToken ?: TokenServerViewModel(context).getTokenServer()
    }
}