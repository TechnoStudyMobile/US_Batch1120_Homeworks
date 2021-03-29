package com.crnkic.weatherapp.view.forecastlist

import android.Manifest
import android.os.Bundle
import android.view.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.crnkic.weatherapp.R
import com.crnkic.weatherapp.data.model.ForecastContainer
import com.crnkic.weatherapp.data.model.ForecastContainerResult
import com.crnkic.weatherapp.util.NotificationUtil
import com.crnkic.weatherapp.util.PermissionUtil
import com.crnkic.weatherapp.util.Prefs
import com.crnkic.weatherapp.view.adapters.FragmentAdapter
import kotlinx.android.synthetic.main.fragment_forecast_list.*

class ForecastListFragment : Fragment() {

    private lateinit var forecastViewModel: ForecastViewModel
//    private lateinit var binding: FragmentForecastListBinding

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (isGranted) {
            getLocationDetails()
        } else {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val factory = ForecastViewModelFactory(requireActivity().application)
        forecastViewModel = ViewModelProvider(requireActivity(), factory).get(ForecastViewModel::class.java)

        forecastViewModel.getSavedForecastContainer()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        forecastViewModel.forecastContainerResultLiveData.observe(viewLifecycleOwner, Observer {
            it?.let { forecastContainerResult ->
                when (forecastContainerResult) {
                    is ForecastContainerResult.Failure -> {
                        //TODO() show error dialog (Couldn't fetch from internet)
                    }
                    ForecastContainerResult.IsLoading -> {
                        //TODO: Show loading animation
                    }
                    is ForecastContainerResult.Success -> {
                        getRecyclerList(forecastContainerResult.forecastContainer)
                        forecastContainerResult.forecastContainer.forecastList.firstOrNull()?.let { forecast ->
                            NotificationUtil.fireTodayForecastNotification(requireContext(), forecast)
                        }
                    }
                }
            }
        })

        swipe_container.setOnRefreshListener {
            swipe_container.isRefreshing = false
        }

        //ask user for permission
        askForLocationPermission()
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_forecast_list, container, false)
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

    private fun askForLocationPermission() {
        when {
            PermissionUtil.isLocationPermissionGranted(requireContext()) -> getLocationDetails()
            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
            }
            else -> requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun getLocationDetails() {
        //TODO: Get location details from GPS
        getForecastContainer()
    }

    private fun getForecastContainer() {
        val isCelsius = Prefs.retrieveIsCelsiusSetting(requireActivity())
        val days = Prefs.loadDaysSettingsValue(requireActivity())
        forecastViewModel.fetchForecastContainer(isCelsius, days)

    }


    private fun getRecyclerList(forecast: ForecastContainer) {
        val adapter = FragmentAdapter(forecast) { position ->
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

//    override fun onRefresh() {
//        val isCelsius = Prefs.retrieveIsCelsiusSetting(requireActivity())
//        val days = Prefs.loadDaysSettingsValue(requireActivity())
//        forecastViewModel.fetchForecastContainer(isCelsius, days)
//        swipe_container.isRefreshing = false
//    }
}