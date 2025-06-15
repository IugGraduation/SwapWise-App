package com.example.graduationproject.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SupabaseModule {

    @Singleton
    @Provides
    fun provideRetrofit(): SupabaseClient {
        val supabase = createSupabaseClient(
            supabaseUrl = "https://jlmqsobipfiihvynwpfh.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImpsbXFzb2JpcGZpaWh2eW53cGZoIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDk4MTcwNjcsImV4cCI6MjA2NTM5MzA2N30.XP3MY1nHaM943MAxdR2K1HMCu0upPzBrjySfuyXyhes"
        ) {
            install(Postgrest)
            install(Auth)
        }

        return supabase
    }

}