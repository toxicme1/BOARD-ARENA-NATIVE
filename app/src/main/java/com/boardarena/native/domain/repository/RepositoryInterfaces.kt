package com.boardarena.native.domain.repository

import com.boardarena.native.game.model.Match
import com.boardarena.native.game.model.Player
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<Player>
    suspend fun register(username: String, email: String, password: String): Result<Player>
    suspend fun logout()
    fun getCurrentUser(): Flow<Player?>
    suspend fun refreshToken(): Result<String>
}

interface GameRepository {
    suspend fun getAvailableGames(): Result<List<GameTypeInfo>>
    suspend fun getGameRules(gameType: String): Result<String>
}

data class GameTypeInfo(
    val id: String,
    val name: String,
    val minPlayers: Int,
    val maxPlayers: Int,
    val description: String
)

interface MatchRepository {
    suspend fun createMatch(gameType: String, playerIds: List<String>): Result<Match>
    suspend fun joinMatch(matchId: String, playerId: String): Result<Match>
    suspend fun makeMove(matchId: String, playerId: String, moveJson: String): Result<Match>
    suspend fun getMatch(matchId: String): Result<Match>
    fun observeMatch(matchId: String): Flow<Match>
    suspend fun getPlayerMatches(playerId: String): Result<List<Match>>
    suspend fun forfeitMatch(matchId: String, playerId: String): Result<Match>
}

interface ProfileRepository {
    suspend fun getProfile(playerId: String): Result<Player>
    suspend fun updateProfile(player: Player): Result<Player>
    suspend fun updateAvatar(playerId: String, avatarUrl: String): Result<Player>
    suspend fun getLeaderboard(limit: Int): Result<List<Player>>
    suspend fun getPlayerStats(playerId: String): Result<PlayerStats>
}

data class PlayerStats(
    val totalGames: Int,
    val wins: Int,
    val losses: Int,
    val draws: Int,
    val winRate: Double,
    val rating: Int,
    val rank: Int
)

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Throwable) : Result<Nothing>()
}