package com.mobilegame.robozzle.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class UltimateUserRequest(
    val id: String,
    val name: String,
    val password: String,
    val playerRanksJson: String
)