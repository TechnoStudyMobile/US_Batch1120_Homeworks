package com.samil.app.theweather.screen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.samil.app.theweather.R
import com.samil.app.theweather.adapter.WeatherAdapter
import com.samil.app.theweather.model.ForecastResponse
import kotlinx.android.synthetic.main.fragment_weather_forecast.*

class WeatherForecastFragment : Fragment(R.layout.fragment_weather_forecast) {

    lateinit var recyclerView: RecyclerView
    private lateinit var forecastViewModel: ForecastViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("WeatherApp", "WeatherForecastFragment is on CreateView")
        return inflater.inflate(R.layout.fragment_weather_forecast, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = forecast_recyclerView
        forecastViewModel = ViewModelProvider(this).get(ForecastViewModel::class.java)
        forecastViewModel.forecastDummyListLiveData.observe(viewLifecycleOwner, Observer {
            createWeatherList(it)
        })
        forecastViewModel.fetchForecastInfo()
    }

    private fun createWeatherList(forecastResponse: ForecastResponse) {
        val weatherAdapter = WeatherAdapter(forecastResponse)
        forecast_recyclerView.layoutManager = LinearLayoutManager(context)
        forecast_recyclerView.adapter = weatherAdapter
    }
}