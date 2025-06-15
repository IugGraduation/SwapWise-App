package com.example.graduationproject.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.data.source.local.AuthDataStoreSourceImpl
import com.example.data.source.local.AuthLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object LocalDataSourceModule {

    @Singleton
    @Provides
    fun provideAuthLocalDataSource(dataStore: DataStore<Preferences>): AuthLocalDataSource =
        AuthDataStoreSourceImpl(dataStore)
}