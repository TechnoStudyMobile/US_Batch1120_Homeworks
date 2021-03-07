package com.crnkic.weatherapp.view.forecastlist

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.crnkic.weatherapp.R
import com.crnkic.weatherapp.model.ForecastContainer
import com.crnkic.weatherapp.util.Prefs
import com.crnkic.weatherapp.view.adapters.FragmentAdapter
import kotlinx.android.synthetic.main.weather_fragment.*

class ForecastListFragment : Fragment() {

    private lateinit var forecastViewModel: ForecastViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = ForecastViewModelFactory(requireActivity().application)
        forecastViewModel =
            ViewModelProvider(requireActivity(), factory).get(ForecastViewModel::class.java)

        val isCelsius = Prefs.retrieveIsCelsiusSetting(requireActivity())
        val days = Prefs.loadDaysSettingsValue(requireActivity())
        forecastViewModel.getForecastContainer(isCelsius, days)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        forecastViewModel.forecastListLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                getRecyclerList(it)
            }
        })

    }

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
        when (item.itemId) {
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

    }

    private fun getRecyclerList(forecast: ForecastContainer) {
        val adapter = FragmentAdapter(forecast) { position ->
            //Navigate
//            val bundle = Bundle()
//            bundle.putParcelable(KEY_DAILY_FORECAST_DETAILS, forecast.forecastList[position])
//            findNavController().navigate(R.id.action_weatherFragment_to_forcastDetailsFragment, bundle)

            val direction =
                ForecastListFragmentDirections.actionWeatherFragmentToForcastDetailsFragment(
                    position
                )
            findNavController().navigate(direction)

        }
        recyclerView_fragment.layoutManager = LinearLayoutManager(context)
        recyclerView_fragment.adapter = adapter
    }
}