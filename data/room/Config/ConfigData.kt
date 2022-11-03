package com.mobilegame.robozzle.data.room.Config

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "config_table")
data class ConfigData(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "lightTheme") val lightTheme: Boolean,
    @ColumnInfo(name = "trashes_in_Game") val trashesInGame: Boolean,
    @ColumnInfo(name = "display_level_win_list") val displayLevelWinInList: Boolean,
)