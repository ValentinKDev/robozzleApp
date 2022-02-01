package com.mobilegame.robozzle.data.server.dto

import kotlinx.serialization.Serializable

@Serializable
data class AppConfigRequest(
    val version: String,
    val updateAvailable: Boolean,
)
