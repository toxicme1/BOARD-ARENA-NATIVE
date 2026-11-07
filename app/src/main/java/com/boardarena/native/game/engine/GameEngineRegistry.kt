package com.boardarena.native.game.engine

import com.boardarena.native.game.model.GameType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameEngineRegistry @Inject constructor(
    private val engines: Map<GameType, GameEngine>
) {
    fun getEngine(gameType: GameType): GameEngine? = engines[gameType]

    fun initializeAll() {
        engines.values.forEach { engine ->
            // Engine initialization if needed
        }
    }
}