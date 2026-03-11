package com.sam.swapwise.di

import com.sam.ui.shared.BottomNavigationViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ViewModelModule {
    @Singleton
    @Provides
    fun provideBottomNavigation(): BottomNavigationViewModel = BottomNavigationViewModel()
}