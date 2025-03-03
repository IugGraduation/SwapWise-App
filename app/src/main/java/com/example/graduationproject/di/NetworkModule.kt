package com.example.graduationproject.di

import com.example.data.util.StatusAwareConverterFactory
import com.example.data.source.remote.AuthRemoteDataSource
import com.example.data.source.remote.PostRemoteDataSource
import com.google.gson.Gson
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
        val gson = Gson()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(StatusAwareConverterFactory(gson))
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
    fun provideAuthenticationApiService(retrofit: Retrofit): AuthRemoteDataSource {
        return retrofit.create(AuthRemoteDataSource::class.java)
    }


}