package com.boardarena.native.data.repository

import com.boardarena.native.data.database.MatchDao
import com.boardarena.native.data.database.PlayerDao
import com.boardarena.native.domain.repository.MatchRepository
import com.boardarena.native.domain.repository.Result
import com.boardarena.native.game.engine.GameEngineRegistry
import com.boardarena.native.game.model.GameType
import com.boardarena.native.game.model.Match
import com.boardarena.native.game.model.MatchStatus
import com.boardarena.native.game.model.Player
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MatchRepositoryImpl @Inject constructor(
    private val matchDao: MatchDao,
    private val playerDao: PlayerDao,
    private val gameEngineRegistry: GameEngineRegistry
) : MatchRepository {

    override suspend fun createMatch(gameType: String, playerIds: List<String>): Result<Match> {
        val gameTypeEnum = GameType.values().find { it.id == gameType } ?: return Result.Error(IllegalArgumentException("Unknown game type"))
        val players = playerDao.getByIds(playerIds).first()
        if (players.size != playerIds.size) {
            return Result.Error(IllegalArgumentException("Some players not found"))
        }

        val engine = gameEngineRegistry.getEngine(gameTypeEnum) ?: return Result.Error(IllegalStateException("Engine not found"))
        val initialState = engine.createInitialState(players)
        val gameStateJson = kotlinx.serialization.json.Json.encodeToString(initialState)

        val match = Match(
            id = java.util.UUID.randomUUID().toString(),
            gameType = gameTypeEnum,
            status = MatchStatus.IN_PROGRESS,
            playerIds = playerIds,
            currentTurnPlayerId = playerIds.firstOrNull(),
            gameStateJson = gameStateJson
        )

        matchDao.insert(match)
        return Result.Success(match)
    }

    override suspend fun joinMatch(matchId: String, playerId: String): Result<Match> {
        val match = matchDao.getById(matchId).first() ?: return Result.Error(IllegalArgumentException("Match not found"))
        if (match.status != MatchStatus.WAITING) {
            return Result.Error(IllegalStateException("Match not joinable"))
        }
        if (playerId in match.playerIds) {
            return Result.Error(IllegalStateException("Already in match"))
        }
        if (match.playerIds.size >= match.gameType.maxPlayers) {
            return Result.Error(IllegalStateException("Match full"))
        }

        val updatedMatch = match.copy(
            playerIds = match.playerIds + playerId,
            status = if (match.playerIds.size + 1 >= match.gameType.minPlayers) MatchStatus.IN_PROGRESS else MatchStatus.WAITING
        )
        matchDao.update(updatedMatch)
        return Result.Success(updatedMatch)
    }

    override suspend fun makeMove(matchId: String, playerId: String, moveJson: String): Result<Match> {
        val match = matchDao.getById(matchId).first() ?: return Result.Error(IllegalArgumentException("Match not found"))
        if (match.status != MatchStatus.IN_PROGRESS) {
            return Result.Error(IllegalStateException("Match not in progress"))
        }
        if (match.currentTurnPlayerId != playerId) {
            return Result.Error(IllegalStateException("Not your turn"))
        }

        val engine = gameEngineRegistry.getEngine(match.gameType) ?: return Result.Error(IllegalStateException("Engine not found"))
        val gameState = kotlinx.serialization.json.Json.decodeFromString<com.boardarena.native.game.engine.GameState>(match.gameStateJson!!)
        val move = kotlinx.serialization.json.Json.decodeFromString<com.boardarena.native.game.engine.Move>(moveJson)

        val validation = engine.validateMove(gameState, playerId, move)
        if (!validation.isValid) {
            return Result.Error(IllegalArgumentException(validation.errorMessage))
        }

        val newState = engine.applyMove(gameState, playerId, move)
        val gameResult = engine.checkGameEnd(newState)
        val newStateJson = kotlinx.serialization.json.Json.encodeToString(newState)

        val nextPlayerIndex = (match.playerIds.indexOf(playerId) + 1) % match.playerIds.size
        val nextPlayerId = match.playerIds[nextPlayerIndex]

        val updatedMatch = match.copy(
            currentTurnPlayerId = if (gameResult != null) null else nextPlayerId,
            gameStateJson = newStateJson,
            status = if (gameResult != null) MatchStatus.COMPLETED else MatchStatus.IN_PROGRESS,
            winnerId = gameResult?.winnerId,
            updatedAt = System.currentTimeMillis(),
            completedAt = if (gameResult != null) System.currentTimeMillis() else null
        )

        matchDao.update(updatedMatch)
        return Result.Success(updatedMatch)
    }

    override suspend fun getMatch(matchId: String): Result<Match> {
        val match = matchDao.getById(matchId).first()
        return if (match != null) Result.Success(match) else Result.Error(IllegalArgumentException("Match not found"))
    }

    override fun observeMatch(matchId: String): Flow<Match> {
        return matchDao.getById(matchId).map { it!! }
    }

    override suspend fun getPlayerMatches(playerId: String): Result<List<Match>> {
        val matches = matchDao.getByPlayerId(playerId).first()
        return Result.Success(matches)
    }

    override suspend fun forfeitMatch(matchId: String, playerId: String): Result<Match> {
        val match = matchDao.getById(matchId).first() ?: return Result.Error(IllegalArgumentException("Match not found"))
        if (playerId !in match.playerIds) {
            return Result.Error(IllegalStateException("Not in match"))
        }
        val winnerId = match.playerIds.first { it != playerId }
        val updatedMatch = match.copy(
            status = MatchStatus.COMPLETED,
            winnerId = winnerId,
            currentTurnPlayerId = null,
            updatedAt = System.currentTimeMillis(),
            completedAt = System.currentTimeMillis()
        )
        matchDao.update(updatedMatch)
        return Result.Success(updatedMatch)
    }
}