package com.boardarena.native.game.model

enum class GameType(val id: String, val displayName: String, val minPlayers: Int, val maxPlayers: Int) {
    CHESS("chess", "Chess", 2, 2),
    LUDO("ludo", "LUDO", 2, 4),
    CHECKERS("checkers", "Checkers", 2, 2),
    TIC_TAC_TOE("tic_tac_toe", "Tic-Tac-Toe", 2, 2),
    CONNECT_FOUR("connect_four", "Connect Four", 2, 2),
    CONNECT_SIX("connect_six", "Connect Six", 2, 2)
}