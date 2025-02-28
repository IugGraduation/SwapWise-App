package com.example.graduationproject.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.data.repository.AuthRepository
import com.example.data.repository.HomeRepository
import com.example.data.repository.OfferRepository
import com.example.data.repository.PostRepository
import com.example.data.repository.UserRepository
import com.example.data.source.local.FakeHomeLocalDataSource
import com.example.data.source.local.FakePostLocalDataSource
import com.example.data.source.remote.AuthenticationRemoteDataSource
import com.example.data.source.remote.PostRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideAuthenticationRepository(
        authenticationRemoteDataSource: AuthenticationRemoteDataSource,
        dataStore: DataStore<Preferences>
    ) = AuthRepository(authenticationRemoteDataSource, dataStore)


    @Singleton
    @Provides
    fun provideHomeRepository(
        fakeHomeLocalDataSource: FakeHomeLocalDataSource
    ): HomeRepository {
        return HomeRepository(fakeHomeLocalDataSource)
    }

    @Singleton
    @Provides
    fun providePostRepository(
        fakePostLocalDataSource: FakePostLocalDataSource,
        postRemoteDataSource: PostRemoteDataSource,
    ): PostRepository {
        return PostRepository(fakePostLocalDataSource, postRemoteDataSource)
    }

    @Singleton
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
