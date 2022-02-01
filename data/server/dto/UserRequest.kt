package com.mobilegame.robozzle.data.server.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserRequest (
//    @SerialName("name")
    val name: String,
//    @SerialName("password")
    val password: String
)
