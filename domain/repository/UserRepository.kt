package com.mobilegame.robozzle.domain.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.mobilegame.robozzle.Extensions.convertToLevel
import com.mobilegame.robozzle.data.base.Level.Level
import com.mobilegame.robozzle.data.base.Level.LevelDao
import com.mobilegame.robozzle.data.base.UltimateUser.User
import com.mobilegame.robozzle.data.base.UltimateUser.UserDao
//import com.mobilegame.robozzle.data.base.User.User
//import com.mobilegame.robozzle.data.base.User.UserDao
import com.mobilegame.robozzle.data.remote.dto.LevelRequest
//import com.mobilegame.robozzle.data.store.user.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

@InternalCoroutinesApi
class UserRepository(private val userDao: UserDao) {

    fun getUser(): User? {
        val user: User? = userDao.getAUser(1)
        Log.v("UserRepository", "getUser()")
        Log.v("UserRepository", "---> name : ${user?.name}")
        Log.v("UserRepository", "---> name : ${user?.password}")
//           return userDao.getAUser(1)
        return user
    }

    suspend fun addUser(newUser: User) {
            userDao.addUser(newUser)
    }
}
