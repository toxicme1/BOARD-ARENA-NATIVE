package com.boardarena.native.presentation.ui.screens.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.em

@Composable
fun WelcomeScreen(onNavigate: (String) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "BOARD ARENA",
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            color = androidx.compose.material3.MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )
        androidx.compose.foundation.layout.Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = "Play Chess, LUDO, Checkers & More",
            fontSize = 18.sp,
            color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            textAlign = TextAlign.Center
        )
        androidx.compose.foundation.layout.Spacer(modifier = Modifier.size(48.dp))
        Button(
            onClick = { onNavigate("login") },
            modifier = Modifier.fillMaxWidth().height(56.dp)
        ) {
            Text(text = "Get Started", fontSize = 18.sp, fontWeight = FontWeight.Medium)
        }
        androidx.compose.foundation.layout.Spacer(modifier = Modifier.size(16.dp))
        Button(
            onClick = { onNavigate("register") },
            modifier = Modifier.fillMaxWidth().height(56.dp)
        ) {
            Text(text = "Create Account", fontSize = 18.sp, fontWeight = FontWeight.Medium)
        }
    }
}