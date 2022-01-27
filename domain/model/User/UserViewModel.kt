package com.mobilegame.robozzle.domain.model

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.*
import com.mobilegame.robozzle.data.base.UltimateUser.User
import com.mobilegame.robozzle.data.remote.JwtToken.JWTTokenService
import com.mobilegame.robozzle.data.remote.User.UserService
import com.mobilegame.robozzle.data.remote.dto.UserRequest
import com.mobilegame.robozzle.data.store.*
import com.mobilegame.robozzle.data.store.user.UserStore
import com.mobilegame.robozzle.domain.DataStoredProviding
import com.mobilegame.robozzle.domain.model.User.ProfilViewModel
import com.mobilegame.robozzle.domain.model.User.RegisterLoginViewModel
import com.mobilegame.robozzle.domain.model.User.ResolvedLevelViewModel
import com.mobilegame.robozzle.presentation.res.NONE
import com.mobilegame.robozzle.domain.res.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

//todo: Securise to API HTTP
@InternalCoroutinesApi
class UserViewModel(application: Application): AndroidViewModel(application) {
    private val Application.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_DATASTORE)

    val lvlStatsVM = ResolvedLevelViewModel(application)
//    val id = UNKNOWN
    var tab = 2

    val profilVM = ProfilViewModel()

    val registLogVM = RegisterLoginViewModel()

//    var userService: UserService

    private val _currentUser = MutableStateFlow<UserStore?>(null)
    val currentUser: StateFlow<UserStore?> = _currentUser
//    private val _currentUser = MutableLiveData<UserStore?>(null)
//    val currentUser: MutableLiveData<UserStore?> = _currentUser

    private val _tokenJwt = MutableStateFlow<String>("")
    val tokenJwt: StateFlow<String> = _tokenJwt

    private val _noUser = MutableStateFlow(true)
//    val noUser: StateFlow<Boolean> = _noUser
    fun UserNotStored(): Boolean = _noUser.value

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
//        userService = UserService.create(_tokenJwt.value)
    }

    //todo : do i have to store the token?
    suspend fun GetAToken() {
        withContext(Dispatchers.IO) {
            val tokenService = JWTTokenService.create(registLogVM.name.value, registLogVM.password.value)
            _tokenJwt.value = tokenService.getJwtToken(UserRequest(registLogVM.name.value, registLogVM.password.value))
        }
    }

    suspend fun GetUserFromServer() {
        withContext(Dispatchers.IO) {
            val userService = UserService.create(_tokenJwt.value)
//            val ultimateUser: UltimateUserRequest? = userService.getUltimateUser()
        }
    }

    suspend fun LoadUser() {
        val user = getUserFromDatastore()
        _currentUser.value = if (user.equals(UserStore(NONE, NONE, NONE))) null else user
        _currentUser.value?.let { _noUser.value = false }
    }

    private suspend fun getUserFromDatastore(): UserStore {
        val dataStore: DataStore<Preferences> = getApplication<Application>().dataStore

        return UserStore(
            id = getStringFromDatastore(DataStoredProviding.ID.key, dataStore),
            name = getStringFromDatastore(DataStoredProviding.NAME.key, dataStore),
            password = getStringFromDatastore(DataStoredProviding.PASSWORD.key, dataStore)
        )
    }

    suspend fun saveUserInDatastore(user: User) {
        val dataStore: DataStore<Preferences> = getApplication<Application>().dataStore

//        saveStringInDatastore(DataStoredProviding.ID.key, user.id, dataStore)
//        saveStringInDatastore(DataStoredProviding.NAME.key, user.name, dataStore)
//        saveStringInDatastore(DataStoredProviding.PASSWORD.key, user.password, dataStore)
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
