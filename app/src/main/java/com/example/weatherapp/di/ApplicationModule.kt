package com.example.weatherapp.di

import android.app.Application
import android.content.Context
import com.example.weatherapp.data.ApiCallback
import com.example.weatherapp.data.local.dao.FavoriteCityDao
import com.example.weatherapp.data.repository.WeatherRepository
import com.example.weatherapp.data.source.WeatherLocalDataSource
import com.example.weatherapp.data.source.WeatherRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    fun provideApplication(application: Application): Context = application

    @Provides
    fun provideMovieRepository(
        apiCallback: ApiCallback,
        favoriteCityDao: FavoriteCityDao
    ) = WeatherRepository(
        WeatherRemoteDataSource(apiCallback),
        WeatherLocalDataSource(favoriteCityDao)
    )
}