package com.mobilegame.robozzle.data.remote.dto.UltimateUser

import kotlinx.serialization.Serializable

@Serializable
data class LevelResolvedRequest(
    val lvl_id: Int,
    val difficulty: Int,
    val details: WinDetailsRequest
)
