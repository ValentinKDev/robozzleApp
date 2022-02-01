package com.mobilegame.robozzle.data.server.dto

import kotlinx.serialization.Serializable

@Serializable
data class UltimateUserRequest(
    val id: String,
    val name: String,
    val password: String,
    val playerRanksJson: String
)