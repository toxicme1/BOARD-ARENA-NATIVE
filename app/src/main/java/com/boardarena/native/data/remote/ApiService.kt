package com.boardarena.native.data.remote

import com.boardarena.native.game.model.Player
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): ApiResponse<LoginResponse>

    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): ApiResponse<LoginResponse>

    @POST("auth/refresh")
    suspend fun refreshToken(): ApiResponse<TokenResponse>

    @GET("games")
    suspend fun getGames(): ApiResponse<List<GameInfo>>

    @GET("games/{id}/rules")
    suspend fun getGameRules(@Path("id") id: String): ApiResponse<GameRules>

    @POST("matches")
    suspend fun createMatch(@Body request: CreateMatchRequest): ApiResponse<MatchResponse>

    @POST("matches/{id}/join")
    suspend fun joinMatch(@Path("id") id: String): ApiResponse<MatchResponse>

    @POST("matches/{id}/move")
    suspend fun makeMove(@Path("id") id: String, @Body request: MoveRequest): ApiResponse<MatchResponse>

    @GET("matches/{id}")
    suspend fun getMatch(@Path("id") id: String): ApiResponse<MatchResponse>

    @GET("matches")
    suspend fun getPlayerMatches(@Query("player_id") playerId: String): ApiResponse<List<MatchResponse>>

    @POST("matches/{id}/forfeit")
    suspend fun forfeitMatch(@Path("id") id: String): ApiResponse<MatchResponse>

    @GET("players/{id}")
    suspend fun getPlayer(@Path("id") id: String): ApiResponse<Player>

    @GET("leaderboard")
    suspend fun getLeaderboard(@Query("limit") limit: Int = 100): ApiResponse<List<Player>>

    @GET("players/{id}/stats")
    suspend fun getPlayerStats(@Path("id") id: String): ApiResponse<PlayerStats>
}

data class LoginRequest(val email: String, val password: String)
data class RegisterRequest(val username: String, val email: String, val password: String)
data class LoginResponse(val player: Player, val accessToken: String, val refreshToken: String)
data class TokenResponse(val accessToken: String)
data class GameInfo(val id: String, val name: String, val minPlayers: Int, val maxPlayers: Int, val description: String)
data class GameRules(val gameId: String, val rules: String)
data class CreateMatchRequest(val gameType: String, val playerIds: List<String>)
data class MoveRequest(val moveJson: String)
data class MatchResponse(
    val id: String,
    val gameType: String,
    val status: String,
    val playerIds: List<String>,
    val currentTurnPlayerId: String?,
    val winnerId: String?,
    val gameStateJson: String?
)
data class PlayerStats(
    val totalGames: Int,
    val wins: Int,
    val losses: Int,
    val draws: Int,
    val winRate: Double,
    val rating: Int,
    val rank: Int
)

sealed class ApiResponse<out T> {
    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Error(val code: Int, val message: String) : ApiResponse<Nothing>()
}