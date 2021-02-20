package com.crnkic.weatherapp.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.crnkic.weatherapp.R
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.weather_fragment.*

class WeatherFragment : Fragment() {

    private lateinit var viewModel: WeatherViewModel


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)
        viewModel.getData()
        viewModel.listOfItemsLiveData.observe(viewLifecycleOwner, Observer {
            getRecyclerList()

        })

    }

    fun getRecyclerList() {
        val adapter = FragmentAdapter(viewModel.listOfItems) {}
        recyclerView_fragment.layoutManager = LinearLayoutManager(context)
        recyclerView_fragment.adapter = adapter
    }

}