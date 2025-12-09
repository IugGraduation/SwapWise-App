package com.example.graduationproject.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.data.repository.AuthRepository
import com.example.data.repository.HomeRepository
import com.example.data.repository.OfferRepository
import com.example.data.repository.PostRepository
import com.example.data.repository.SearchRepository
import com.example.data.repository.UserRepository
import com.example.data.source.local.AuthLocalDataSource
import com.example.data.source.remote.AuthRemoteDataSource
import com.example.data.source.remote.HomeRemoteDataSource
import com.example.data.source.remote.OfferRemoteDataSource
import com.example.data.source.remote.PostRemoteDataSource
import com.example.data.source.remote.ProfileRemoteDataSource
import com.example.data.source.remote.SearchRemoteDataSource
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
            homeRemoteDataSource, dataStore, userRepository = userRepository
        )


    @Singleton
    @Provides
    fun providePostRepository(
        postRemoteDataSource: PostRemoteDataSource,
        userRepository: UserRepository
    ) =
        PostRepository(postRemoteDataSource, userRepository)

    @Singleton
    @Provides
    fun provideOfferRepository(offerRemoteDataSource: OfferRemoteDataSource) =
        OfferRepository(offerRemoteDataSource)

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
}
