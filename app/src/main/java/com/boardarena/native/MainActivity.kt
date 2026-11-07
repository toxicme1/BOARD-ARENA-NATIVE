package com.boardarena.native

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Material3
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberAnimatedNavController
import com.boardarena.native.di.NetworkModule
import com.boardarena.native.di.RepositoryModule
import com.boardarena.native.game.engine.GameEngineRegistry
import com.boardarena.native.presentation.navigation.AppNavHost
import com.boardarena.native.presentation.ui.theme.BoardArenaTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var gameEngineRegistry: GameEngineRegistry

    private val appViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            BoardArenaTheme {
                Material3 {
                    AppNavHost(navController = rememberAnimatedNavController(), startDestination = "auth")
                }
            }
        }
        // Initialize game engines
        gameEngineRegistry.initializeAll()
    }
}