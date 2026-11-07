package com.boardarena.native.data.remote

import com.boardarena.native.game.model.Match
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.engineio.client.transports.WebSocket
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SocketManager @Inject constructor() {

    private var socket: Socket? = null
    private val matchUpdates = Channel<Match>(capacity = 100)
    private val connectionState = Channel<Boolean>(capacity = 1)

    fun connect(token: String) {
        if (socket?.connected == true) return

        socket = IO.Options().apply {
            this.transports = arrayOf(WebSocket.NAME)
            this.auth = JSONObject().put("token", token)
            this.reconnection = true
            this.reconnectionAttempts = 5
            this.reconnectionDelay = 1000
        }.let { IO.socket("https://api.boardarena.app/", it) }

        socket?.on(Socket.EVENT_CONNECT) { _ ->
            connectionState.trySend(true)
        }

        socket?.on(Socket.EVENT_DISCONNECT) { _ ->
            connectionState.trySend(false)
        }

        socket?.on("match_update") { args ->
            val json = args[0] as JSONObject
            val match = kotlinx.serialization.json.Json.decodeFromString<Match>(json.toString())
            matchUpdates.trySend(match)
        }

        socket?.connect()
    }

    fun disconnect() {
        socket?.disconnect()
        socket = null
    }

    fun joinMatchRoom(matchId: String) {
        socket?.emit("join_match", matchId)
    }

    fun leaveMatchRoom(matchId: String) {
        socket?.emit("leave_match", matchId)
    }

    fun sendMove(matchId: String, moveJson: String) {
        socket?.emit("make_move", JSONObject().put("matchId", matchId).put("move", moveJson))
    }

    val matchUpdatesFlow: Flow<Match> = matchUpdates.receiveAsFlow()
    val connectionStateFlow: Flow<Boolean> = connectionState.receiveAsFlow()
}