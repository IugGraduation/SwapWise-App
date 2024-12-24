package com.example.graduationproject.di

import com.example.data.repository.HomeRepository
import com.example.data.repository.OfferRepository
import com.example.data.repository.PostRepository
import com.example.data.source.local.FakeHomeLocalDataSource
import com.example.data.source.local.FakePostLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun provideHomeRepository(
        fakeHomeLocalDataSource: FakeHomeLocalDataSource
    ): HomeRepository {
        return HomeRepository(fakeHomeLocalDataSource)
    }

    @Provides
    fun providePostRepository(
        fakePostLocalDataSource: FakePostLocalDataSource
    ): PostRepository {
        return PostRepository(fakePostLocalDataSource)
    }

    @Provides
    fun provideOfferRepository(
//        fakePostLocalDataSource: FakePostLocalDataSource
    ): OfferRepository {
        return OfferRepository()
    }
}
