package com.mobilegame.robozzle.data.room.levelWins

import androidx.room.*

@Dao
interface LevelWinDao {
    @Query("SELECT * FROM level_win_table")
    fun getAll(): List<LevelWinData>


//    @Query("SELECT * FROM player_ranks_table WHERE id_level = :idLevel")
    @Query("SELECT * FROM level_win_table WHERE levelId = :idLevel")
    fun getLevelLevelWinData(idLevel: Int): LevelWinData?

    @Query("SELECT points FROM level_win_table WHERE levelId = :idLevel")
    fun getPointsForId(idLevel: Int): Int?


    @Query("SELECT levelId FROM level_win_table")
    fun getIds(): List<Int>
//    @Query("SELECT * FROM player_ranks_table WHERE points IN (:diff)")
//    fun loadAll(diff: IntArray): List<LevelResolvedData>

    @Insert
    fun insertAll(vararg resoledLevel: LevelWinData)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addLevelWinData(lvl: LevelWinData)

    @Query("DELETE FROM level_win_table")
    fun deleteAll()

    @Delete
    fun delete(level: LevelWinData)


    @Update
    fun updateLevelWinData(vararg levels: LevelWinData)

}
