package com.boardarena.native.presentation.ui.screens.game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.boardarena.native.game.engine.GameState
import com.boardarena.native.game.engine.Move
import com.boardarena.native.game.engine.Position
import com.boardarena.native.game.model.GameType

@Composable
fun GameScreen(
    matchId: String,
    onNavigate: (String) -> Unit
) {
    var gameState by remember { mutableStateOf<GameState?>(null) }
    var currentPlayer by remember { mutableStateOf("player1") }
    var isMyTurn by remember { mutableStateOf(true) }

    // TODO: Observe match from repository
    // TODO: Connect to socket for real-time updates

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(text = "Match $matchId", fontWeight = FontWeight.Bold) },
            colors = androidx.compose.material3.TopAppBarDefaults.topAppBarColors(containerColor = androidx.compose.material3.MaterialTheme.colorScheme.primaryContainer),
            navigationIcon = { Icon(imageVector = androidx.compose.material.icons.filled.ArrowBack, contentDescription = "Back", onClick = { onNavigate("main") }) }
        )

        Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            // Game board rendering based on game type
            when (val state = gameState) {
                is GameState.ChessState -> ChessBoard(state = state, onMove = { /* TODO */ })
                is GameState.LudoState -> LudoBoard(state = state, onMove = { /* TODO */ })
                is GameState.CheckersState -> CheckersBoard(state = state, onMove = { /* TODO */ })
                is GameState.TicTacToeState -> TicTacToeBoard(state = state, onMove = { /* TODO */ })
                is GameState.ConnectFourState -> ConnectFourBoard(state = state, onMove = { /* TODO */ })
                is GameState.ConnectSixState -> ConnectSixBoard(state = state, onMove = { /* TODO */ })
                else -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Loading game...", fontSize = 18.sp)
                    }
                }
            }
        }

        // Turn indicator
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = androidx.compose.material3.CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isMyTurn) "Your Turn" : "Opponent's Turn",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isMyTurn) androidx.compose.material3.MaterialTheme.colorScheme.primary else androidx.compose.material3.MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
                if (isMyTurn) {
                    Button(onClick = { /* TODO: Offer draw */ }) {
                        Text("Offer Draw")
                    }
                }
            }
        }
    }
}

@Composable
fun ChessBoard(state: GameState.ChessState, onMove: (Move.ChessMove) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Render 8x8 board
        androidx.compose.foundation.layout.Box(
            modifier = Modifier
                .size(320.dp)
                .background(Color(0xFF8B4513))
        ) {
            // TODO: Render chess pieces
            Text(text = "Chess Board - ${state.currentTurn}", fontSize = 18.sp, color = Color.White)
        }
    }
}

@Composable
fun LudoBoard(state: GameState.LudoState, onMove: (Move.LudoMove) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        androidx.compose.foundation.layout.Box(
            modifier = Modifier
                .size(320.dp)
                .background(Color(0xFF2E8B57))
        ) {
            Text(text = "LUDO Board - ${state.currentPlayerId}", fontSize = 18.sp, color = Color.White)
        }
    }
}

@Composable
fun CheckersBoard(state: GameState.CheckersState, onMove: (Move.CheckersMove) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        androidx.compose.foundation.layout.Box(
            modifier = Modifier
                .size(320.dp)
                .background(Color(0xFF8B4513))
        ) {
            Text(text = "Checkers Board - ${state.currentTurn}", fontSize = 18.sp, color = Color.White)
        }
    }
}

@Composable
fun TicTacToeBoard(state: GameState.TicTacToeState, onMove: (Move.TicTacToeMove) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        androidx.compose.foundation.layout.Box(
            modifier = Modifier
                .size(320.dp)
                .background(Color.White)
        ) {
            // 3x3 grid
            androidx.compose.foundation.layout.Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                (0..2).forEach { row ->
                    androidx.compose.foundation.layout.Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        (0..2).forEach { col ->
                            val value = state.board[row][col]
                            androidx.compose.material3.Box(
                                modifier = Modifier
                                    .size(100.dp)
                                    .border(2.dp, Color.Black)
                                    .fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = when (value) {
                                        com.boardarena.native.game.engine.CellValue.X -> "X"
                                        com.boardarena.native.game.engine.CellValue.O -> "O"
                                        else -> ""
                                    },
                                    fontSize = 48.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (value == com.boardarena.native.game.engine.CellValue.X) Color.Red else Color.Blue
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ConnectFourBoard(state: GameState.ConnectFourState, onMove: (Move.ConnectFourMove) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        androidx.compose.foundation.layout.Box(
            modifier = Modifier
                .size(320.dp)
                .background(Color(0xFF1E90FF))
        ) {
            // 6x7 grid
            Text(text = "Connect Four - ${state.currentTurn}", fontSize = 18.sp, color = Color.White)
        }
    }
}

@Composable
fun ConnectSixBoard(state: GameState.ConnectSixState, onMove: (Move.ConnectSixMove) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        androidx.compose.foundation.layout.Box(
            modifier = Modifier
                .size(320.dp)
                .background(Color(0xFF1E90FF))
        ) {
            // 15x15 grid
            Text(text = "Connect Six - ${state.currentTurn}", fontSize = 18.sp, color = Color.White)
        }
    }
}