package com.mobilegame.robozzle.domain.WinDetails

import com.mobilegame.robozzle.domain.RobuzzleLevel.FunctionInstructions
import kotlinx.serialization.Serializable

@Serializable
data class WinDetails(
    val instructionsNumber: Int,
    val actionsNumber: Int,
    val solutionFound: List<FunctionInstructions>,
)

