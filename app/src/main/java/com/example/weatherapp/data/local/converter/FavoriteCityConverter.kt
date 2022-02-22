package com.example.weatherapp.data.local.converter

import androidx.room.TypeConverter
import com.example.weatherapp.data.local.entity.FavoriteCity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class FavoriteCityConverter {
    @TypeConverter
    fun fromFavCityListToString(city: MutableList<FavoriteCity>?): String? {
        return Gson().toJson(city)
    }

    @TypeConverter
    fun fromStringToFavCityList(data: String?): MutableList<FavoriteCity>? {
        val type = object : TypeToken<MutableList<FavoriteCity>>(){}.type
        return Gson().fromJson(data, type)
    }
}