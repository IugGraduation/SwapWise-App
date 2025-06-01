package com.example.graduationproject.di

import com.example.data.source.remote.ProfileRetrofitDataSource
import com.example.ui.base.StringsResource
import com.example.ui.util.StringsResourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
abstract class ResourcesModule {
    @Binds
    @ViewModelScoped
    abstract fun bindStringsResource(stringsResource: StringsResourceImpl): StringsResource

    @Singleton
    @Binds
    abstract fun bindBindProfileDataSource(profileRetrofitDataSource: ProfileRetrofitDataSource): ProfileRetrofitDataSource
}