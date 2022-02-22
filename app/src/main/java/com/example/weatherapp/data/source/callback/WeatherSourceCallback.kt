package com.example.weatherapp.data.source.callback

import com.example.weatherapp.data.local.entity.FavoriteCity
import com.example.weatherapp.data.remote.Result
import com.example.weatherapp.data.remote.model.currentweather.CurrentWeatherModel
import com.example.weatherapp.data.remote.model.forecast.ForecastWeathersModel
import kotlinx.coroutines.flow.Flow

interface WeatherSourceCallback {

    fun getCurrentWeather(lat: Double, lon: Double): Flow<Result<CurrentWeatherModel>>
    fun getWeatherByCity(cityName: String): Flow<Result<CurrentWeatherModel>>
    fun getForecastWeather(lat: Double, lon: Double): Flow<Result<ForecastWeathersModel>>

    fun getFavCityResult(): Flow<List<FavoriteCity>>
    suspend fun insertFavCityResult(favoriteCity: FavoriteCity)
    suspend fun deleteFavCityResult(cityName: String)
}