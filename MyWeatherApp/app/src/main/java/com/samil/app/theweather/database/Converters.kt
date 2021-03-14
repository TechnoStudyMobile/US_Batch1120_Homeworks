package com.samil.app.theweather.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.samil.app.theweather.model.Forecast
import com.samil.app.theweather.model.Weather

class ForecastListConverter {

    @TypeConverter
    fun forecastListToString(forecastList: List<Forecast>): String {
        val type = object : TypeToken<List<Forecast>>() {}.type
        return Gson().toJson(forecastList, type)
    }

    @TypeConverter
    fun stringtoForecastList(string: String): List<Forecast> {
        val type = object : TypeToken<List<Forecast>>() {}.type
        return Gson().fromJson(string, type)
    }
}
