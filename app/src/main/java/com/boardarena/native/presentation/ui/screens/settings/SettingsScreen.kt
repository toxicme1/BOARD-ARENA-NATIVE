package com.boardarena.native.presentation.ui.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
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

@Composable
fun SettingsScreen(onNavigate: (String) -> Unit) {
    var notificationsEnabled by remember { mutableStateOf(true) }
    var soundEnabled by remember { mutableStateOf(true) }
    var vibrationEnabled by remember { mutableStateOf(true) }
    var darkModeEnabled by remember { mutableStateOf(true) }
    var autoAcceptInvites by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(text = "Settings", fontWeight = FontWeight.Bold) },
            colors = androidx.compose.material3.TopAppBarDefaults.topAppBarColors(containerColor = androidx.compose.material3.MaterialTheme.colorScheme.primaryContainer)
        )

        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Notifications
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = androidx.compose.material3.CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Notifications", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    androidx.compose.foundation.layout.Spacer(modifier = Modifier.size(16.dp))
                    SettingRow(
                        title = "Push Notifications",
                        subtitle = "Receive match invites and game updates",
                        icon = "🔔",
                        action = { Switch(checked = notificationsEnabled, onCheckedChange = { notificationsEnabled = it }) }
                    )
                    SettingRow(
                        title = "Sound",
                        subtitle = "Play sound effects during games",
                        icon = "🔊",
                        action = { Switch(checked = soundEnabled, onCheckedChange = { soundEnabled = it }) }
                    )
                    SettingRow(
                        title = "Vibration",
                        subtitle = "Vibrate on your turn",
                        icon = "📳",
                        action = { Switch(checked = vibrationEnabled, onCheckedChange = { vibrationEnabled = it }) }
                    )
                }
            }

            // Appearance
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = androidx.compose.material3.CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Appearance", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    androidx.compose.foundation.layout.Spacer(modifier = Modifier.size(16.dp))
                    SettingRow(
                        title = "Dark Mode",
                        subtitle = "Use dark theme",
                        icon = "🌙",
                        action = { Switch(checked = darkModeEnabled, onCheckedChange = { darkModeEnabled = it }) }
                    )
                }
            }

            // Gameplay
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = androidx.compose.material3.CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Gameplay", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    androidx.compose.foundation.layout.Spacer(modifier = Modifier.size(16.dp))
                    SettingRow(
                        title = "Auto-Accept Invites",
                        subtitle = "Automatically join private matches from friends",
                        icon = "✅",
                        action = { Switch(checked = autoAcceptInvites, onCheckedChange = { autoAcceptInvites = it }) }
                    )
                    SettingRow(
                        title = "Move Confirmation",
                        subtitle = "Require confirmation before making a move",
                        icon = "❓",
                        action = { Switch(checked = false, onCheckedChange = { }) }
                    )
                    SettingRow(
                        title = "Show Legal Moves",
                        subtitle = "Highlight valid moves during your turn",
                        icon = "💡",
                        action = { Switch(checked = true, onCheckedChange = { }) }
                    )
                }
            }

            // Account
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = androidx.compose.material3.CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Account", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    androidx.compose.foundation.layout.Spacer(modifier = Modifier.size(16.dp))
                    SettingRow(
                        title = "Change Password",
                        subtitle = "Update your account password",
                        icon = "🔐",
                        action = { Button(onClick = { /* TODO */ }) { Text("Change") } }
                    )
                    SettingRow(
                        title = "Linked Accounts",
                        subtitle = "Manage connected social accounts",
                        icon = "🔗",
                        action = { Button(onClick = { /* TODO */ }) { Text("Manage") } }
                    )
                    SettingRow(
                        title = "Delete Account",
                        subtitle = "Permanently delete your account",
                        icon = "🗑️",
                        action = { Button(onClick = { /* TODO */ }, colors = androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = Color.Red)) { Text("Delete") } }
                    )
                }
            }

            // About
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = androidx.compose.material3.CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "About", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    androidx.compose.foundation.layout.Spacer(modifier = Modifier.size(16.dp))
                    SettingRow(
                        title = "Version",
                        subtitle = "1.0.0",
                        icon = "ℹ️"
                    )
                    SettingRow(
                        title = "Privacy Policy",
                        subtitle = "Read our privacy policy",
                        icon = "📄",
                        action = { Button(onClick = { /* TODO */ }) { Text("View") } }
                    )
                    SettingRow(
                        title = "Terms of Service",
                        subtitle = "Read our terms of service",
                        icon = "📜",
                        action = { Button(onClick = { /* TODO */ }) { Text("View") } }
                    )
                    SettingRow(
                        title = "Open Source Licenses",
                        subtitle = "View third-party licenses",
                        icon = "📚",
                        action = { Button(onClick = { /* TODO */ }) { Text("View") } }
                    )
                }
            }

            Button(
                onClick = { /* TODO: Logout */ },
                modifier = Modifier.fillMaxWidth(),
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Text(text = "Logout", fontSize = 16.sp, fontWeight = FontWeight.Medium, color = Color.White)
            }
        }
    }
}

@Composable
fun SettingRow(
    title: String,
    subtitle: String,
    icon: String,
    action: @Composable () -> Unit = {}
) {
    androidx.compose.foundation.layout.Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = icon, fontSize = 24.sp)
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.Medium)
            Text(text = subtitle, fontSize = 12.sp, color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
        }
        action()
    }
}