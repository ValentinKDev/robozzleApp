package com.mobilegame.robozzle.data.base.Level

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//todo: add @NonNull ?
@Entity(tableName = "level_table")
data class Level(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "difficulty") val difficulty: Int,
    @ColumnInfo(name = "instructionsMenu_str") val instructionsMenuStr: String,
    @ColumnInfo(name = "fun_instruction_str") val funInstructionsListStr: String,
    @ColumnInfo(name = "playerInitial_str") val playerInitalStr: String,
    @ColumnInfo(name = "stars_str") val starsListStr: String,
    @ColumnInfo(name = "map_Str") val mapStr: String,
)
