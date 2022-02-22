package com.example.weatherapp.data.source

import com.example.weatherapp.base.Const
import com.example.weatherapp.data.ApiCallback
import com.example.weatherapp.utils.flowResponse

class WeatherRemoteDataSource(private val apiCallback: ApiCallback) {

    fun handleGetCurrentWeather(lat: Double, lon: Double) =
        flowResponse { apiCallback.getCurrentWeather(lat = lat, lon = lon, unit = "metric", Const.API_KEY) }

    fun handleCurrentWeatherByCity(cityName: String) =
        flowResponse { apiCallback.getWeatherByCity(cityName = cityName, unit = "metric", Const.API_KEY) }
}