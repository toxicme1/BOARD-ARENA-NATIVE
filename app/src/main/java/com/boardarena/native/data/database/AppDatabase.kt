package com.boardarena.native.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.boardarena.native.game.model.Match
import com.boardarena.native.game.model.Player

@Database(
    entities = [Player::class, Match::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun playerDao(): PlayerDao
    abstract fun matchDao(): MatchDao
}