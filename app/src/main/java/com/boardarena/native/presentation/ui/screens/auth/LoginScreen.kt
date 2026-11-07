package com.boardarena.native.presentation.ui.screens.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.em

@Composable
fun LoginScreen(onNavigate: (String) -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Welcome Back", fontSize = 32.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
        androidx.compose.foundation.layout.Spacer(modifier = Modifier.size(32.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = androidx.compose.ui.text.input.KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Email)
        )
        androidx.compose.foundation.layout.Spacer(modifier = Modifier.size(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = androidx.compose.material3.PasswordVisualTransformation(),
            keyboardOptions = androidx.compose.ui.text.input.KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Password)
        )
        androidx.compose.foundation.layout.Spacer(modifier = Modifier.size(24.dp))

        Button(
            onClick = {
                // TODO: Call login
                onNavigate("main")
            },
            modifier = Modifier.fillMaxWidth().height(56.dp)
        ) {
            Text(text = "Login", fontSize = 18.sp, fontWeight = FontWeight.Medium)
        }
        androidx.compose.foundation.layout.Spacer(modifier = Modifier.size(16.dp))

        androidx.compose.material3.TextButton(
            onClick = { onNavigate("register") }
        ) {
            Text(text = "Don't have an account? Register", fontSize = 14.sp)
        }
    }
}