package com.mobilegame.robozzle.data.base.PlayerRanks

import androidx.room.*

@Dao
interface PlayerRanksDao {
    @Query("SELECT * FROM player_ranks_table")
    fun getAll(): List<LevelResolvedData>

//    @Query("SELECT * FROM player_ranks_table WHERE id IN (:id)")
//    fun load(id: IntArray): List<LevelResolvedData>

    @Query("SELECT * FROM player_ranks_table WHERE id = :id")
    fun getLevelResolved(id: Int): LevelResolvedData?

    @Query("SELECT * FROM player_ranks_table WHERE difficulty IN (:diff)")
    fun loadAllByDifficutly(diff: IntArray): List<LevelResolvedData>

    @Insert
    fun insertAll(vararg resoledLevel: LevelResolvedData)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addLevelResolved(lvl: LevelResolvedData)

    @Delete
    fun delete(level: LevelResolvedData)

    @Update
    fun updateLevelResolved(vararg levels: LevelResolvedData)

}
