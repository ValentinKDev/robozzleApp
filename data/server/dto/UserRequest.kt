package com.mobilegame.robozzle.data.server.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserRequest (
    val name: String,
    val password: String
)
