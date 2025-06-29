package com.example.graduationproject.di

import com.example.data.source.remote.AuthRemoteDataSource
import com.example.data.source.remote.AuthSupabaseDataSourceImpl
import com.example.data.source.remote.HomeRemoteDataSource
import com.example.data.source.remote.HomeSupabaseDataSourceImpl
import com.example.data.source.remote.MockDataSourceImpl
import com.example.data.source.remote.NotificationsRemoteDataSource
import com.example.data.source.remote.OfferRemoteDataSource
import com.example.data.source.remote.PostRemoteDataSource
import com.example.data.source.remote.ProfileRemoteDataSource
import com.example.data.source.remote.ProfileSupabaseDataSourceImpl
import com.example.data.source.remote.SearchRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataSourceModule {
    @Binds
    @Singleton
    abstract fun provideAuthRemoteDataSource(authSupabaseDataSourceImpl: AuthSupabaseDataSourceImpl): AuthRemoteDataSource

    @Binds
    @Singleton
    abstract fun provideProfileRemoteDataSource(profileSupabaseDataSourceImpl: ProfileSupabaseDataSourceImpl): ProfileRemoteDataSource

    @Binds
    @Singleton
    abstract fun providePostRemoteDataSource(mockDataSourceImpl: MockDataSourceImpl): PostRemoteDataSource

    @Binds
    @Singleton
    abstract fun provideHomeRemoteDataSource(homeSupabaseDataSourceImpl: HomeSupabaseDataSourceImpl): HomeRemoteDataSource

    @Binds
    @Singleton
    abstract fun provideOfferRemoteDataSource(mockDataSourceImpl: MockDataSourceImpl): OfferRemoteDataSource

    @Binds
    @Singleton
    abstract fun provideSearchRemoteDataSource(mockDataSourceImpl: MockDataSourceImpl): SearchRemoteDataSource

    @Binds
    @Singleton
    abstract fun provideNotificationsRemoteDataSource(mockDataSourceImpl: MockDataSourceImpl): NotificationsRemoteDataSource


}