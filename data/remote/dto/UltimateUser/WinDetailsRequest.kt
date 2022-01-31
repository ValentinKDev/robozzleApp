package com.mobilegame.robozzle.data.remote.dto.UltimateUser

import kotlinx.serialization.Serializable

@Serializable
data class WinDetailsRequest(
    val instructionsNumber: Int,
    val actionsNumber: Int,
//    val solutionFound: List<List<String>>
)
