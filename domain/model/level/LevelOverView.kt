package com.mobilegame.robozzle.domain.model.level

data class LevelOverView(
    val id: Int,
    val name: String,
    val map: List<String>
)