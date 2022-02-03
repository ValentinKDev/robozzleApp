package com.mobilegame.robozzle.data.room.PlayerRanks

import androidx.room.*

@Dao
interface PlayerRanksDao {
    @Query("SELECT * FROM player_ranks_table")
    fun getAll(): List<LevelResolvedData>


//    @Query("SELECT * FROM player_ranks_table WHERE id_level = :idLevel")
    @Query("SELECT * FROM player_ranks_table WHERE id = :idLevel")
    fun getLevelResolved(idLevel: Int): LevelResolvedData?

//    @Query("SELECT * FROM player_ranks_table WHERE points IN (:diff)")
//    fun loadAll(diff: IntArray): List<LevelResolvedData>

    @Insert
    fun insertAll(vararg resoledLevel: LevelResolvedData)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addLevelResolved(lvl: LevelResolvedData)

    @Delete
    fun delete(level: LevelResolvedData)

    @Update
    fun updateLevelResolved(vararg levels: LevelResolvedData)

}
