package com.example.weatherapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weatherapp.base.Const

@Entity(tableName = Const.Entity.FAVORITE_CITY)
data class FavoriteCity(
    @PrimaryKey
    val id: Int
)
