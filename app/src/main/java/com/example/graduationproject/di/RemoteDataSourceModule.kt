package com.example.graduationproject.di

import com.example.data.source.remote.AuthRemoteDataSource
import com.example.data.source.remote.AuthSupabaseDataSourceImpl
import com.example.data.source.remote.ProfileRemoteDataSource
import com.example.data.source.remote.ProfileSupabaseDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RemoteDataSourceModule {


//    @Singleton
//    @Provides
//    fun provideAuthRemoteDataSource(
//        supabaseClient: SupabaseClient, profileRemoteDataSource: ProfileRemoteDataSource
//    ): AuthSupabaseDataSourceImpl =
//        AuthSupabaseDataSourceImpl(supabaseClient, profileRemoteDataSource)
//

//    @Singleton
//    @Provides
//    fun provideProfileRemoteDataSource(): ProfileRemoteDataSource =
//        ProfileFirebaseDataSourceImpl()
//
//    @Singleton
//    @Provides
//    fun providePostRemoteDataSource(): PostRemoteDataSource =
//        PostFirebaseDataSource()


}


@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataSourceModule2 {
    @Binds
    @Singleton
    abstract fun provideAuthRemoteDataSource(authSupabaseDataSourceImpl: AuthSupabaseDataSourceImpl): AuthRemoteDataSource

    @Binds
    @Singleton
    abstract fun provideProfileRemoteDataSource(profileSupabaseDataSourceImpl: ProfileSupabaseDataSourceImpl): ProfileRemoteDataSource

}