package com.mobilegame.robozzle.data.remote.dto.UltimateUser

import com.mobilegame.robozzle.data.remote.dto.FunctionInstructionsRequest
import kotlinx.serialization.Serializable

@Serializable
data class WinDetailsRequest(
    val instructionsNumber: Int,
    val actionsNumber: Int,
    val solutionFound: List<FunctionInstructionsRequest>
)
