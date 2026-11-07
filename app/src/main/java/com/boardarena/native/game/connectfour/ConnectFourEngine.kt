package com.boardarena.native.game.connectfour

import com.boardarena.native.game.engine.GameEngine
import com.boardarena.native.game.engine.GameState
import com.boardarena.native.game.engine.Move
import com.boardarena.native.game.engine.ValidationResult
import com.boardarena.native.game.model.GameType
import com.boardarena.native.game.model.Player
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConnectFourEngine @Inject constructor() : GameEngine {
    override val gameType = GameType.CONNECT_FOUR

    override fun createInitialState(players: List<Player>): GameState.ConnectFourState {
        val board = Array(6) { Array(7) { com.boardarena.native.game.engine.CellValue.EMPTY } }
        return GameState.ConnectFourState(
            board = board,
            currentTurn = com.boardarena.native.game.engine.CellValue.RED,
            winningLine = null
        )
    }

    override fun validateMove(state: GameState, playerId: String, move: Move): ValidationResult {
        return if (move is Move.ConnectFourMove) {
            val connectFourState = state as GameState.ConnectFourState
            if (move.column in 0..6 && connectFourState.board[0][move.column] == com.boardarena.native.game.engine.CellValue.EMPTY) {
                ValidationResult(true)
            } else {
                ValidationResult(false, "Invalid column or column full")
            }
        } else {
            ValidationResult(false, "Invalid move type for Connect Four")
        }
    }

    override fun applyMove(state: GameState, playerId: String, move: Move): GameState {
        val connectFourState = state as GameState.ConnectFourState
        val moveData = move as Move.ConnectFourMove
        val newBoard = connectFourState.board.map { it.copyOf() }.toTypedArray()
        // Find lowest empty row in column
        var row = 5
        while (row >= 0 && newBoard[row][moveData.column] != com.boardarena.native.game.engine.CellValue.EMPTY) {
            row--
        }
        if (row >= 0) {
            newBoard[row][moveData.column] = connectFourState.currentTurn
        }
        val nextTurn = if (connectFourState.currentTurn == com.boardarena.native.game.engine.CellValue.RED)
            com.boardarena.native.game.engine.CellValue.BLUE
        else
            com.boardarena.native.game.engine.CellValue.RED
        val winningLine = checkWin(newBoard, row, moveData.column)
        return GameState.ConnectFourState(
            board = newBoard,
            currentTurn = nextTurn,
            winningLine = winningLine
        )
    }

    private fun checkWin(board: Array<Array<com.boardarena.native.game.engine.CellValue>>, row: Int, col: Int): List<com.boardarena.native.game.engine.Position>? {
        val value = board[row][col]
        val directions = listOf(
            Pair(0, 1),   // horizontal
            Pair(1, 0),   // vertical
            Pair(1, 1),   // diagonal down-right
            Pair(1, -1)   // diagonal down-left
        )
        for ((dr, dc) in directions) {
            var count = 1
            var positions = mutableListOf(com.boardarena.native.game.engine.Position(row, col))
            // Check forward
            var r = row + dr
            var c = col + dc
            while (r in 0..5 && c in 0..6 && board[r][c] == value) {
                count++
                positions.add(com.boardarena.native.game.engine.Position(r, c))
                r += dr
                c += dc
            }
            // Check backward
            r = row - dr
            c = col - dc
            while (r in 0..5 && c in 0..6 && board[r][c] == value) {
                count++
                positions.add(0, com.boardarena.native.game.engine.Position(r, c))
                r -= dr
                c -= dc
            }
            if (count >= 4) return positions
        }
        return null
    }

    override fun checkGameEnd(state: GameState): com.boardarena.native.game.engine.GameResult? {
        val connectFourState = state as GameState.ConnectFourState
        if (connectFourState.winningLine != null) {
            val winnerValue = connectFourState.board[connectFourState.winningLine!![0].row][connectFourState.winningLine!![0].col]
            return com.boardarena.native.game.engine.GameResult(
                winnerId = if (winnerValue == com.boardarena.native.game.engine.CellValue.RED) "player1" else "player2",
                isDraw = false,
                reason = "Win"
            )
        }
        if (connectFourState.board[0].all { it != com.boardarena.native.game.engine.CellValue.EMPTY }) {
            return com.boardarena.native.game.engine.GameResult(
                winnerId = null,
                isDraw = true,
                reason = "Draw"
            )
        }
        return null
    }

    override fun getLegalMoves(state: GameState, playerId: String): List<Move> {
        val connectFourState = state as GameState.ConnectFourState
        return (0..6).filter { connectFourState.board[0][it] == com.boardarena.native.game.engine.CellValue.EMPTY }
            .map { Move.ConnectFourMove(it) }
    }

    override fun getHint(state: GameState, playerId: String): Move? {
        return getLegalMoves(state, playerId).firstOrNull()
    }
}