package com.example.weatherapp.data.remote.model.forecast


import com.google.gson.annotations.SerializedName

data class ForecastWeathersModel(
    @SerializedName("city")
    var city: City?,
    @SerializedName("cnt")
    var cnt: Int?,
    @SerializedName("cod")
    var cod: String?,
    @SerializedName("list")
    var list: List<WeatherList>?,
    @SerializedName("message")
    var message: Int?
)