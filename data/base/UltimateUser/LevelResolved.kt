package com.mobilegame.robozzle.data.base.UltimateUser

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "level_resolved_table")
data class LevelResolved(
    @PrimaryKey val lvlId: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "difficulty") val difficulty: Int,
    @ColumnInfo(name = "instructionNumber") val instructionsNumber: Int,
    @ColumnInfo(name = "actionsNumber") val actionsNumber: Int,
    @ColumnInfo(name = "solutionFound") val solutionFound: List<List<String>>,
)