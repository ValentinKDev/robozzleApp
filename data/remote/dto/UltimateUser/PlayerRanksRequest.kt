package com.mobilegame.robozzle.data.remote.dto.UltimateUser

import kotlinx.serialization.Serializable

@Serializable
data class PlayerRanksRequest(
    val resolvedLevels: List<LevelResolvedRequest>
)
