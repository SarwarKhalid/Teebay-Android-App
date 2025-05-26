package com.example.teebay.framework.network.di

import com.example.teebay.framework.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 *  Class to provide manual dependency injection for network classes
 */
@Module
@InstallIn(SingletonComponent::class)
object HiltNetworkModule {

    private val BASE_URL = "http://192.168.50.146"

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("$BASE_URL:8000/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)
}