package com.example.graduationproject.di

import com.example.data.source.remote.AuthRetrofitDataSource
import com.example.data.source.remote.HomeRemoteDataSource
import com.example.data.source.remote.NotificationsRemoteDataSource
import com.example.data.source.remote.OfferRemoteDataSource
import com.example.data.source.remote.PostRetrofitDataSource
import com.example.data.source.remote.ProfileRetrofitDataSource
import com.example.data.source.remote.SearchRemoteDataSource
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

    @Singleton
    @Provides
    fun providePostApiService(retrofit: Retrofit): PostRetrofitDataSource {
        return retrofit.create(PostRetrofitDataSource::class.java)
    }

    @Singleton
    @Provides
    fun provideOfferApiService(retrofit: Retrofit): OfferRemoteDataSource {
        return retrofit.create(OfferRemoteDataSource::class.java)
    }

    @Singleton
    @Provides
    fun provideAuthenticationApiService(retrofit: Retrofit): AuthRetrofitDataSource {
        return retrofit.create(AuthRetrofitDataSource::class.java)
    }

    @Singleton
    @Provides
    fun provideHomeApiService(retrofit: Retrofit): HomeRemoteDataSource {
        return retrofit.create(HomeRemoteDataSource::class.java)
    }

    @Singleton
    @Provides
    fun provideSearchApiService(retrofit: Retrofit): SearchRemoteDataSource {
        return retrofit.create(SearchRemoteDataSource::class.java)
    }

    @Singleton
    @Provides
    fun provideNotificationsApiService(retrofit: Retrofit): NotificationsRemoteDataSource {
        return retrofit.create(NotificationsRemoteDataSource::class.java)
    }

    @Singleton
    @Provides
    fun provideProfileApiService(retrofit: Retrofit): ProfileRetrofitDataSource {
        return retrofit.create(ProfileRetrofitDataSource::class.java)
    }


}