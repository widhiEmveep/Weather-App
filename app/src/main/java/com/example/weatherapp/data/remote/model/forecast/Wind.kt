package com.example.weatherapp.data.remote.model.forecast


import com.google.gson.annotations.SerializedName

data class Wind(
    @SerializedName("deg")
    var deg: Int?,
    @SerializedName("gust")
    var gust: Double?,
    @SerializedName("speed")
    var speed: Double?
)