package com.example.weatherapp.data.remote.model.currentweather


import com.google.gson.annotations.SerializedName

data class Clouds(
    @SerializedName("all")
    var all: Int?
)