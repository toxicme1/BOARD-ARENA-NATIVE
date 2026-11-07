package com.boardarena.native.game.tictactoe

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
class TicTacToeEngine @Inject constructor() : GameEngine {
    override val gameType = GameType.TIC_TAC_TOE

    override fun createInitialState(players: List<Player>): GameState.TicTacToeState {
        val board = Array(3) { Array(3) { com.boardarena.native.game.engine.CellValue.EMPTY } }
        return GameState.TicTacToeState(
            board = board,
            currentTurn = com.boardarena.native.game.engine.CellValue.X,
            winningLine = null
        )
    }

    override fun validateMove(state: GameState, playerId: String, move: Move): ValidationResult {
        return if (move is Move.TicTacToeMove) {
            val ticTacToeState = state as GameState.TicTacToeState
            if (ticTacToeState.board[move.position.row][move.position.col] == com.boardarena.native.game.engine.CellValue.EMPTY) {
                ValidationResult(true)
            } else {
                ValidationResult(false, "Cell already occupied")
            }
        } else {
            ValidationResult(false, "Invalid move type for Tic-Tac-Toe")
        }
    }

    override fun applyMove(state: GameState, playerId: String, move: Move): GameState {
        val ticTacToeState = state as GameState.TicTacToeState
        val moveData = move as Move.TicTacToeMove
        val newBoard = ticTacToeState.board.map { it.copyOf() }.toTypedArray()
        newBoard[moveData.position.row][moveData.position.col] = ticTacToeState.currentTurn
        val nextTurn = if (ticTacToeState.currentTurn == com.boardarena.native.game.engine.CellValue.X)
            com.boardarena.native.game.engine.CellValue.O
        else
            com.boardarena.native.game.engine.CellValue.X
        val winningLine = checkWin(newBoard, moveData.position)
        return GameState.TicTacToeState(
            board = newBoard,
            currentTurn = nextTurn,
            winningLine = winningLine
        )
    }

    private fun checkWin(board: Array<Array<com.boardarena.native.game.engine.CellValue>>, lastMove: Position): List<com.boardarena.native.game.engine.Position>? {
        val value = board[lastMove.row][lastMove.col]
        // Check row
        if (board[lastMove.row].all { it == value }) {
            return (0..2).map { com.boardarena.native.game.engine.Position(lastMove.row, it) }
        }
        // Check column
        if ((0..2).all { board[it][lastMove.col] == value }) {
            return (0..2).map { com.boardarena.native.game.engine.Position(it, lastMove.col) }
        }
        // Check diagonals
        if (lastMove.row == lastMove.col && (0..2).all { board[it][it] == value }) {
            return (0..2).map { com.boardarena.native.game.engine.Position(it, it) }
        }
        if (lastMove.row + lastMove.col == 2 && (0..2).all { board[it][2 - it] == value }) {
            return (0..2).map { com.boardarena.native.game.engine.Position(it, 2 - it) }
        }
        return null
    }

    override fun checkGameEnd(state: GameState): com.boardarena.native.game.engine.GameResult? {
        val ticTacToeState = state as GameState.TicTacToeState
        if (ticTacToeState.winningLine != null) {
            val winnerValue = ticTacToeState.board[ticTacToeState.winningLine!![0].row][ticTacToeState.winningLine!![0].col]
            return com.boardarena.native.game.engine.GameResult(
                winnerId = if (winnerValue == com.boardarena.native.game.engine.CellValue.X) "player1" else "player2",
                isDraw = false,
                reason = "Win"
            )
        }
        if (ticTacToeState.board.flatten().none { it == com.boardarena.native.game.engine.CellValue.EMPTY }) {
            return com.boardarena.native.game.engine.GameResult(
                winnerId = null,
                isDraw = true,
                reason = "Draw"
            )
        }
        return null
    }

    override fun getLegalMoves(state: GameState, playerId: String): List<Move> {
        val ticTacToeState = state as GameState.TicTacToeState
        return ticTacToeState.board.flatMapIndexed { row, rowArray ->
            rowArray.mapIndexed { col, cell ->
                if (cell == com.boardarena.native.game.engine.CellValue.EMPTY)
                    Move.TicTacToeMove(com.boardarena.native.game.engine.Position(row, col))
                else
                    null
            }
        }.filterNotNull()
    }

    override fun getHint(state: GameState, playerId: String): Move? {
        return getLegalMoves(state, playerId).firstOrNull()
    }
}