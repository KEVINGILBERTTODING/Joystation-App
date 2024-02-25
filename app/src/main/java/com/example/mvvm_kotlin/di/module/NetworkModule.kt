package com.example.mvvm_kotlin.di.module

import com.example.mvvm_kotlin.data.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Named("joystation")
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiService.END_POINT)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(@Named("joystation") retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}