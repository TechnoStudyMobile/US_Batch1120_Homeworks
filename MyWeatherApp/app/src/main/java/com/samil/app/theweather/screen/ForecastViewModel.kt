package com.samil.app.theweather.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.samil.app.theweather.R
import com.samil.app.theweather.model.Forecast
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ForecastViewModel: ViewModel() {
    private val forecastList = mutableListOf<Forecast>()

    private val _forecastListLiveData = MutableLiveData<List<Forecast>>()
    val forecastListLiveData: LiveData<List<Forecast>>
    get() = _forecastListLiveData

    @RequiresApi(Build.VERSION_CODES.O)
    fun populateForecastList(){

        forecastList.add(
            Forecast(
                imageId = R.drawable.a01d,
                textViewWeekdays = getDay(0),
                textViewForecast = "Scattered Clouds",
                textViewHighTemp = "0°",
                textViewLowTemp = "-5°"
            )
        )
        forecastList.add(
            Forecast(
                imageId = R.drawable.c01d,
                textViewWeekdays = getDay(1),
                textViewForecast = "Sunny",
                textViewHighTemp = "54°",
                textViewLowTemp = "40°"
            )
        )
        forecastList.add(
            Forecast(
                imageId = R.drawable.d01d,
                textViewWeekdays = getDay(2),
                textViewForecast = "Rainy",
                textViewHighTemp = "44°",
                textViewLowTemp = "36°"
            )
        )
        forecastList.add(
            Forecast(
                imageId = R.drawable.f01d,
                textViewWeekdays = getDay(3),
                textViewForecast = "Heavy Rain",
                textViewHighTemp = "42°",
                textViewLowTemp = "38°"
            )
        )
        forecastList.add(
            Forecast(
                imageId = R.drawable.s01d,
                textViewWeekdays = getDay(4),
                textViewForecast = "Snow",
                textViewHighTemp = "0°",
                textViewLowTemp = "-10°"
            )
        )
        forecastList.add(
            Forecast(
                imageId = R.drawable.t01d,
                textViewWeekdays = getDay(5),
                textViewForecast = "Storm & Rain",
                textViewHighTemp = "43°",
                textViewLowTemp = "38°"
            )
        )
        forecastList.add(
            Forecast(
                imageId = R.drawable.t05d,
                textViewWeekdays = getDay(6),
                textViewForecast = "Heavy Storm",
                textViewHighTemp = "44°",
                textViewLowTemp = "32°"
            )
        )
        forecastList.add(
            Forecast(
                imageId = R.drawable.a01d,
                textViewWeekdays = getDay(7),
                textViewForecast = "Scattered Clouds",
                textViewHighTemp = "0°",
                textViewLowTemp = "-5°"
            )
        )
        forecastList.add(
            Forecast(
                imageId = R.drawable.c01d,
                textViewWeekdays = getDay(8),
                textViewForecast = "Sunny",
                textViewHighTemp = "54°",
                textViewLowTemp = "40°"
            )
        )
        forecastList.add(
            Forecast(
                imageId = R.drawable.d01d,
                textViewWeekdays = getDay(9),
                textViewForecast = "Rainy",
                textViewHighTemp = "44°",
                textViewLowTemp = "36°"
            )
        )
        forecastList.add(
            Forecast(
                imageId = R.drawable.f01d,
                textViewWeekdays = getDay(10),
                textViewForecast = "Heavy Rain",
                textViewHighTemp = "42°",
                textViewLowTemp = "38°"
            )
        )
        forecastList.add(
            Forecast(
                imageId = R.drawable.s01d,
                textViewWeekdays = getDay(11),
                textViewForecast = "Snow",
                textViewHighTemp = "0°",
                textViewLowTemp = "-10°"
            )
        )
        forecastList.add(
            Forecast(
                imageId = R.drawable.t01d,
                textViewWeekdays = getDay(12),
                textViewForecast = "Storm & Rain",
                textViewHighTemp = "43°",
                textViewLowTemp = "38°"
            )
        )
        forecastList.add(
            Forecast(
                imageId = R.drawable.t05d,
                textViewWeekdays = getDay(13),
                textViewForecast = "Heavy Storm",
                textViewHighTemp = "44°",
                textViewLowTemp = "32°"
            )
        )
        _forecastListLiveData.value = forecastList
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDays(days: Int): String {
        val formatter = DateTimeFormatter.ofPattern("EE, MMM yy")
        return LocalDateTime.now().plusDays(days.toLong()).format(formatter).toString()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDay(days: Int): String {
        val formatter = DateTimeFormatter.ofPattern("EEEE")
        return LocalDateTime.now().plusDays(days.toLong()).format(formatter).toString()
    }
}