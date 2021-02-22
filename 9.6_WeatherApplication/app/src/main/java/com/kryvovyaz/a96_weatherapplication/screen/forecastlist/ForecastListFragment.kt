package com.kryvovyaz.a96_weatherapplication.screen.forecastlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.kryvovyaz.a96_weatherapplication.R
import com.kryvovyaz.a96_weatherapplication.model.Forecast
import com.kryvovyaz.a96_weatherapplication.screen.view.WeatherAdapter
import kotlinx.android.synthetic.main.fragment_forecast.*

class ForecastListFragment : Fragment() {
    private lateinit var viewModel: ForecastViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_forecast, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ForecastViewModel::class.java)
        viewModel.fetchForecastInfo()
        viewModel.forecastLiveData.observe(viewLifecycleOwner, Observer {
            getRecyclerList(it)
        })
    }

    private fun getRecyclerList(forecast: Forecast) {
        val adapter = WeatherAdapter(forecast)
        weather_recycler_view.layoutManager = LinearLayoutManager(context)
        weather_recycler_view.adapter = adapter
    }
}