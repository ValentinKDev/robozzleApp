package com.mobilegame.robozzle.data.base.UltimateUser

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {
    @Query("SELECT * FROM user_table")
    fun getAllUser(): LiveData<List<User>>

    @Query("SELECT * FROM user_table WHERE id = :id")
    fun getAUser(id: Int): User?

    @Insert
    fun insertAll(vararg user: User)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User)

    @Delete
    fun delete(user: User)
}
