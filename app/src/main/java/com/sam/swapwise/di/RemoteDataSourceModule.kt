package com.sam.swapwise.di

import com.sam.data.source.remote.AuthRemoteDataSource
import com.sam.data.source.remote.AuthSupabaseDataSourceImpl
import com.sam.data.source.remote.HomeRemoteDataSource
import com.sam.data.source.remote.HomeSupabaseDataSourceImpl
import com.sam.data.source.remote.LocationRemoteDataSource
import com.sam.data.source.remote.LocationSupabaseDataSourceImpl
import com.sam.data.source.remote.MockDataSourceImpl
import com.sam.data.source.remote.NotificationsRemoteDataSource
import com.sam.data.source.remote.PostRemoteDataSource
import com.sam.data.source.remote.PostSupabaseDataSourceImpl
import com.sam.data.source.remote.ProfileRemoteDataSource
import com.sam.data.source.remote.ProfileSupabaseDataSourceImpl
import com.sam.data.source.remote.SearchRemoteDataSource
import com.sam.data.source.remote.SearchSupabaseDataSourceImpl
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
    abstract fun providePostRemoteDataSource(postSupabaseDataSourceImpl: PostSupabaseDataSourceImpl): PostRemoteDataSource

    @Binds
    @Singleton
    abstract fun provideHomeRemoteDataSource(homeSupabaseDataSourceImpl: HomeSupabaseDataSourceImpl): HomeRemoteDataSource

    @Binds
    @Singleton
    abstract fun provideSearchRemoteDataSource(searchSupabaseDataSourceImpl: SearchSupabaseDataSourceImpl): SearchRemoteDataSource

    @Binds
    @Singleton
    abstract fun provideNotificationsRemoteDataSource(mockDataSourceImpl: MockDataSourceImpl): NotificationsRemoteDataSource

    @Binds
    @Singleton
    abstract fun provideLocationRemoteDataSource(locationSupabaseDataSourceImpl: LocationSupabaseDataSourceImpl): LocationRemoteDataSource


}