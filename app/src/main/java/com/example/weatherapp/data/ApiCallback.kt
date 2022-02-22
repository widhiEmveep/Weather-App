package com.example.weatherapp.data

import com.example.weatherapp.base.Const
import com.example.weatherapp.data.remote.model.currentweather.CurrentWeatherModel
import com.example.weatherapp.data.remote.model.forecast.ForecastWeathersModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiCallback {

    @GET(Const.Network.CURRENT_WEATHER)
    suspend fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") unit: String,
        @Query("appid") apiKey: String
    ): Response<CurrentWeatherModel>

    @GET(Const.Network.CURRENT_WEATHER)
    suspend fun getWeatherByCity(
        @Query("q") cityName: String,
        @Query("units") unit: String,
        @Query("appid") apiKey: String
    ): Response<CurrentWeatherModel>

    @GET(Const.Network.FORECAST_DAILY)
    suspend fun getForecastWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("cnt") count: Int,
        @Query("units") unit: String,
        @Query("appid") apiKey: String
    ): Response<ForecastWeathersModel>
}