package com.example.graduationproject.di

import com.example.data.source.remote.AuthenticationRemoteDataSource
import com.example.data.source.remote.PostRemoteDataSource
import com.example.data.source.remote.ProfileDataSource
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        val BASE_URL = "https://swapwise.shop/api/"
        val gson = GsonBuilder().setLenient().create()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Singleton
    @Provides
    fun providePostApiService(retrofit: Retrofit): PostRemoteDataSource {
        return retrofit.create(PostRemoteDataSource::class.java)
    }

    @Singleton
    @Provides
    fun provideAuthenticationApiService(retrofit: Retrofit): AuthenticationRemoteDataSource {
        return retrofit.create(AuthenticationRemoteDataSource::class.java)
    }

    @Singleton
    @Provides
    fun provideProfileApiService(retrofit: Retrofit): ProfileDataSource {
        return retrofit.create(ProfileDataSource::class.java)
    }


}