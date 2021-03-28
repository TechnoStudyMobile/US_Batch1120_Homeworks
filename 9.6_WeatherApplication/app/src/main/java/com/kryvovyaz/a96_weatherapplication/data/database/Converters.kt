package com.kryvovyaz.a96_weatherapplication.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kryvovyaz.a96_weatherapplication.data.model.Forecast

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