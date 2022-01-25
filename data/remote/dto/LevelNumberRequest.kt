package com.mobilegame.robozzle.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LevelNumberRequest (
    @SerialName("numberOfLevels")
    val numberOfLevels: Int
)