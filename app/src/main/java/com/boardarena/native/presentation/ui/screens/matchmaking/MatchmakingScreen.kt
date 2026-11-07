package com.boardarena.native.presentation.ui.screens.matchmaking

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.boardarena.native.game.model.GameType

@Composable
fun MatchmakingScreen(onNavigate: (String) -> Unit) {
    val gameType = GameType.values().firstOrNull { it.id == "chess" } ?: GameType.CHESS

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Find Match: ${gameType.displayName}", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        androidx.compose.foundation.layout.Spacer(modifier = Modifier.size(32.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = androidx.compose.material3.CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(text = "Quick Match", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                androidx.compose.foundation.layout.Spacer(modifier = Modifier.size(8.dp))
                Text(text = "Find a random opponent", fontSize = 14.sp, color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
                androidx.compose.foundation.layout.Spacer(modifier = Modifier.size(16.dp))
                Button(
                    onClick = { onNavigate("game/match_123") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Start Searching", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                }
            }
        }
        androidx.compose.foundation.layout.Spacer(modifier = Modifier.size(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = androidx.compose.material3.CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(text = "Private Match", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                androidx.compose.foundation.layout.Spacer(modifier = Modifier.size(8.dp))
                Text(text = "Play with a friend via code", fontSize = 14.sp, color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
                androidx.compose.foundation.layout.Spacer(modifier = Modifier.size(16.dp))
                Button(
                    onClick = { /* TODO: Show create/join private match dialog */ },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Create Private Match", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                }
            }
        }
    }
}