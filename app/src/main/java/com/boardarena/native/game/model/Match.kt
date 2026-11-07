package com.boardarena.native.game.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "matches")
@Serializable
data class Match(
    @PrimaryKey val id: String,
    val gameType: GameType,
    val status: MatchStatus,
    val playerIds: List<String>,
    val currentTurnPlayerId: String? = null,
    val winnerId: String? = null,
    val gameStateJson: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    val completedAt: Long? = null
)

enum class MatchStatus {
    WAITING, IN_PROGRESS, COMPLETED, ABANDONED, CANCELLED
}