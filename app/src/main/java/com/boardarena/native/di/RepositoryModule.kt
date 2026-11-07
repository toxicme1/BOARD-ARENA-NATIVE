package com.boardarena.native.di

import com.boardarena.native.data.repository.AuthRepositoryImpl
import com.boardarena.native.data.repository.GameRepositoryImpl
import com.boardarena.native.data.repository.MatchRepositoryImpl
import com.boardarena.native.data.repository.ProfileRepositoryImpl
import com.boardarena.native.domain.repository.AuthRepository
import com.boardarena.native.domain.repository.GameRepository
import com.boardarena.native.domain.repository.MatchRepository
import com.boardarena.native.domain.repository.ProfileRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository = impl

    @Provides
    fun provideGameRepository(impl: GameRepositoryImpl): GameRepository = impl

    @Provides
    fun provideMatchRepository(impl: MatchRepositoryImpl): MatchRepository = impl

    @Provides
    fun provideProfileRepository(impl: ProfileRepositoryImpl): ProfileRepository = impl
}