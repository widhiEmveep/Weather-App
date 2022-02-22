package com.example.weatherapp.di

import android.content.Context
import androidx.room.Room
import com.example.weatherapp.base.Const
import com.example.weatherapp.data.local.Database
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            Database::class.java,
            Const.Entity.DATABASE_NAME)
            .fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideFavoriteCityDao(database: Database) = database.favoriteCityDao()
}