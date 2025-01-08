package com.example.graduationproject.di

import com.example.ui.base.StringsResource
import com.example.ui.util.StringsResourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class ResourcesModule {
    @Binds
    @ViewModelScoped
    abstract fun bindStringsResource(stringsResource: StringsResourceImpl): StringsResource
}