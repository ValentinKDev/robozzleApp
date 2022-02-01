package com.mobilegame.robozzle.data.server.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Level_VersionRequest (
    @SerialName("version")
    val version: String
)
