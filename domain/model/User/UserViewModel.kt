package com.mobilegame.robozzle.domain.model

import android.app.Application
import androidx.lifecycle.*
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.data.base.UltimateUser.User
import com.mobilegame.robozzle.data.remote.JwtToken.JWTTokenService
import com.mobilegame.robozzle.data.remote.User.ServerRet
import com.mobilegame.robozzle.data.remote.User.UserService
import com.mobilegame.robozzle.data.remote.dto.UltimateUserRequest
import com.mobilegame.robozzle.data.remote.dto.UserRequest
//import com.mobilegame.robozzle.data.store.user.UserStore
import com.mobilegame.robozzle.domain.UserConnectionState
import com.mobilegame.robozzle.domain.model.Screen.RegisterScreenViewModel
import com.mobilegame.robozzle.domain.model.User.ResolvedLevelViewModel
import com.mobilegame.robozzle.data.store.DataStoreService
import com.mobilegame.robozzle.domain.model.store.UserDataStoreViewModel
import com.mobilegame.robozzle.domain.res.ERROR
import com.mobilegame.robozzle.domain.res.NOTOKEN
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.lang.NumberFormatException

//todo: Securise to API HTTP
@InternalCoroutinesApi
class UserViewModel(application: Application): AndroidViewModel(application) {

    val lvlStatsVM = ResolvedLevelViewModel(application)
//    val id = UNKNOWN
    var tab = 2

    val registLogVM = RegisterScreenViewModel()

//    var userService: UserService

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser

//    private val _currentUser = MutableLiveData<UserStore?>(null)
//    val currentUser: MutableLiveData<UserStore?> = _currentUser

    private val _tokenJwt = MutableStateFlow<String>(NOTOKEN)
    val tokenJwt: StateFlow<String> = _tokenJwt

    private val _noUser = MutableStateFlow(true)
//    val noUser: StateFlow<Boolean> = _noUser
    fun userNotStored(): Boolean = _noUser.value

    fun getCurrentUserName(): String {
        return _currentUser.value!!.name
    }

    private val _userConnectionState = MutableStateFlow(UserConnectionState.NoUser)
    val userConnectionSate: StateFlow<UserConnectionState> = _userConnectionState
    private fun set_userConnectionState(state: UserConnectionState) {
        _userConnectionState.value = state
    }

    val userDataStoreService = DataStoreService.createUserService(getApplication())
    val userDataStoreVM = UserDataStoreViewModel(userDataStoreService)

    init {
    }

    fun registerOnClickListner() {
        infoLog("register", "onclicklistner()")
        viewModelScope.launch {
            createANewUser()
            getAToken(registLogVM.name.value, registLogVM.password.value)
            waitForToken()
            connectUserToServer(registLogVM.name.value, tokenJwt.value)
        }
    }

    private suspend fun waitForToken() {
        while (tokenJwt.value == NOTOKEN) {
            infoLog("wait", "token")
            delay(50)
        }
    }

    private suspend fun createANewUser() {
        infoLog("createANewUser", "start")
        val userService: UserService = UserService.create(token = NOTOKEN)
        val newUserRequest = UserRequest(registLogVM.name.value, registLogVM.password.value)
        val serverRet: String = userService.postNewUser(newUserRequest)
        infoLog("user connection state before", "${userConnectionSate.value}")
        set_userConnectionState( when (serverRet) {
            ServerRet.Positiv.ret -> UserConnectionState.Created
            else -> UserConnectionState.NotConnected
        })
        infoLog("user connection state after", "${userConnectionSate.value}")
    }

    fun newUserCreationProcess() {
        viewModelScope.launch {
            coroutineScope {
                var token = NOTOKEN
                token = getAToken(registLogVM.name.value, registLogVM.password.value)
                waitForToken()
                connectUserToServer(registLogVM.name.value, token)
            }
        }
    }

    //todo : do i have to store the token?
    suspend fun getAToken(name: String, password: String): String {
        infoLog("userVM", "getAToken()")
        var token = NOTOKEN
        val jwtTokenService: JWTTokenService = JWTTokenService.create(name, password)
        token = jwtTokenService.getJwtToken()
        _tokenJwt.value = token
        return token
    }

