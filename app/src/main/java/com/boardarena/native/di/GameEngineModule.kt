package com.boardarena.native.di

import com.boardarena.native.game.engine.GameEngine
import com.boardarena.native.game.engine.GameEngineRegistry
import com.boardarena.native.game.chess.ChessEngine
import com.boardarena.native.game.ludo.LudoEngine
import com.boardarena.native.game.checkers.CheckersEngine
import com.boardarena.native.game.tictactoe.TicTacToeEngine
import com.boardarena.native.game.connectfour.ConnectFourEngine
import com.boardarena.native.game.connectsix.ConnectSixEngine
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GameEngineModule {

    @Provides
    @Singleton
    fun provideGameEngineRegistry(
        chessEngine: ChessEngine,
        ludoEngine: LudoEngine,
        checkersEngine: CheckersEngine,
        ticTacToeEngine: TicTacToeEngine,
        connectFourEngine: ConnectFourEngine,
        connectSixEngine: ConnectSixEngine
    ): GameEngineRegistry {
        return GameEngineRegistry(
            mapOf(
                GameType.CHESS to chessEngine,
                GameType.LUDO to ludoEngine,
                GameType.CHECKERS to checkersEngine,
                GameType.TIC_TAC_TOE to ticTacToeEngine,
                GameType.CONNECT_FOUR to connectFourEngine,
                GameType.CONNECT_SIX to connectSixEngine
            )
        )
    }
}