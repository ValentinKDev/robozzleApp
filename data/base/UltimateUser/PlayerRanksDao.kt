package com.mobilegame.robozzle.data.base.UltimateUser

import androidx.room.*

//todo add an overAll rank of players
@Dao
interface PlayerRanksDao {
    @Query("SELECT * FROM level_resolved_table")
    fun getAllranks(): List<LevelResolved>

    @Query("SELECT * FROM user_table WHERE id = :levelId")
    fun getLevelResolved(levelId: Int): LevelResolved

    @Insert
    fun insertAll(vararg user: User)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addLevelResolved(lvlResolved: LevelResolved)

    @Delete
    fun delete(lvlResolved: LevelResolved)
}
