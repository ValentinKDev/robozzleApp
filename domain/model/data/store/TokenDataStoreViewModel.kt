package com.mobilegame.robozzle.domain.model.data.store

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilegame.robozzle.data.store.DataStoreService
import com.mobilegame.robozzle.domain.res.NOTOKEN
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class TokenDataStoreViewModel (
    context: Context
) : ViewModel() {
    private val service = DataStoreService.createTokenService(context)

    fun saveToken(value: String) {
        viewModelScope.launch(Dispatchers.IO) {
            service.putString(KeyProvider.Token.key, value)
        }
    }

    fun getTokenData(): String? = runBlocking(Dispatchers.IO) {
        service.getString(KeyProvider.Token.key)
    }

    fun delTokenData() = runBlocking(Dispatchers.IO) {
        service.delString(KeyProvider.Token.key)
    }

    fun getToken(): String {
        return getTokenData() ?: NOTOKEN
    }
}
