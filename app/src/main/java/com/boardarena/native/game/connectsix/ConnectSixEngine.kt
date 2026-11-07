package com.boardarena.native.game.connectsix

import com.boardarena.native.game.engine.GameEngine
import com.boardarena.native.game.engine.GameState
import com.boardarena.native.game.engine.Move
import com.boardarena.native.game.engine.Position
import com.boardarena.native.game.engine.ValidationResult
import com.boardarena.native.game.model.GameType
import com.boardarena.native.game.model.Player
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConnectSixEngine @Inject constructor() : GameEngine {
    override val gameType = GameType.CONNECT_SIX

    override fun createInitialState(players: List<Player>): GameState.ConnectSixState {
        val board = Array(15) { Array(15) { com.boardarena.native.game.engine.CellValue.EMPTY } }
        return GameState.ConnectSixState(
            board = board,
            currentTurn = com.boardarena.native.game.engine.CellValue.RED,
            winningLine = null
        )
    }

    override fun validateMove(state: GameState, playerId: String, move: Move): ValidationResult {
        return if (move is Move.ConnectSixMove) {
            val connectSixState = state as GameState.ConnectSixState
            if (move.position.row in 0..14 && move.position.col in 0..14 &&
                connectSixState.board[move.position.row][move.position.col] == com.boardarena.native.game.engine.CellValue.EMPTY) {
                ValidationResult(true)
            } else {
                ValidationResult(false, "Invalid position or cell occupied")
            }
        } else {
            ValidationResult(false, "Invalid move type for Connect Six")
        }
    }

    override fun applyMove(state: GameState, playerId: String, move: Move): GameState {
        val connectSixState = state as GameState.ConnectSixState
        val moveData = move as Move.ConnectSixMove
        val newBoard = connectSixState.board.map { it.copyOf() }.toTypedArray()
        newBoard[moveData.position.row][moveData.position.col] = connectSixState.currentTurn
        val nextTurn = if (connectSixState.currentTurn == com.boardarena.native.game.engine.CellValue.RED)
            com.boardarena.native.game.engine.CellValue.BLUE
        else
            com.boardarena.native.game.engine.CellValue.RED
        val winningLine = checkWin(newBoard, moveData.position)
        return GameState.ConnectSixState(
            board = newBoard,
            currentTurn = nextTurn,
            winningLine = winningLine
        )
    }

    private fun checkWin(board: Array<Array<com.boardarena.native.game.engine.CellValue>>, lastMove: Position): List<com.boardarena.native.game.engine.Position>? {
        val value = board[lastMove.row][lastMove.col]
        val directions = listOf(
            Pair(0, 1),   // horizontal
            Pair(1, 0),   // vertical
            Pair(1, 1),   // diagonal down-right
            Pair(1, -1)   // diagonal down-left
        )
        for ((dr, dc) in directions) {
            var count = 1
            var positions = mutableListOf(com.boardarena.native.game.engine.Position(lastMove.row, lastMove.col))
            // Check forward
            var r = lastMove.row + dr
            var c = lastMove.col + dc
            while (r in 0..14 && c in 0..14 && board[r][c] == value) {
                count++
                positions.add(com.boardarena.native.game.engine.Position(r, c))
                r += dr
                c += dc
            }
            // Check backward
            r = lastMove.row - dr
            c = lastMove.col - dc
            while (r in 0..14 && c in 0..14 && board[r][c] == value) {
                count++
                positions.add(0, com.boardarena.native.game.engine.Position(r, c))
                r -= dr
                c -= dc
            }
            if (count >= 6) return positions
        }
        return null
    }

    override fun checkGameEnd(state: GameState): com.boardarena.native.game.engine.GameResult? {
        val connectSixState = state as GameState.ConnectSixState
        if (connectSixState.winningLine != null) {
            val winnerValue = connectSixState.board[connectSixState.winningLine!![0].row][connectSixState.winningLine!![0].col]
            return com.boardarena.native.game.engine.GameResult(
                winnerId = if (winnerValue == com.boardarena.native.game.engine.CellValue.RED) "player1" else "player2",
                isDraw = false,
                reason = "Win"
            )
        }
        if (connectSixState.board.flatten().none { it == com.boardarena.native.game.engine.CellValue.EMPTY }) {
            return com.boardarena.native.game.engine.GameResult(
                winnerId = null,
                isDraw = true,
                reason = "Draw"
            )
        }
        return null
    }

    override fun getLegalMoves(state: GameState, playerId: String): List<Move> {
        val connectSixState = state as GameState.ConnectSixState
        val moves = mutableListOf<Move>()
        for (row in 0..14) {
            for (col in 0..14) {
                if (connectSixState.board[row][col] == com.boardarena.native.game.engine.CellValue.EMPTY) {
                    moves.add(Move.ConnectSixMove(com.boardarena.native.game.engine.Position(row, col)))
                }
            }
        }
        return moves
    }

    override fun getHint(state: GameState, playerId: String): Move? {
        return getLegalMoves(state, playerId).firstOrNull()
    }
}