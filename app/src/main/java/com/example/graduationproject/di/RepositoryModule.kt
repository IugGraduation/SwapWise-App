package com.example.graduationproject.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.data.repository.AuthenticationRepository
import com.example.data.repository.HomeRepository
import com.example.data.repository.OfferRepository
import com.example.data.repository.PostRepository
import com.example.data.repository.UserRepository
import com.example.data.source.local.FakeHomeLocalDataSource
import com.example.data.source.local.FakePostLocalDataSource
import com.example.data.source.remote.AuthenticationRemoteDataSource
import com.example.data.source.remote.PostRemoteDataSource
import com.example.data.source.remote.ProfileDataSource
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
        authenticationRemoteDataSource: AuthenticationRemoteDataSource
    ) = AuthenticationRepository(authenticationRemoteDataSource)


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
     fun provideUserRepository(dataStore: DataStore<Preferences>, profileDataSource: ProfileDataSource): UserRepository {
         return UserRepository(dataStore = dataStore, profileDataSource = profileDataSource)
    }
}
