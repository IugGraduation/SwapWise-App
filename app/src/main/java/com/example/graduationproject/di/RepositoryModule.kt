package com.example.graduationproject.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.data.repository.AuthRepository
import com.example.data.repository.HomeRepository
import com.example.data.repository.OfferRepository
import com.example.data.repository.PostRepository
import com.example.data.repository.SearchRepository
import com.example.data.repository.UserRepository
import com.example.data.source.remote.AuthRemoteDataSource
import com.example.data.source.remote.HomeRemoteDataSource
import com.example.data.source.remote.OfferRemoteDataSource
import com.example.data.source.remote.PostRemoteDataSource
import com.example.data.source.remote.SearchRemoteDataSource
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
        authRemoteDataSource: AuthRemoteDataSource,
        dataStore: DataStore<Preferences>
    ) = AuthRepository(authRemoteDataSource, dataStore)


    @Singleton
    @Provides
    fun provideHomeRepository(
        homeRemoteDataSource: HomeRemoteDataSource,
        dataStore: DataStore<Preferences>
    ) =
        HomeRepository(homeRemoteDataSource, dataStore)

    @Singleton
    @Provides
    fun providePostRepository(postRemoteDataSource: PostRemoteDataSource) =
        PostRepository(postRemoteDataSource)

    @Singleton
    @Provides
    fun provideOfferRepository(offerRemoteDataSource: OfferRemoteDataSource) =
        OfferRepository(offerRemoteDataSource)

    @Provides
     fun provideUserRepository(dataStore: DataStore<Preferences>, profileDataSource: ProfileDataSource): UserRepository {
         return UserRepository(dataStore = dataStore, profileDataSource = profileDataSource)
    }

    @Provides
    fun provideSearchRepository(searchRemoteDataSource: SearchRemoteDataSource): SearchRepository {
        return SearchRepository(searchRemoteDataSource)
    }
}
