package com.boardarena.native.data.repository

import com.boardarena.native.data.database.PlayerDao
import com.boardarena.native.domain.repository.ProfileRepository
import com.boardarena.native.domain.repository.PlayerStats
import com.boardarena.native.domain.repository.Result
import com.boardarena.native.game.model.Match
import com.boardarena.native.game.model.MatchStatus
import com.boardarena.native.game.model.Player
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepositoryImpl @Inject constructor(
    private val playerDao: PlayerDao,
    private val matchDao: MatchDao
) : ProfileRepository {

    override suspend fun getProfile(playerId: String): Result<Player> {
        val player = playerDao.getById(playerId).first()
        return if (player != null) Result.Success(player) else Result.Error(IllegalArgumentException("Player not found"))
    }

    override suspend fun updateProfile(player: Player): Result<Player> {
        playerDao.update(player)
        return Result.Success(player)
    }

    override suspend fun updateAvatar(playerId: String, avatarUrl: String): Result<Player> {
        val player = playerDao.getById(playerId).first() ?: return Result.Error(IllegalArgumentException("Player not found"))
        val updated = player.copy(avatarUrl = avatarUrl)
        playerDao.update(updated)
        return Result.Success(updated)
    }

    override suspend fun getLeaderboard(limit: Int): Result<List<Player>> {
        val players = playerDao.getAllOrderedByRating().first().take(limit)
        return Result.Success(players)
    }

    override suspend fun getPlayerStats(playerId: String): Result<PlayerStats> {
        val player = playerDao.getById(playerId).first() ?: return Result.Error(IllegalArgumentException("Player not found"))
        val matches = matchDao.getByPlayerId(playerId).first()
        val completed = matches.filter { it.status == MatchStatus.COMPLETED }
        val wins = completed.count { it.winnerId == playerId }
        val losses = completed.count { it.winnerId != null && it.winnerId != playerId }
        val draws = completed.count { it.winnerId == null }
        val total = completed.size
        val winRate = if (total > 0) wins.toDouble() / total else 0.0
        val rank = calculateRank(player.rating)

        return Result.Success(PlayerStats(
            totalGames = total,
            wins = wins,
            losses = losses,
            draws = draws,
            winRate = winRate,
            rating = player.rating,
            rank = rank
        ))
    }

    private fun calculateRank(rating: Int): Int {
        // Simplified - in production, query actual rank from leaderboard
        return maxOf(1, 10000 - rating / 10)
    }
}