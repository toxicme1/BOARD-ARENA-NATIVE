package com.boardarena.native.presentation.ui.screens.leaderboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.boardarena.native.game.model.Player
import com.boardarena.native.presentation.ui.components.PlayerAvatar

@Composable
fun LeaderboardScreen(onNavigate: (String) -> Unit) {
    // Mock data
    val players = listOf(
        Player("1", "MagnusCarlsen", "magnus@chess.com", null, 2850, 500, 450, 30, 20),
        Player("2", "HikaruNakamura", "hikaru@chess.com", null, 2800, 480, 420, 40, 20),
        Player("3", "FabianoCaruana", "fabiano@chess.com", null, 2780, 460, 400, 45, 15),
        Player("4", "DingLiren", "ding@chess.com", null, 2770, 450, 390, 50, 10),
        Player("5", "IanNepomniachtchi", "ian@chess.com", null, 2760, 440, 380, 48, 12),
        Player("6", "AlirezaFirouzja", "alireza@chess.com", null, 2750, 420, 360, 50, 10),
        Player("7", "WesleySo", "wesley@chess.com", null, 2740, 400, 350, 40, 10),
        Player("8", "AnishGiri", "anish@chess.com", null, 2730, 380, 330, 42, 8),
        Player("9", "MaximeVachierLagrave", "maxime@chess.com", null, 2720, 360, 310, 40, 10),
        Player("10", "LevonAronian", "levon@chess.com", null, 2710, 350, 300, 40, 10)
    )

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(text = "Leaderboard", fontWeight = FontWeight.Bold) },
            colors = androidx.compose.material3.TopAppBarDefaults.topAppBarColors(containerColor = androidx.compose.material3.MaterialTheme.colorScheme.primaryContainer)
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(players) { index, player ->
                LeaderboardItem(rank = index + 1, player = player)
            }
        }
    }
}

@Composable
fun LeaderboardItem(rank: Int, player: Player) {
    val medalColor = when (rank) {
        1 -> Color(0xFFFFD700)
        2 -> Color(0xFFC0C0C0)
        3 -> Color(0xFFCD7F32)
        else -> androidx.compose.material3.MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = androidx.compose.material3.CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        androidx.compose.foundation.layout.Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "#$rank",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = medalColor
            )
            PlayerAvatar(name = player.username, size = 40)
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = player.username, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                Text(text = "${player.gamesPlayed} games • ${(player.winRate * 100).toInt()}% win rate", fontSize = 12.sp, color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
            }
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "${player.rating}", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = androidx.compose.material3.MaterialTheme.colorScheme.primary)
                Text(text = "Rating", fontSize = 10.sp, color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
            }
        }
    }
}