package com.example.weatherapp.data.remote.model.currentweather


import com.google.gson.annotations.SerializedName

data class Sys(
    @SerializedName("country")
    var country: String?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("message")
    var message: Double?,
    @SerializedName("sunrise")
    var sunrise: Int?,
    @SerializedName("sunset")
    var sunset: Int?,
    @SerializedName("type")
    var type: Int?
)