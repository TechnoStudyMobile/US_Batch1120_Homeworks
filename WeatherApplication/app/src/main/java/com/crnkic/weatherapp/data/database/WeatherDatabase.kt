package com.crnkic.weatherapp.data.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.WEATHER_ROOM_DATABASE_NAME
import com.crnkic.weatherapp.data.model.ForecastContainer


@Database(entities = [ForecastContainer::class], version = 1)
@TypeConverters(ForecastListConverter::class) //WeatherConverter::class
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun getForecastContainerDao(): ForecastContainerDao

    companion object {
        @Volatile
        private var INSTANCE: WeatherDatabase? = null

        fun getDatabase(application: Application): WeatherDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    application.applicationContext,
                    WeatherDatabase::class.java,
                    WEATHER_ROOM_DATABASE_NAME
                ).fallbackToDestructiveMigration().build()

                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

}