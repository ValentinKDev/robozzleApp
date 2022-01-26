package com.mobilegame.robozzle.domain.model

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.*
import com.mobilegame.robozzle.data.remote.User.UserService
import com.mobilegame.robozzle.data.store.*
import com.mobilegame.robozzle.data.store.user.User
import com.mobilegame.robozzle.domain.model.User.ProfilViewModel
import com.mobilegame.robozzle.domain.model.User.ResolvedLevelViewModel
import com.mobilegame.robozzle.presentation.res.NONE
import com.mobilegame.robozzle.domain.res.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

//todo: Securise to API HTTP
@InternalCoroutinesApi
class UserViewModel(application: Application): AndroidViewModel(application) {
    private val Application.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_DATASTORE)

    val id = UNKNOWN

    val profilVM = ProfilViewModel()
    val lvlStatsVM = ResolvedLevelViewModel(application)

    var service: UserService

    private val _currentUser = MutableLiveData<User?>(null)
    val currentUser: MutableLiveData<User?> = _currentUser

    private val _noUser = MutableStateFlow(true)
    val noUser: StateFlow<Boolean> = _noUser

    fun GetCurrentUserName(): String {
        return _currentUser.value!!.name
    }

    private val _connected = MutableLiveData<Boolean>(false)
    val connected: MutableLiveData<Boolean> = _connected
    fun ConnectedIsTrue() {_connected.postValue(true)}

    init {
        viewModelScope.launch {
            LoadUser()
        }
        service = UserService.create()
    }

    suspend fun LoadUser() {
        val user = getUserFromDatastore()
        _currentUser.postValue( if (user.equals(User(NONE, NONE, NONE))) null else user)
        _currentUser.value?.let {
            _noUser.value = false
        }
    }

    private suspend fun getUserFromDatastore(): User {
        val dataStore: DataStore<Preferences> = getApplication<Application>().dataStore

        return User(
            id = getStringFromDatastore(USER_ID_INFOS_KEY, dataStore),
            name = getStringFromDatastore(USER_NAME_INFOS_KEY, dataStore),
            password = getStringFromDatastore(USER_PASSWORD_INFOS_KEY, dataStore)
        )
    }

    private suspend fun saveUserInDatastore(user: User) {
        val dataStore: DataStore<Preferences> = getApplication<Application>().dataStore

        saveStringInDatastore(USER_ID_INFOS_KEY, user.id, dataStore)
        saveStringInDatastore(USER_NAME_INFOS_KEY, user.name, dataStore)
        saveStringInDatastore(USER_PASSWORD_INFOS_KEY, user.password, dataStore)
    }
//    private suspend fun getUserInfoFromDatastore(infoKey: String, default: String = NONE): String = withContext(Dispatchers.IO) {
//        val wrappedKey = stringPreferencesKey(infoKey)
//        val valueFlow: Flow<String> = getApplication<Application>().dataStore.data.map { it[wrappedKey] ?: default }
//        return@withContext valueFlow.first()
//    }

//    suspend fun saveUserInfoInDatastore(infoKey: String, value: String) = withContext(Dispatchers.IO) {
//        val wrappedKey = stringPreferencesKey(infoKey)
//        getApplication<Application>().dataStore.edit {
//            it[wrappedKey] = value
//        }
//    }
}

@InternalCoroutinesApi
class UserViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
