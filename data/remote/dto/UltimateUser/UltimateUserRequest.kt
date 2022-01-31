package com.mobilegame.robozzle.data.remote.dto.UltimateUser

import kotlinx.serialization.Serializable

@Serializable
data class UltimateUserRequest(
    val id: String,
    val name: String,
    val password: String,

    val playerRanks: PlayerRanksRequest
)