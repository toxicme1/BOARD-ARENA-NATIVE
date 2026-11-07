package com.boardarena.native.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.boardarena.native.game.model.Match
import com.boardarena.native.game.model.MatchStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface MatchDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(match: Match)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(matches: List<Match>)

    @Update
    suspend fun update(match: Match)

    @Query("SELECT * FROM matches WHERE id = :id")
    fun getById(id: String): Flow<Match?>

    @Query("SELECT * FROM matches WHERE :playerId IN playerIds ORDER BY updatedAt DESC")
    fun getByPlayerId(playerId: String): Flow<List<Match>>

    @Query("SELECT * FROM matches WHERE status = :status ORDER BY updatedAt DESC")
    fun getByStatus(status: MatchStatus): Flow<List<Match>>

    @Query("SELECT * FROM matches ORDER BY updatedAt DESC LIMIT :limit")
    fun getRecent(limit: Int): Flow<List<Match>>

    @Query("DELETE FROM matches WHERE id = :id")
    suspend fun deleteById(id: String)

    @Query("DELETE FROM matches")
    suspend fun deleteAll()
}