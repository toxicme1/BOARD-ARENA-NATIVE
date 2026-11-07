package com.boardarena.native.game.checkers

import com.boardarena.native.game.engine.GameEngine
import com.boardarena.native.game.engine.GameState
import com.boardarena.native.game.engine.Move
import com.boardarena.native.game.engine.ValidationResult
import com.boardarena.native.game.model.GameType
import com.boardarena.native.game.model.Player
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CheckersEngine @Inject constructor() : GameEngine {
    override val gameType = GameType.CHECKERS

    override fun createInitialState(players: List<Player>): GameState.CheckersState {
        val board = Array(8) { row ->
            Array(8) { col -> null }
        }
        // Set up initial pieces (rows 0-2 for black, 5-7 for white)
        for (row in 0..2) {
            for (col in 0..7) {
                if ((row + col) % 2 == 1) {
                    board[row][col] = com.boardarena.native.game.engine.CheckersPiece(
                        color = com.boardarena.native.game.engine.PieceColor.BLACK
                    )
                }
            }
        }
        for (row in 5..7) {
            for (col in 0..7) {
                if ((row + col) % 2 == 1) {
                    board[row][col] = com.boardarena.native.game.engine.CheckersPiece(
                        color = com.boardarena.native.game.engine.PieceColor.WHITE
                    )
                }
            }
        }
        return GameState.CheckersState(
            board = board,
            currentTurn = com.boardarena.native.game.engine.PieceColor.WHITE,
            forcedCapture = null
        )
    }

    override fun validateMove(state: GameState, playerId: String, move: Move): ValidationResult {
        return if (move is Move.CheckersMove) {
            ValidationResult(true)
        } else {
            ValidationResult(false, "Invalid move type for Checkers")
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