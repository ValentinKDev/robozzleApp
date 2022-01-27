package com.mobilegame.robozzle.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class UltimateUserRequest(
    val id: String,
    val name: String,
    val password: String,
    val playerRanks: PlayerRanksRequest
)

@Serializable
data class PlayerRanksRequest(
    val resolvedLevels: List<LevelResolvedRequest>
)

@Serializable
data class LevelResolvedRequest(
    val lvl_id: Int,
    val difficulty: Int,
    val details: WinDetailsRequest
)

@Serializable
data class WinDetailsRequest(
    val instructionsNumber: Int,
    val actionsNumber: Int,
    val solutionFound: List<List<String>>
)
