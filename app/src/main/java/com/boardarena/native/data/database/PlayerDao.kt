package com.boardarena.native.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.boardarena.native.game.model.Player
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(player: Player)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(players: List<Player>)

    @Update
    suspend fun update(player: Player)

    @Query("SELECT * FROM players WHERE id = :id")
    fun getById(id: String): Flow<Player?>

    @Query("SELECT * FROM players ORDER BY rating DESC")
    fun getAllOrderedByRating(): Flow<List<Player>>

    @Query("SELECT * FROM players WHERE id IN (:ids)")
    fun getByIds(ids: List<String>): Flow<List<Player>>

    @Query("DELETE FROM players WHERE id = :id")
    suspend fun deleteById(id: String)

    @Query("DELETE FROM players")
    suspend fun deleteAll()
}