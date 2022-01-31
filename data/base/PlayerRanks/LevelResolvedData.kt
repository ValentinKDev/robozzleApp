package com.mobilegame.robozzle.data.base.PlayerRanks

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "player_ranks_table")
data class LevelResolvedData(
    @PrimaryKey val id_level: Int,
    @ColumnInfo(name = "points") val points: String,
    @ColumnInfo(name = "instruction_number") val instructionNumber: String,
    @ColumnInfo(name = "action_number") val actionNumber: String,
)
