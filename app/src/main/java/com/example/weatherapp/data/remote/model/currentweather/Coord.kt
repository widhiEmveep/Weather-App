package com.example.weatherapp.data.remote.model.currentweather


import com.google.gson.annotations.SerializedName

data class Coord(
    @SerializedName("lat")
    var lat: Int?,
    @SerializedName("lon")
    var lon: Int?
)