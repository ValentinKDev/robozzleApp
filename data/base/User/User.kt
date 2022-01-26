package com.mobilegame.robozzle.data.base.User

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mobilegame.robozzle.data.remote.dto.UserRequest

//@Entity(tableName = "user_table")
//data class User(
//    @PrimaryKey val id: Int,
//    @ColumnInfo(name = "name") val name: String,
//    @ColumnInfo(name = "password") val password: String,
//)
//
//fun User.toUserRequest(): UserRequest {
//    return UserRequest(this.name, this.password)
//}