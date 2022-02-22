package com.example.weatherapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.weatherapp.base.Const
import com.example.weatherapp.data.local.converter.FavoriteCityConverter

@Entity(tableName = Const.Entity.FAVORITE_CITY_ENTITY)
@TypeConverters(FavoriteCityConverter::class)
data class FavoriteCity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val lat: Double,
    val lon: Double,
    val cityName: String
)


