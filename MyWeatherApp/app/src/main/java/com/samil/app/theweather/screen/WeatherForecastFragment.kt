package com.samil.app.theweather.screen

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.samil.app.theweather.R
import com.samil.app.theweather.adapter.WeatherAdapter
import com.samil.app.theweather.model.Forecast
import kotlinx.android.synthetic.main.fragment_weather_forecast.*

class WeatherForecastFragment : Fragment(R.layout.fragment_weather_forecast) {

    lateinit var recyclerView: RecyclerView
    private lateinit var forecastViewModel: ForecastViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("MyApp", "WeatherForecastFragment is on CreateView")
        return inflater.inflate(R.layout.fragment_weather_forecast, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = forecast_recyclerView
        forecastViewModel = ViewModelProvider(this).get(ForecastViewModel::class.java)
        forecastViewModel.forecastListLiveData.observe(viewLifecycleOwner, Observer {
            createWeatherList(it)
        })

        forecastViewModel.populateForecastList()
    }

    fun createWeatherList(list: List<Forecast>) {
        val weatherAdapter = WeatherAdapter(list)
        val layoutManager = LinearLayoutManager(context)
        forecast_recyclerView.layoutManager = layoutManager
        forecast_recyclerView.adapter = weatherAdapter
    }
}