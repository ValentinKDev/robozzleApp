package com.mobilegame.robozzle.data.base.ResolvedLevel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(tableName = "resolved_level_table")
data class ResolvedLevelData(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "difficulty") val difficulty: Int,
    @ColumnInfo(name = "stats") val stats: String,
)