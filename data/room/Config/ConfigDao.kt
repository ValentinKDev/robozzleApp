package com.mobilegame.robozzle.data.room.Config

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mobilegame.robozzle.data.room.Level.LevelData

@Dao
interface ConfigDao {
    @Query("SELECT * FROM config_table")
    fun getAllConfigs(): List<ConfigData>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addConfig(config: ConfigData)

    @Query("SELECT * FROM config_table WHERE id = 0")
    fun getConfig(): ConfigData

    @Query("SELECT trashes_in_Game FROM config_table WHERE id = 0")
    fun getTrashesInGameState(): Boolean

    @Query("SELECT display_level_win_list FROM config_table WHERE id = 0")
    fun getDisplayLevelWinListState(): Boolean

    @Query("SELECT orientation FROM config_table WHERE id = 0")
    fun getOrientation(): Int

    @Query("UPDATE config_table SET trashes_in_Game = :state WHERE id = 0 ")
    fun upDateTrashesInGameStateTo(state: Boolean)

    @Query("UPDATE config_table SET display_level_win_list = :state WHERE id = 0 ")
    fun upDateDisplayLevelWinListTo(state: Boolean)

    @Query("UPDATE config_table SET orientation = :state WHERE id = 0 ")
    fun updateOrientation(state: Int)
}