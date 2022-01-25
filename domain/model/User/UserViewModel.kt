package com.mobilegame.robozzle.domain.model

import android.app.Application
import androidx.lifecycle.*
import com.mobilegame.robozzle.data.base.User.User
import com.mobilegame.robozzle.data.base.User.UserDataBase
import com.mobilegame.robozzle.data.remote.User.UserService
import com.mobilegame.robozzle.data.remote.dto.UserRequest
import com.mobilegame.robozzle.domain.model.User.ProfilViewModel
import com.mobilegame.robozzle.domain.model.User.ResolvedLevelViewModel
import com.mobilegame.robozzle.domain.repository.UserRepository
import kotlinx.coroutines.*

//todo: Securise to API HTTP
@InternalCoroutinesApi
class UserViewModel(application: Application): AndroidViewModel(application) {
    val id = 1

    val profilVM = ProfilViewModel()
    val lvlStatsVM = ResolvedLevelViewModel(application)

    val repository: UserRepository

    var service: UserService

    private val _currentUser = MutableLiveData<User>(User(id, "", ""))
    val currentUser: MutableLiveData<User> = _currentUser
    fun GetCurrentUserName(): String {
        return _currentUser.value!!.name
    }

    private val _connected = MutableLiveData<Boolean>(false)
    val connected: MutableLiveData<Boolean> = _connected
    fun ConnectedIsTrue() {_connected.postValue(true)}



    init {
        val userDao = UserDataBase.getInstance(application).userDao()
        repository = UserRepository(userDao)
        service = UserService.create(_currentUser.value!!.name, _currentUser.value!!.password)
//
//        LoadUser()
//
//
//        _currentUser.value?.let {
//            IsUserStoredRemotely()
//        }

    }

    fun IsUserStoredRemotely() {
        var ret: Boolean = false
        val launch: Job = viewModelScope.launch(Dispatchers.IO) {
            var userRequest: UserRequest? =
                service.getUser("${_currentUser.value?.name}${_currentUser.value?.password}".hashCode())
            ConnectedIsTrue()
        }
    }

//    fun ConnectToServer() {
//        service.getUser()
//    }

    fun LoadUser() {
        viewModelScope.launch(Dispatchers.Default) {
            var userStored = repository.getUser(id)
            userStored?.let { _currentUser.postValue(userStored!!) }
        }
    }

    fun CreateUser(name: String, password: String) {
        val newUser = User(id, name, password)
        AddUserInternalStorage(newUser)
        AddUserToRemoteStorage(newUser)
    }

    fun AddUserInternalStorage(newUser: User) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(newUser)
        }
    }

    fun AddUserToRemoteStorage(newUser: User) {
        viewModelScope.launch(Dispatchers.IO) {
//            service.createUser(User.toUserRequest)
            service.postNewUser(UserRequest(newUser.name, newUser.password))
        }
    }
}

class UserViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    @InternalCoroutinesApi
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
