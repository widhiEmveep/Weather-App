package com.example.weatherapp.data.source.callback

import com.example.weatherapp.data.remote.Result
import com.example.weatherapp.data.remote.model.currentweather.CurrentWeatherModel
import kotlinx.coroutines.flow.Flow

interface WeatherSourceCallback {

    fun getCurrentWeather(lat: Double, lon: Double) : Flow<Result<CurrentWeatherModel>>
    fun getWeatherByCity(cityName: String) : Flow<Result<CurrentWeatherModel>>
}