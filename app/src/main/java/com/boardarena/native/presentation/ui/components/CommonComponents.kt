package com.boardarena.native.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.em

@Composable
fun BoardArenaTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    actions: @Composable RowScope.() -> Unit = {},
    navigationIcon: @Composable () -> Unit = {}
) {
    TopAppBar(
        modifier = modifier,
        title = { Text(text = title, fontWeight = FontWeight.Bold, fontSize = 20.sp) },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = androidx.compose.material3.MaterialTheme.colorScheme.primaryContainer),
        actions = actions,
        navigationIcon = navigationIcon
    )
}

@Composable
fun GameCard(
    gameType: com.boardarena.native.game.model.GameType,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth().height(120.dp),
        onClick = onClick,
        elevation = androidx.compose.material3.CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(80.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = gameType.displayName[0].toString(),
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = androidx.compose.material3.MaterialTheme.colorScheme.primary
                )
            }
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = gameType.displayName,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${gameType.minPlayers}-${gameType.maxPlayers} players",
                    fontSize = 14.sp,
                    color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
            IconButton(onClick = onClick) {
                Icon(
                    imageVector = androidx.compose.material.icons.filled.NavigateNext,
                    contentDescription = "Play ${gameType.displayName}"
                )
            }
        }
    }
}

@Composable
fun PlayerAvatar(
    name: String,
    avatarUrl: String? = null,
    size: Int = 48,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.size(size.dp),
        contentAlignment = Alignment.Center
    ) {
        if (avatarUrl != null) {
            // TODO: Load with Coil
            Text(text = name[0].toString().uppercase(), fontSize = (size * 0.4).sp, fontWeight = FontWeight.Bold, color = Color.White)
        } else {
            Text(text = name[0].toString().uppercase(), fontSize = (size * 0.4).sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
    }
}