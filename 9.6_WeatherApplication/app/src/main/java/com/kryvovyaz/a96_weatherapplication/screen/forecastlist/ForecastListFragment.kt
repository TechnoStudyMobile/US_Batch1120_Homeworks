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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ForecastViewModel::class.java)
        viewModel.getData()
        viewModel.listOfItemsLiveData.observe(viewLifecycleOwner, Observer {
            getRecyclerList()
        })
    }

    private fun getRecyclerList() {
        val adapter = WeatherAdapter(viewModel.listOfItemsLiveData.value) { }
        weather_recycler.layoutManager = LinearLayoutManager(context)
        weather_recycler.adapter = adapter
    }
}