package com.example.weatherapp.data.repository

import com.example.weatherapp.data.local.entity.FavoriteCity
import com.example.weatherapp.data.remote.Result
import com.example.weatherapp.data.remote.model.currentweather.CurrentWeatherModel
import com.example.weatherapp.data.remote.model.forecast.ForecastWeathersModel
import com.example.weatherapp.data.source.WeatherLocalDataSource
import com.example.weatherapp.data.source.WeatherRemoteDataSource
import com.example.weatherapp.data.source.callback.WeatherSourceCallback
import kotlinx.coroutines.flow.Flow

class WeatherRepository(
    weatherRemoteDataSource: WeatherRemoteDataSource,
    weatherLocalDataSource: WeatherLocalDataSource
) : WeatherSourceCallback {
    private val remoteDataSource = weatherRemoteDataSource
    private val localDataSource = weatherLocalDataSource

    override fun getCurrentWeather(lat: Double, lon: Double): Flow<Result<CurrentWeatherModel>> =
        remoteDataSource.handleGetCurrentWeather(lat, lon)

    override fun getWeatherByCity(cityName: String): Flow<Result<CurrentWeatherModel>> =
        remoteDataSource.handleCurrentWeatherByCity(cityName)

    override fun getForecastWeather(lat: Double, lon: Double): Flow<Result<ForecastWeathersModel>> =
        remoteDataSource.handleForecastWeather(lat, lon)

    override fun getFavCityResult(): Flow<List<FavoriteCity>> = localDataSource.getFavCity()

    override suspend fun insertFavCityResult(favoriteCity: FavoriteCity) =
        localDataSource.insertFavCityResult(favoriteCity)

    override suspend fun deleteFavCityResult(cityName: String) =
        localDataSource.deleteFavCityResult(cityName)
}