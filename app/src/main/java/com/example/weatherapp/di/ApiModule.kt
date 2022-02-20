package com.example.weatherapp.di

import com.example.weatherapp.data.ApiCallback
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    fun provideWeatherAppApiCallback(retrofit: Retrofit): ApiCallback =
        retrofit.create(ApiCallback::class.java)

}