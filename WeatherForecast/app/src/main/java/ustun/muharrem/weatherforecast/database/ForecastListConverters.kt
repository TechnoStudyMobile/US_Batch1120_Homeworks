package ustun.muharrem.weatherforecast.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ustun.muharrem.weatherforecast.data.Forecast

class ForecastListConverters {
    @TypeConverter
    fun forecastListToString(forecastList: List<Forecast>): String {
        val type = object : TypeToken<List<Forecast>>() {}.type
        return Gson().toJson(forecastList, type)
    }

    @TypeConverter
    fun stringToForecastList(string: String): List<Forecast> {
        val type = object : TypeToken<List<Forecast>>() {}.type
        return Gson().fromJson(string, type)
    }
}