    suspend fun connectUserToServer(userName: String, token: String) {
        infoLog("connectUserToServer", "userName : ${userName} token : ${token}")
        val userService: UserService = UserService.create(token)
        val ultimateUser: UltimateUserRequest? = userService.getUltimateUser(userName)

        if (ultimateUser == null) {
            set_userConnectionState(UserConnectionState.NotConnected)
        } else {
            set_userConnectionState(UserConnectionState.Connected)
        }
        ultimateUser?. let {
            val userId = try {
                ultimateUser.id.toInt()
            } catch (e: NumberFormatException) {
                errorLog("UserToServer", "ulimateUser.id = null")
                errorLog("UserToServer", "Error number format exception")
                errorLog("UserToServer", "${e.message}")
                ERROR
            }
            saveUserInDatastore(User(userId, ultimateUser.name, ultimateUser.password))
        }
    }

//    private val Application.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_DATASTORE)
//    val dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)

    fun getUserInDataStore() {
        infoLog("get user in Datastore", "start")
//        val dataStoreService = DataStoreRepository.create(getApplication())
//        val datastoreVM = DataViewModel(dataStoreService)

        val id = userDataStoreVM.getId()
        val name = userDataStoreVM.getName()
        val password = userDataStoreVM.getPassword()
        infoLog(id.toString(), "from datastore")
        infoLog(name!!, "from datastore")
        infoLog(password!!, "from datastore")
    }

    suspend fun saveUserInDatastore(user: User) {
        infoLog("saveUserInDatastore", "start")
//        val dataStoreService = DataStoreRepository.create(getApplication())
//        val userDataStoreVM = DataViewModel(dataStoreService)
        userDataStoreVM.saveId(user.id)
        userDataStoreVM.saveName(user.name)
        userDataStoreVM.savePassword(user.password)

//        saveStringInDatastore(KeyProvider.Id.key, user.id.toString(), dataStore)
//        saveStringInDatastore(KeyProvider.Name.key, user.name, dataStore)
//        saveStringInDatastore(KeyProvider.Password.key, user.password, dataStore)
//        saveStringInDatastore()


        infoLog("check ", "n")

//        dataRepo.putInt(KeyProvider.Id.key, user.id)
//        dataRepo.putString(KeyProvider.Name.key, user.name)
//        dataRepo.putString(KeyProvider.Password.key, user.password)

//        val check = dataRepo.getInt(KeyProvider.Id.key)
//        val check2 = dataRepo.getString(KeyProvider.Name.key)
//        errorLog("check", "id $check")
//        errorLog("check", "name $check2")

//        val dataStore: DataStore<Preferences> = getApplication<Application>().dataStore

    }

    //    fun collectConnectedState(): Flow<UserConnectionState> = flow {
//        if (tab == 2) {
//            emit( UserConnectionState.CONNECTED )
//        } else {
//            emit( UserConnectionState.SERVERNOTREACHED )
//        }
//    }
    fun connectedTO(state: UserConnectionState) {
        viewModelScope.launch {
//        flow {
            _userConnectionState.emit(state)
        }
    }
//    fun ConnectedIsTrue() {_connected.postValue(true)}
//    fun ConnectedIsTrue() {_connected.postValue(true)}

//    suspend fun LoadUser() {
//        val user = getUserFromDatastore()
//        _currentUser.value = if (user.equals(UserStore(NONE, NONE, NONE))) null else user
//        _currentUser.value?.let { _noUser.value = false }
//    }
//
//    private suspend fun getUserFromDatastore(): UserStore {
//        val dataStore: DataStore<Preferences> = getApplication<Application>().dataStore
//
//        return UserStore(
//            id = getStringFromDatastore(DataStoredProviding.ID.key, dataStore),
//            name = getStringFromDatastore(DataStoredProviding.NAME.key, dataStore),
//            password = getStringFromDatastore(DataStoredProviding.PASSWORD.key, dataStore)
//        )
//    }

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
