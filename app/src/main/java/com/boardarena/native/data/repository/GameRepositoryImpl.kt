package com.boardarena.native.data.repository

import com.boardarena.native.domain.repository.GameRepository
import com.boardarena.native.domain.repository.GameTypeInfo
import com.boardarena.native.domain.repository.Result
import com.boardarena.native.game.model.GameType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameRepositoryImpl @Inject constructor() : GameRepository {

    override suspend fun getAvailableGames(): Result<List<GameTypeInfo>> {
        val games = GameType.values().map { type ->
            GameTypeInfo(
                id = type.id,
                name = type.displayName,
                minPlayers = type.minPlayers,
                maxPlayers = type.maxPlayers,
                description = getDescription(type)
            )
        }
        return Result.Success(games)
    }

    override suspend fun getGameRules(gameType: String): Result<String> {
        val rules = when (gameType) {
            "chess" -> "Chess rules..."
            "ludo" -> "LUDO rules..."
            "checkers" -> "Checkers rules..."
            "tic_tac_toe" -> "Tic-Tac-Toe rules..."
            "connect_four" -> "Connect Four rules..."
            "connect_six" -> "Connect Six rules..."
            else -> "Unknown game"
        }
        return Result.Success(rules)
    }

    private fun getDescription(type: GameType): String = when (type) {
        GameType.CHESS -> "Classic 2-player strategy game"
        GameType.LUDO -> "Race your 4 tokens around the board"
        GameType.CHECKERS -> "Capture all opponent pieces"
        GameType.TIC_TAC_TOE -> "Get 3 in a row"
        GameType.CONNECT_FOUR -> "Connect 4 discs vertically, horizontally, or diagonally"
        GameType.CONNECT_SIX -> "Connect 6 pieces on a 15x15 board"
    }
}