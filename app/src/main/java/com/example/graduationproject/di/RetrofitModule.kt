package com.example.graduationproject.di

import com.example.data.util.StatusAwareConverterFactory
import com.example.data.util.TokenInterceptor
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val BASE_URL = "https://swapwise.shop/api/"
        val gson = Gson()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(StatusAwareConverterFactory(gson))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(tokenInterceptor: TokenInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(tokenInterceptor)
            .build()
    }

}