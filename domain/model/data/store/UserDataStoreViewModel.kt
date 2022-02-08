package com.mobilegame.robozzle.domain.model.data.store

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.data.store.DataStoreService
import com.mobilegame.robozzle.domain.User
import com.mobilegame.robozzle.domain.res.ID_NO_VALUE
import com.mobilegame.robozzle.domain.res.NAME_NO_VALUE
import com.mobilegame.robozzle.domain.res.PASSWORD_NO_VALUE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

//todo : declare the service here by using the context direction in the param ?
class UserDataStoreViewModel (
    context: Context
//    private val service: DataStoreService
) : ViewModel() {
    private val service = DataStoreService.createUserService(context)

    fun getUser(): User {
        infoLog("get user in Datastore from UserDataStoreViewModel", "start")
        val id = getId() ?: ID_NO_VALUE
        val name = getName() ?: NAME_NO_VALUE
        val password = getPassword() ?: PASSWORD_NO_VALUE

        infoLog(id.toString(), "from datastore")
        infoLog(name, "name from datastore")
        infoLog(password, "password from datastore")
        return User(id, name, password)
    }

    fun clearUser() {
        viewModelScope.launch(Dispatchers.IO) {
            service.delInt(KeyProvider.Id.key)
            service.delString(KeyProvider.Name.key)
            service.delString(KeyProvider.Password.key)
        }
    }

    fun saveUser(user: User) {
        saveId(user.id)
        saveName(user.name)
        savePassword(user.password)
    }

    fun saveUserConnectionState(state: String) {
        viewModelScope.launch {
            service.putString(KeyProvider.ConnectionState.key, state)
        }
    }

    private fun saveName(value: String) {
        viewModelScope.launch {
            service.putString(KeyProvider.Name.key, value)
        }
    }

    fun getUserConnectionState(): String? = runBlocking {
        infoLog("get", "user connection state")
        service.getString(KeyProvider.ConnectionState.key)
    }

    fun getName(): String? = runBlocking {
        service.getString(KeyProvider.Name.key)
    }

    private fun savePassword(value: String) {
        viewModelScope.launch {
            service.putString(KeyProvider.Password.key, value)
        }
    }

    private fun getPassword(): String? = runBlocking {
        service.getString(KeyProvider.Password.key)
    }

    private fun saveId(value: Int) {
        viewModelScope.launch {
            service.putInt(KeyProvider.Id.key, value)
        }
    }

    fun getId(): Int? = runBlocking {
        service.getInt(KeyProvider.Id.key)
    }
}