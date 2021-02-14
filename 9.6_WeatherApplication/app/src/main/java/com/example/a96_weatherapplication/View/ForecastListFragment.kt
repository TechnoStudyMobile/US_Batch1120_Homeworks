package com.example.a96_weatherapplication.View

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a96_weatherapplication.Data.Forecast
import com.example.a96_weatherapplication.R
import kotlinx.android.synthetic.main.recycler.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

class ForecastListFragment() : Fragment() {
    private val forecastLlist = mutableListOf<Forecast>()
    lateinit var recyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.recycler, container, false)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        populateAnimalsList()
        val adapter = WeatherAdapter(forecastLlist)
        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.weatherRecycler)
        weatherRecycler.layoutManager = layoutManager
        weatherRecycler.adapter = adapter

    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SimpleDateFormat")
    fun populateAnimalsList() {
        forecastLlist.add(
            Forecast(
                imageID = R.drawable.forecast,
                textViewWeekDay = getDay(0),
                textViewForecast = "Forecast",
                textViewFTempHight = "0°",
                textViewFTempLow = "-2°"
            )
        )
        forecastLlist.add(
            Forecast(
                imageID = R.drawable.sunny,
                textViewWeekDay = getDay(1),
                textViewForecast = "Snow",
                textViewFTempHight = "-2°",
                textViewFTempLow = "-5°"
            )
        )
        forecastLlist.add(
            Forecast(
                imageID = R.drawable.rain,
                textViewWeekDay = getDay(2),
                textViewForecast = "Rain",
                textViewFTempHight = "+35°"
            )
        )
        forecastLlist.add(
            Forecast(
                imageID = R.drawable.cloudy,
                textViewWeekDay = getDay(3),
                textViewForecast = "Cloudy",
                textViewFTempLow = "-100°"
            )
        )
        forecastLlist.add(
            Forecast(
                imageID = R.drawable.rain,
                textViewWeekDay = getDay(4),
                textViewForecast = "Rain",
                textViewFTempHight = "+3°"
            )
        )
        forecastLlist.add(
            Forecast(
                imageID = R.drawable.snow,
                textViewWeekDay = getDay(5),
                textViewForecast = "Snow",
                textViewFTempHight = "-2°",
                textViewFTempLow = "-5°"
            )
        )
        forecastLlist.add(
            Forecast(
                imageID = R.drawable.sunny,
                textViewWeekDay = getDay(6),
                textViewForecast = "Sunny",
                textViewFTempHight = "-1°",
                textViewFTempLow = "-5°"
            )
        )
        forecastLlist.add(
            Forecast(
                imageID = R.drawable.sunny,
                textViewWeekDay = getDays(7),
                textViewForecast = "Sunny",
                textViewFTempHight = "0°",
                textViewFTempLow = "-2°"
            )
        )
        forecastLlist.add(
            Forecast(
                imageID = R.drawable.snow,
                textViewWeekDay = getDays(8),
                textViewForecast = "Cloudy",
                textViewFTempHight = "-1°",
                textViewFTempLow = "-5°"
            )
        )
        forecastLlist.add(
            Forecast(
                imageID = R.drawable.cloudy,
                textViewWeekDay = getDays(9),
                textViewForecast = "Cloudy",
                textViewFTempHight = "-1°",
                textViewFTempLow = "-5°"
            )
        )
        forecastLlist.add(
            Forecast(
                imageID = R.drawable.cloudy,
                textViewWeekDay = getDays(10),
                textViewForecast = "Cloudy",
                textViewFTempHight = "-1°",
                textViewFTempLow = "-5°"
            )
        )
        forecastLlist.add(
            Forecast(
                imageID = R.drawable.cloudy,
                textViewWeekDay = getDays(11),
                textViewForecast = "Cloudy",
                textViewFTempHight = "-1°",
                textViewFTempLow = "-5°"
            )
        )
        forecastLlist.add(
            Forecast(
                imageID = R.drawable.snow,
                textViewWeekDay = getDays(12),
                textViewForecast = "Snow",
                textViewFTempHight = "-2°",
                textViewFTempLow = "-5°"
            )
        )
        forecastLlist.add(
            Forecast(
                imageID = R.drawable.sunny,
                textViewWeekDay = getDays(13),
                textViewForecast = "Sunny",
                textViewFTempHight = "-1°",
                textViewFTempLow = "-5°"
            )
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SimpleDateFormat")
    fun getDays(days: Int): String {
        var formatter = DateTimeFormatter.ofPattern("EE, MMM yy")
        return LocalDateTime.now().plusDays(days.toLong()).format(formatter).toString()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SimpleDateFormat")
    fun getDay(days: Int): String {
        var formatter = DateTimeFormatter.ofPattern("EEEE")
        return LocalDateTime.now().plusDays(days.toLong()).format(formatter).toString()

    }

}