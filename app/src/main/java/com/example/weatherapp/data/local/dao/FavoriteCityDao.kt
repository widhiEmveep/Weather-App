package com.example.weatherapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapp.data.local.entity.FavoriteCity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteCityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavCity(favoriteCity: FavoriteCity)

    @Query("SELECT * FROM FAVORITE_CITY_ENTITY")
    fun getFavCity(): Flow<List<FavoriteCity>>

    @Query("DELETE FROM FAVORITE_CITY_ENTITY WHERE cityName = :cityName")
    suspend fun deleteCity(cityName: String)

}