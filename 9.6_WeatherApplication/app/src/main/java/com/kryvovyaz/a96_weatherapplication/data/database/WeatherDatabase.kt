package com.kryvovyaz.a96_weatherapplication.data.database

import android.app.Application
import androidx.room.*
import com.kryvovyaz.a96_weatherapplication.data.model.ForecastContainer
import com.kryvovyaz.a96_weatherapplication.util.WEATHER_ROOM_DATABASE_NAME

@Database(entities = [ForecastContainer::class], version = 1)
@TypeConverters(ForecastListConverter::class)
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