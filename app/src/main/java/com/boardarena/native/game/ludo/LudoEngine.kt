package com.boardarena.native.game.ludo

import com.boardarena.native.game.engine.GameEngine
import com.boardarena.native.game.engine.GameState
import com.boardarena.native.game.engine.Move
import com.boardarena.native.game.engine.ValidationResult
import com.boardarena.native.game.model.GameType
import com.boardarena.native.game.model.Player
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LudoEngine @Inject constructor() : GameEngine {
    override val gameType = GameType.LUDO

    override fun createInitialState(players: List<Player>): GameState.LudoState {
        val playerTokens = players.associateWith { player ->
            (0..3).map { index ->
                com.boardarena.native.game.engine.LudoToken(
                    id = index,
                    position = com.boardarena.native.game.engine.LudoPosition.HOME
                )
            }
        }
        return GameState.LudoState(
            playerTokens = playerTokens,
            currentPlayerId = players.first().id,
            diceValue = null,
            diceRolled = false
        )
    }

    override fun validateMove(state: GameState, playerId: String, move: Move): ValidationResult {
        return if (move is Move.LudoMove) {
            ValidationResult(true)
        } else {
            ValidationResult(false, "Invalid move type for LUDO")
        }
    }

    override fun applyMove(state: GameState, playerId: String, move: Move): GameState {
        return state
    }

    override fun checkGameEnd(state: GameState): com.boardarena.native.game.engine.GameResult? {
        return null
    }

    override fun getLegalMoves(state: GameState, playerId: String): List<Move> {
        return emptyList()
    }

    override fun getHint(state: GameState, playerId: String): Move? {
        return null
    }
}