package com.mobilegame.robozzle.data.base.LevelVersion

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "level_version")
data class Level_Version(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "version") val version: String,
)
