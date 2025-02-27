package com.example.graduationproject.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.data.repository.HomeRepository
import com.example.data.repository.OfferRepository
import com.example.data.repository.PostRepository
import com.example.data.repository.UserRepository
import com.example.data.source.local.FakeHomeLocalDataSource
import com.example.data.source.local.FakePostLocalDataSource
import com.example.data.source.remote.PostApiService
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
        fakePostLocalDataSource: FakePostLocalDataSource,
        postApiService: PostApiService,
    ): PostRepository {
        return PostRepository(fakePostLocalDataSource, postApiService)
    }

    @Provides
    fun provideOfferRepository(
//        fakePostLocalDataSource: FakePostLocalDataSource
    ): OfferRepository {
        return OfferRepository()
    }

    @Provides
     fun provideUserRepository(dataStore: DataStore<Preferences>): UserRepository {
         return UserRepository(dataStore)
    }
}
