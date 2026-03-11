package com.sam.swapwise.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.sam.data.repository.AuthRepository
import com.sam.data.repository.HomeRepository
import com.sam.data.repository.LocationRepository
import com.sam.data.repository.PostRepository
import com.sam.data.repository.SearchRepository
import com.sam.data.repository.UserRepository
import com.sam.data.source.local.AuthLocalDataSource
import com.sam.data.source.remote.AuthRemoteDataSource
import com.sam.data.source.remote.HomeRemoteDataSource
import com.sam.data.source.remote.LocationRemoteDataSource
import com.sam.data.source.remote.PostRemoteDataSource
import com.sam.data.source.remote.ProfileRemoteDataSource
import com.sam.data.source.remote.SearchRemoteDataSource
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
        authRemoteDataSource: AuthRemoteDataSource,
        authLocalDataSource: AuthLocalDataSource
    ) = AuthRepository(authRemoteDataSource, authLocalDataSource)

    @Singleton
    @Provides
    fun provideHomeRepository(
        homeRemoteDataSource: HomeRemoteDataSource,
        dataStore: DataStore<Preferences>,
        userRepository: UserRepository
    ) =
        HomeRepository(
            homeRemoteDataSource, dataStore, userRepository
        )


    @Singleton
    @Provides
    fun providePostRepository(
        postRemoteDataSource: PostRemoteDataSource,
        userRepository: UserRepository
    ) =
        PostRepository(postRemoteDataSource, userRepository)

    @Provides
    fun provideUserRepository(
        dataStore: DataStore<Preferences>,
        profileRemoteDataSource: ProfileRemoteDataSource
    ): UserRepository {
        return UserRepository(
            dataStore = dataStore,
            profileRemoteDataSource = profileRemoteDataSource
        )
    }

    @Provides
    fun provideSearchRepository(
        searchRemoteDataSource: SearchRemoteDataSource,
        userRepository: UserRepository
    ): SearchRepository {
        return SearchRepository(searchRemoteDataSource, userRepository)
    }

    @Provides
    @Singleton
    fun provideLocationRepository(locationRemoteDataSource: LocationRemoteDataSource): LocationRepository {
        return LocationRepository(locationRemoteDataSource)
    }
}
