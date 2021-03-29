package com.crnkic.weatherapp.data.database

import androidx.room.TypeConverter
import com.crnkic.weatherapp.data.model.Forecast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class WeatherConverter {
//    @TypeConverter
//    fun weatherToString(weather: Weather): String = Gson().toJson(weather)
//
//    @TypeConverter
//    fun stringToWeather(string: String): Weather = Gson().fromJson(string, Weather::class.java)
}

class ForecastListConverter {
    @TypeConverter
    fun forecastListToString(forecastList: List<Forecast>): String {
        val type = object : TypeToken<List<Forecast>>() {}.type
        return Gson().toJson(forecastList, type)
    }

    @TypeConverter
    fun stringToForecastList(forecastList: String): List<Forecast> {
        val type = object : TypeToken<List<Forecast>>() {}.type
        return Gson().fromJson(forecastList, type)
    }
}