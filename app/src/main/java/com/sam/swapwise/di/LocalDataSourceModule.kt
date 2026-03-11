package com.sam.swapwise.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.sam.data.source.local.AuthDataStoreSourceImpl
import com.sam.data.source.local.AuthLocalDataSource
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