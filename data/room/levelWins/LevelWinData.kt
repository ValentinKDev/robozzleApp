package com.mobilegame.robozzle.data.room.levelWins

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "level_win_table")
data class LevelWinData(
    @PrimaryKey val levelId: Int,
//    @ColumnInfo(name = "id_level") val idLevel: Int,
    @ColumnInfo(name = "points") val points: Int,
    @ColumnInfo(name = "WinDetails") val winDetailsJson: String,
//    @ColumnInfo(name = "instruction_number") val instructionNumberJson: String,
//    @ColumnInfo(name = "action_number") val actionNumberJson: String,
//    @ColumnInfo(name = "solution_found") val solutionFoundJson: String,
)
