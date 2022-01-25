package com.mobilegame.robozzle.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserRequest (
//    @SerialName("name")
    val name: String,
//    @SerialName("password")
    val password: String
)
