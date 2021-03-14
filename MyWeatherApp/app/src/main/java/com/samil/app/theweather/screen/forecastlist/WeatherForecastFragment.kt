package com.samil.app.theweather.screen.forecastlist

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.samil.app.theweather.R
import com.samil.app.theweather.screen.adapters.ForecastAdapter
import com.samil.app.theweather.model.ForecastContainer
import com.samil.app.theweather.screen.ForecastViewModel
import com.samil.app.theweather.screen.ForecastViewModelFactory
import com.samil.app.theweather.utils.Prefs
import kotlinx.android.synthetic.main.fragment_weather_forecast.*

class WeatherForecastFragment : Fragment() {

    private lateinit var forecastViewModel: ForecastViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = ForecastViewModelFactory(requireActivity().application)
        forecastViewModel = ViewModelProvider(requireActivity(), factory).get(ForecastViewModel::class.java)

        val isCelsius = Prefs.retrieveIsCelsiusSetting(requireActivity())
        forecastViewModel.getforecastContainer(isCelsius)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("WeatherApp", "WeatherForecastFragment is on CreateView")
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_weather_forecast, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val factory = ForecastViewModelFactory(requireActivity().application)
//        forecastViewModel =
//            ViewModelProvider(requireActivity(), factory).get(ForecastViewModel::class.java)
        forecastViewModel.forecastListLiveData.observe(viewLifecycleOwner, Observer {
            createForecastList(it)
        })
    }

    private fun createForecastList(forecastContainer: ForecastContainer) {
        val adapter = ForecastAdapter(forecastContainer) { position ->
            val direction = WeatherForecastFragmentDirections
                .actionWeatherForecastFragmentToForecastDetailsFragment(position)
            findNavController().navigate(direction)
        }
        forecast_recyclerView.layoutManager = LinearLayoutManager(context)
        forecast_recyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.weather_forecast_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings -> {
                val direction =
                    WeatherForecastFragmentDirections.actionWeatherForecastFragmentToSettingsFragment()
                findNavController().navigate(direction)
            }
            R.id.map_location -> {
                //TODO: Open Google maps with user`s location
            }
        }
        return super.onOptionsItemSelected(item)
    }
}