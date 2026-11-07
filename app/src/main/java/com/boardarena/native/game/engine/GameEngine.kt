package com.boardarena.native.game.engine

import com.boardarena.native.game.model.GameType
import com.boardarena.native.game.model.Match
import com.boardarena.native.game.model.Player

interface GameEngine {
    val gameType: GameType

    fun createInitialState(players: List<Player>): GameState
    fun validateMove(state: GameState, playerId: String, move: Move): ValidationResult
    fun applyMove(state: GameState, playerId: String, move: Move): GameState
    fun checkGameEnd(state: GameState): GameResult?
    fun getLegalMoves(state: GameState, playerId: String): List<Move>
    fun getHint(state: GameState, playerId: String): Move?
}

sealed interface GameState {
    data class ChessState(
        val board: Array<Array<ChessPiece?>>,
        val currentTurn: PieceColor,
        val castlingRights: CastlingRights,
        val enPassantTarget: Position?,
        val halfMoveClock: Int,
        val fullMoveNumber: Int,
        val moveHistory: List<ChessMove>
    ) : GameState

    data class LudoState(
        val playerTokens: Map<String, List<LudoToken>>,
        val currentPlayerId: String,
        val diceValue: Int?,
        val diceRolled: Boolean
    ) : GameState

    data class CheckersState(
        val board: Array<Array<CheckersPiece?>>,
        val currentTurn: PieceColor,
        val forcedCapture: Position?
    ) : GameState

    data class TicTacToeState(
        val board: Array<Array<CellValue?>>,
        val currentTurn: CellValue,
        val winningLine: List<Position>?
    ) : GameState

    data class ConnectFourState(
        val board: Array<Array<CellValue?>>,
        val currentTurn: CellValue,
        val winningLine: List<Position>?
    ) : GameState

    data class ConnectSixState(
        val board: Array<Array<CellValue?>>,
        val currentTurn: CellValue,
        val winningLine: List<Position>?
    ) : GameState
}

sealed interface Move {
    data class ChessMove(
        val from: Position,
        val to: Position,
        val promotion: ChessPieceType? = null
    ) : Move

    data class LudoMove(
        val tokenIndex: Int,
        val steps: Int
    ) : Move

    data class CheckersMove(
        val from: Position,
        val to: Position,
        val captured: List<Position>
    ) : Move

    data class TicTacToeMove(
        val position: Position
    ) : Move

    data class ConnectFourMove(
        val column: Int
    ) : Move

    data class ConnectSixMove(
        val position: Position
    ) : Move
}

data class ValidationResult(
    val isValid: Boolean,
    val errorMessage: String? = null
)

data class GameResult(
    val winnerId: String?,
    val isDraw: Boolean,
    val reason: String
)

data class Position(val row: Int, val col: Int)

enum class PieceColor { WHITE, BLACK, RED, BLUE, GREEN, YELLOW }
enum class ChessPieceType { PAWN, KNIGHT, BISHOP, ROOK, QUEEN, KING }
enum class CellValue { EMPTY, X, O, RED, BLUE, GREEN, YELLOW }

data class ChessPiece(val type: ChessPieceType, val color: PieceColor)

data class CastlingRights(
    val whiteKingside: Boolean = true,
    val whiteQueenside: Boolean = true,
    val blackKingside: Boolean = true,
    val blackQueenside: Boolean = true
)

data class LudoToken(
    val id: Int,
    val position: LudoPosition,
    val isHome: Boolean = true,
    val isFinished: Boolean = false
)

enum class LudoPosition {
    HOME, START, BOARD, FINISH
}

data class CheckersPiece(
    val color: PieceColor,
    val isKing: Boolean = false
)