package com.boardarena.native.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberAnimatedNavController
import com.boardarena.native.presentation.ui.screens.auth.LoginScreen
import com.boardarena.native.presentation.ui.screens.auth.RegisterScreen
import com.boardarena.native.presentation.ui.screens.auth.WelcomeScreen
import com.boardarena.native.presentation.ui.screens.game.GameScreen
import com.boardarena.native.presentation.ui.screens.leaderboard.LeaderboardScreen
import com.boardarena.native.presentation.ui.screens.main.MainScreen
import com.boardarena.native.presentation.ui.screens.matchmaking.MatchmakingScreen
import com.boardarena.native.presentation.ui.screens.profile.ProfileScreen
import com.boardarena.native.presentation.ui.screens.settings.SettingsScreen

@Composable
fun AppNavHost(
    navController: NavHostController = rememberAnimatedNavController(),
    startDestination: String = "welcome"
) {
    NavHost(navController, startDestination) {
        composable("welcome") { WelcomeScreen(onNavigate = { navController.navigate(it) }) }
        composable("login") { LoginScreen(onNavigate = { navController.navigate(it) }) }
        composable("register") { RegisterScreen(onNavigate = { navController.navigate(it) }) }
        composable("main") { MainScreen(onNavigate = { navController.navigate(it) }) }
        composable("matchmaking") { MatchmakingScreen(onNavigate = { navController.navigate(it) }) }
        composable("game/{matchId}") {
            val matchId = it.getString()!!
            GameScreen(matchId = matchId, onNavigate = { navController.navigate(it) })
        }
        composable("profile") { ProfileScreen(onNavigate = { navController.navigate(it) }) }
        composable("leaderboard") { LeaderboardScreen(onNavigate = { navController.navigate(it) }) }
        composable("settings") { SettingsScreen(onNavigate = { navController.navigate(it) }) }
    }
}