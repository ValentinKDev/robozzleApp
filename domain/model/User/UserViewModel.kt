package com.mobilegame.robozzle.domain.model

//import com.mobilegame.robozzle.data.base.UltimateUser.User
//import com.mobilegame.robozzle.data.store.user.UserStore

//todo: Securise to API HTTP
//@InternalCoroutinesApi
//class UserViewModel(application: Application): AndroidViewModel(application) {
//
////    private val userDataStoreService = DataStoreService.createUserService(getApplication())
////    val userDataStoreVM = UserDataStoreViewModel(userDataStoreService)
//    val userDataStoreVM = UserDataStoreViewModel(getApplication())
//
//    private val tokenDataVm = TokenDataStoreViewModel(getApplication())
//
//    private val _currentUser = MutableStateFlow<User?>(userDataStoreVM.getUser())
//    val currentUser: StateFlow<User?> = _currentUser
//
//    private val _tokenJwt = MutableStateFlow<String>(tokenDataVm.getToken())
//    val tokenJwt: StateFlow<String> = _tokenJwt
//
//    private val _noUser = MutableStateFlow(true)
//    fun userNotStored(): Boolean = _noUser.value
//
//    fun getCurrentUserName(): String {
//        return _currentUser.value!!.name
//    }
//
//    private val _userConnectionState = MutableStateFlow(UserConnectionState.NoUser)
//    val userConnectionSate: StateFlow<UserConnectionState> = _userConnectionState
//    private fun set_userConnectionState(state: UserConnectionState) {
//        _userConnectionState.value = state
//    }
//
////    fun getuserConnectionState(state: UserConnectionState) {
////        userDataStoreVM.getUserConnectionState()
////    }
//
//    init {
//    }
//
//    fun getUserConnectionState(): String = runBlocking(Dispatchers.IO) {
//        userDataStoreVM.getUserConnectionState() ?: UserConnection.NoUser.state
//    }
//
//    private suspend fun waitForToken() {
//        while (tokenJwt.value == NOTOKEN) {
//            infoLog("wait", "token")
//            delay(50)
//        }
//    }
//
//    suspend fun connectUserToServer(userName: String, token: String) {
//        infoLog("connectUserToServer", "userName : ${userName} token : ${token}")
//        val ultimateUserService: UltimateUserService = UltimateUserService.create(token)
//        val ultimateUser: UltimateUserRequest? = ultimateUserService.getUltimateUser(userName)
//
//        if (ultimateUser == null) {
//            set_userConnectionState(UserConnectionState.NotConnected)
//        } else {
//            set_userConnectionState(UserConnectionState.Connected)
//        }
//        //todo : UltimateUser to User ?
//        ultimateUser?. let {
//            val userId = try {
//                ultimateUser.id.toInt()
//            } catch (e: NumberFormatException) {
//                errorLog("UserToServer", "ulimateUser.id = null")
//                errorLog("UserToServer", "Error number format exception")
//                errorLog("UserToServer", "${e.message}")
//                ERROR
//            }
//            saveUserInDatastore(User(userId, ultimateUser.name, ultimateUser.password))
//        }
//    }
//
//    //todo : do i have to store the token?
////    suspend fun getAToken(name: String, password: String): String {
////        infoLog("userVM", "getAToken()")
////        var token: String? = null
////        val jwtTokenService: JWTTokenService = JWTTokenService.create(name, password)
////        token = jwtTokenService.getJwtToken()
////
////        return (if (token == null) { "" } else {
////            _tokenJwt.value = token
////            tokenDataVm.saveToken(token)
////            token
////        })
////    }
//
//    fun saveUserInDatastore(user: User) {
//        infoLog("saveUserInDatastore", "start")
//        userDataStoreVM.saveUser(user)
//    }
//
//
//    fun connectedTO(state: UserConnectionState) {
//        viewModelScope.launch {
//            _userConnectionState.emit(state)
//        }
//    }
//}
