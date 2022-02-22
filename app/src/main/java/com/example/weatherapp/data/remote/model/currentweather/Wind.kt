package com.example.weatherapp.data.remote.model.currentweather


import com.google.gson.annotations.SerializedName

data class Wind(
    @SerializedName("deg")
    var deg: Double?,
    @SerializedName("speed")
    var speed: Double?
)