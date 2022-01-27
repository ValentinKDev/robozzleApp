package com.mobilegame.robozzle.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResolvedLevelRequest(
    @SerialName("lvl_id")
    val lvl_id: Int,
    @SerialName("difficulty")
    val lvl_difficulty: Int,
    @SerialName("details")
    val details: WinDetailsRequest
)

//@Serializable
//data class WinDetailsRequest(
//    @SerialName("instructionsNumber")
//    val instructionsNumber: Int,
//    @SerialName("actionsNumber")
//    val actionsNumber: Int,
//    @SerialName("solutionFound")
//    val solutionFound: List<List<String>>
