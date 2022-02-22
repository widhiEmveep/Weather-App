package com.example.weatherapp.data

import com.example.weatherapp.base.Const
import com.example.weatherapp.data.remote.model.currentweather.CurrentWeatherModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiCallback {

    @GET(Const.Network.CURRENT_WEATHER)
    suspend fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") unit : String,
        @Query("appid") apiKey : String
    ): Response<CurrentWeatherModel>
}