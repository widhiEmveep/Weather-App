package com.example.weatherapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherapp.data.local.dao.FavoriteCityDao
import com.example.weatherapp.data.local.entity.FavoriteCity

@Database(
    entities = [FavoriteCity::class],
    version = 1,
    exportSchema = false
)
abstract class Database: RoomDatabase() {

    abstract fun favoriteCityDao(): FavoriteCityDao

}