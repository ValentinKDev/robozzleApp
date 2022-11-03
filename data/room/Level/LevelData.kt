package com.mobilegame.robozzle.data.room.Level

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.mobilegame.robozzle.domain.model.level.LevelState

//todo: add @NonNull ?
@Entity(tableName = "level_table")
data class LevelData(
    @PrimaryKey val id: Int,
//    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "difficulty") val difficulty: Int,
    @ColumnInfo(name = "map_json") val mapJson: String,
    @ColumnInfo(name = "instructionsMenu_json") val instructionsMenuJson: String,
    @ColumnInfo(name = "fun_instruction_json") val funInstructionsListJson: String,
    @ColumnInfo(name = "playerInitial_json") val playerInitalJson: String,
    @ColumnInfo(name = "stars_json") val starsListJson: String,

    @ColumnInfo(name = "state_key") val stateKey: String = LevelState.neverOpenedKey()
//    @Ignore@ColumnInfo(name = "state_key") val stateKey: String,
//    @ColumnInfo(name = "state_key") val stateKey: String,
)
