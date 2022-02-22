package com.example.weatherapp.data.source

import com.example.weatherapp.data.local.dao.FavoriteCityDao
import com.example.weatherapp.data.local.entity.FavoriteCity

class WeatherLocalDataSource(
    favoriteCityDao: FavoriteCityDao
) {
    private val dao = favoriteCityDao

    fun getFavCity() = dao.getFavCity()

    suspend fun insertFavCityResult(favoriteCity: FavoriteCity) = dao.insertFavCity(favoriteCity)

    suspend fun deleteFavCityResult(cityName: String) = dao.deleteCity(cityName)
}