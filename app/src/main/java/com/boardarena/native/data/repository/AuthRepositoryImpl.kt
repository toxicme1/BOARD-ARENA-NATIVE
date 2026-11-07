package com.boardarena.native.data.repository

import com.boardarena.native.data.database.PlayerDao
import com.boardarena.native.domain.repository.AuthRepository
import com.boardarena.native.domain.repository.Result
import com.boardarena.native.game.model.Player
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val playerDao: PlayerDao,
    private val apiService: com.boardarena.native.data.remote.ApiService
) : AuthRepository {

    private val _currentUser = MutableStateFlow<Player?>(null)
    override val currentUser: kotlinx.coroutines.flow.Flow<Player?> = _currentUser.asStateFlow()

    override suspend fun login(email: String, password: String): Result<Player> {
        // TODO: Implement actual login with API
        return Result.Error(Throwable("Not implemented"))
    }

    override suspend fun register(username: String, email: String, password: String): Result<Player> {
        // TODO: Implement actual registration with API
        return Result.Error(Throwable("Not implemented"))
    }

    override suspend fun logout() {
        _currentUser.value = null
    }

    override fun getCurrentUser() = currentUser

    override suspend fun refreshToken(): Result<String> {
        return Result.Error(Throwable("Not implemented"))
    }
}