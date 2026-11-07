package com.boardarena.native.game.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "players")
@Serializable
data class Player(
    @PrimaryKey val id: String,
    val username: String,
    val email: String,
    val avatarUrl: String? = null,
    val rating: Int = 1000,
    val gamesPlayed: Int = 0,
    val gamesWon: Int = 0,
    val gamesLost: Int = 0,
    val gamesDrawn: Int = 0,
    val createdAt: Long = System.currentTimeMillis(),
    val lastActiveAt: Long = System.currentTimeMillis()
) {
    val winRate: Double get() = if (gamesPlayed > 0) gamesWon.toDouble() / gamesPlayed else 0.0
}