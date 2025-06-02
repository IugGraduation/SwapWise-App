package com.example.graduationproject.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.data.source.local.AuthDataStoreSourceImpl
import com.example.data.source.local.AuthLocalDataSource
import com.example.data.source.remote.AuthFirebaseDataSourceImpl
import com.example.data.source.remote.AuthRemoteDataSource
import com.example.data.source.remote.ProfileFirebaseDataSourceImpl
import com.example.data.source.remote.ProfileRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Singleton
    @Provides
    fun provideAuthLocalDataSource(dataStore: DataStore<Preferences>): AuthLocalDataSource =
        AuthDataStoreSourceImpl(dataStore)

    @Singleton
    @Provides
    fun provideAuthRemoteDataSource(profileFirebaseDataSourceImpl: ProfileFirebaseDataSourceImpl): AuthRemoteDataSource =
        AuthFirebaseDataSourceImpl(profileFirebaseDataSourceImpl)

    @Singleton
    @Provides
    fun provideProfileRemoteDataSource(): ProfileRemoteDataSource =
        ProfileFirebaseDataSourceImpl()


}