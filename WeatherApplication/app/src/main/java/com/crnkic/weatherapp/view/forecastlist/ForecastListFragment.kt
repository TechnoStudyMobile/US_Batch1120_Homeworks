package com.crnkic.weatherapp.view.forecastlist

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.crnkic.weatherapp.R
import com.crnkic.weatherapp.forecastResponse.ForecastResponse
import com.crnkic.weatherapp.util.Prefs
import com.crnkic.weatherapp.view.adapters.FragmentAdapter
import kotlinx.android.synthetic.main.weather_fragment.*

class ForecastListFragment : Fragment() {

    private lateinit var forcastViewModel: WeatherViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.weather_fragment, container, false)
    }

    ////////////////////////////////////////////////////////////////////
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_list_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.settings -> {
                val direction = ForecastListFragmentDirections.actionWeatherFragmentToSettingFragment()
                findNavController().navigate(direction)
            }

            R.id.share -> {
                //TODO: Open google maps with user's location
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        forcastViewModel = ViewModelProvider(requireActivity()).get(WeatherViewModel::class.java)

        activity?.let {
            val isCelsius = Prefs.retrieveIsCelsiusSetting(it)
            val days =Prefs.retrieveDaysSetting(it)
            forcastViewModel.fetchData(isCelsius, days)
        }

        forcastViewModel.listOfItemsLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                getRecyclerList(it)
            }
        })

    }

    private fun getRecyclerList(forecast : ForecastResponse) {
        val adapter = FragmentAdapter(forcastViewModel.listOfItems) { position ->
            //Navigate
//            val bundle = Bundle()
//            bundle.putParcelable(KEY_DAILY_FORECAST_DETAILS, forecast.forecastList[position])
//            findNavController().navigate(R.id.action_weatherFragment_to_forcastDetailsFragment, bundle)

            val direction = ForecastListFragmentDirections.actionWeatherFragmentToForcastDetailsFragment(position)
            findNavController().navigate(direction)

        }
        recyclerView_fragment.layoutManager = LinearLayoutManager(context)
        recyclerView_fragment.adapter = adapter
    }
}