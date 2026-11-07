package com.boardarena.native.presentation.ui.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.boardarena.native.domain.repository.PlayerStats
import com.boardarena.native.presentation.ui.components.PlayerAvatar

@Composable
fun ProfileScreen(onNavigate: (String) -> Unit) {
    var playerName by remember { mutableStateOf("Player123") }
    var playerRating by remember { mutableStateOf(1250) }
    var gamesPlayed by remember { mutableStateOf(42) }
    var winRate by remember { mutableStateOf(0.65) }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(text = "Profile", fontWeight = FontWeight.Bold) },
            colors = androidx.compose.material3.TopAppBarDefaults.topAppBarColors(containerColor = androidx.compose.material3.MaterialTheme.colorScheme.primaryContainer)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Profile header
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = androidx.compose.material3.CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    PlayerAvatar(name = playerName, size = 80)
                    androidx.compose.foundation.layout.Spacer(modifier = Modifier.size(16.dp))
                    Text(text = playerName, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    androidx.compose.foundation.layout.Spacer(modifier = Modifier.size(8.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        Column {
                            Text(text = "$playerRating", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = androidx.compose.material3.MaterialTheme.colorScheme.primary)
                            Text(text = "Rating", fontSize = 12.sp, color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                        }
                        Column {
                            Text(text = "$gamesPlayed", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                            Text(text = "Games", fontSize = 12.sp, color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                        }
                        Column {
                            Text(text = "${(winRate * 100).toInt()}%", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Green)
                            Text(text = "Win Rate", fontSize = 12.sp, color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                        }
                    }
                }
            }

            // Stats cards
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = androidx.compose.material3.CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Statistics", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    androidx.compose.foundation.layout.Spacer(modifier = Modifier.size(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        StatCard(label = "Wins", value = "28", color = Color.Green)
                        StatCard(label = "Losses", value = "12", color = Color.Red)
                        StatCard(label = "Draws", value = "2", color = Color.Gray)
                    }
                }
            }

            // Achievements
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = androidx.compose.material3.CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Achievements", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    androidx.compose.foundation.layout.Spacer(modifier = Modifier.size(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        AchievementBadge(title = "First Win", icon = "🏆", unlocked = true)
                        AchievementBadge(title = "10 Wins", icon = "🥇", unlocked = true)
                        AchievementBadge(title = "50 Wins", icon = "💎", unlocked = false)
                        AchievementBadge(title = "Grandmaster", icon = "👑", unlocked = false)
                    }
                }
            }

            Button(
                onClick = { onNavigate("settings") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Edit Profile", fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun StatCard(label: String, value: String, color: Color) {
    Card(
        modifier = Modifier.weight(1f).height(80.dp),
        colors = androidx.compose.material3.CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.1f)
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = value, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = color)
            Text(text = label, fontSize = 12.sp, color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
        }
    }
}

@Composable
fun AchievementBadge(title: String, icon: String, unlocked: Boolean) {
    Card(
        modifier = Modifier.size(80.dp),
        colors = androidx.compose.material3.CardDefaults.cardColors(
            containerColor = if (unlocked) androidx.compose.material3.MaterialTheme.colorScheme.primaryContainer else androidx.compose.material3.MaterialTheme.colorScheme.surfaceContainerHighest
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = icon, fontSize = 24.sp, alpha = if (unlocked) 1f else 0.3f)
            Text(text = title, fontSize = 10.sp, textAlign = androidx.compose.ui.text.style.TextAlign.Center, color = if (unlocked) androidx.compose.material3.MaterialTheme.colorScheme.onPrimaryContainer else androidx.compose.material3.MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f))
        }
    }
}