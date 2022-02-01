package com.mobilegame.robozzle.data.server.dto

import kotlinx.serialization.Serializable

@Serializable
data class LevelRequest(
    val descriptionJson: String
//    val id: Int,
//    val name: String,
//    val difficulty: Int,
//    val map: List<String>,
//    val instructionsMenuJson: String,
//    val funInstructionsListJson: String,
//    val playerInitialJson: String,
//    val starsListJson: String,
)