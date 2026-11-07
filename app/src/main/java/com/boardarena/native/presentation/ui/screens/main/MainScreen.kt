package com.boardarena.native.presentation.ui.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.boardarena.native.game.model.GameType
import com.boardarena.native.presentation.ui.components.GameCard

@Composable
fun MainScreen(onNavigate: (String) -> Unit) {
    var selectedTab by remember { mutableStateOf(0) }

    Column(modifier = Modifier.fillMaxSize()) {
        // Top bar area
        androidx.compose.material3.TopAppBar(
            title = { Text(text = "BOARD ARENA", fontWeight = FontWeight.Bold, fontSize = 20.sp) },
            colors = androidx.compose.material3.TopAppBarDefaults.topAppBarColors(containerColor = androidx.compose.material3.MaterialTheme.colorScheme.primaryContainer)
        )

        // Game list
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(GameType.values()) { gameType ->
                GameCard(gameType = gameType, onClick = { onNavigate("matchmaking?game=${gameType.id}") })
            }
        }

        // Bottom navigation
        NavigationBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = androidx.compose.foundation.layout.WindowInsets.navigationBars.asPaddingValues().bottom)
        ) {
            val items = listOf("Games" to "home", "Matchmaking" to "search", "Profile" to "person", "Leaderboard" to "leaderboard")
            items.forEachIndexed { index, (label, iconName) ->
                NavigationBarItem(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index; onNavigateWhenTabSelected(index) },
                    icon = { Icon(imageVector = getIcon(iconName), contentDescription = label) },
                    label = { Text(text = label, fontSize = 12.sp) }
                )
            }
        }
    }
}

private fun onNavigateWhenTabSelected(index: Int) {
    // Navigation handled by parent
}

@Composable
private fun getIcon(name: String): androidx.compose.ui.graphics.vector.ImageVector {
    return when (name) {
        "home" -> androidx.compose.material.icons.filled.Home
        "search" -> androidx.compose.material.icons.filled.Search
        "person" -> androidx.compose.material.icons.filled.Person
        "leaderboard" -> androidx.compose.material.icons.filled.Leaderboard
        else -> androidx.compose.material.icons.filled.Home
    }
}