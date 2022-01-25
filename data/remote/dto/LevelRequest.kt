package com.mobilegame.robozzle.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LevelRequest(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String,
    @SerialName("difficulty")
    val difficulty: String,
    @SerialName("map")
    val map: List<String>,
    @SerialName("instructionsMenu")
    val instructionsMenu: List<List<String>>,
    @SerialName("funInstructionsList")
    val funInstructionsList: List<List<String>>,
    @SerialName("playerInitial")
    val playerInitial: List<List<String>>,
    @SerialName("starsList")
    val starsList: List<List<String>>,
)

@Serializable
//@SerialName("com.example.applicationtest01.data.remote.dto.LevelRequest.PlayerInGame" )
data class PlayerInGameRequest(
//data class PlayerInGame(
    @SerialName("pos")
    var pos: Position,
    @SerialName("direction")
//    var direction: DirectionRequest = DirectionRequest("0", "1")
    var direction: Direction= Direction("0", "1")
)

@Serializable
@SerialName("com.example.applicationtest01.data.remote.dto.LevelRequest.FunctionInstructionsRequest" )
data class FunctionInstructionsRequest(
    @SerialName("instructions")
    val instructions: String,
    @SerialName("colors")
    val colors: String = instructions.replace("[a-zA-Z0-9.*]".toRegex(), "G")
)

@Serializable
data class Position(
    @SerialName("line")
    val line: String,
    @SerialName("column")
    val column: String
)

@Serializable
//data class DirectionRequest(
data class Direction(
    @SerialName("x")
    val x: String,
    @SerialName("y")
    val y: String
)
