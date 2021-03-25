package ustun.muharrem.weatherforecast.screens.forecastfragment

import android.Manifest
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ustun.muharrem.weatherforecast.R
import ustun.muharrem.weatherforecast.data.ForecastContainer
import ustun.muharrem.weatherforecast.data.ForecastContainerResult
import ustun.muharrem.weatherforecast.databinding.FragmentForecastBinding
import ustun.muharrem.weatherforecast.screens.ForecastViewModel
import ustun.muharrem.weatherforecast.screens.ForecastViewModelFactory
import ustun.muharrem.weatherforecast.screens.adapters.ForecastListAdapter
import ustun.muharrem.weatherforecast.utilities.IS_CELSIUS_SETTING_KEY
import ustun.muharrem.weatherforecast.utilities.NotificationUtil
import ustun.muharrem.weatherforecast.utilities.PermissionUtil
import ustun.muharrem.weatherforecast.utilities.SharedPrefs

class ForecastFragment : Fragment() {

    private var _binding: FragmentForecastBinding? = null
    private val binding
    get() = _binding!!
    private lateinit var forecastViewModel: ForecastViewModel
    private val sharedPrefListener =
        SharedPreferences.OnSharedPreferenceChangeListener { sharedPref, key ->
            when (key) {
                IS_CELSIUS_SETTING_KEY -> forecastViewModel.fetchForecastContainer()
            }
        }
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            getLocationDetails()
            binding.includedLocPermLayout.layoutLocationPermission.visibility = View.GONE
        } else {
            binding.includedLocPermLayout.layoutLocationPermission.visibility = View.VISIBLE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = ForecastViewModelFactory(requireActivity().application)
        forecastViewModel =
            ViewModelProvider(requireActivity(), factory).get(ForecastViewModel::class.java)
        forecastViewModel.initializeAppLangCode()
        SharedPrefs.sharedPref.registerOnSharedPreferenceChangeListener(sharedPrefListener)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForecastBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.swipeToRefresh.setOnRefreshListener {
            askForLocationPermission()
            binding.swipeToRefresh.isRefreshing = false
        }

//        button_grants_location_permission.setOnClickListener {
//            location_permission_layout.visibility = View.GONE
//            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
//        }
//        button_denies_location_permission.setOnClickListener{
//            // TODO: button_denies_location_permission click listener actions
//        }

        forecastViewModel.forecastContainerResultLiveData.observe(viewLifecycleOwner, Observer {
            it?.let { forecastContainerResult ->
                when (forecastContainerResult) {
                    is ForecastContainerResult.Success -> {
                        createRecyclerView(forecastContainerResult.forecastContainer)
//                        forecastContainerResult.forecastContainer.data.firstOrNull()
//                            ?.let { forecast ->
//                                NotificationUtil.fireTodayNotification(requireContext(), forecast)
//                            }
                    }
                    ForecastContainerResult.IsLoading -> {
                        //TODO Show loading animation
                    }
                    is ForecastContainerResult.Failure -> {
                        // TODO Show error dialog (Could not fetch from internet)
                    }
                }
            }
        })

        askForLocationPermission()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun createRecyclerView(forecastContainer: ForecastContainer) {
        binding.recyclerViewForecastFragment.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewForecastFragment.adapter =
            ForecastListAdapter(forecastContainer) { position ->
                val direction =
                    ForecastFragmentDirections.actionForecastFragmentToForecastDetailsFragment(
                        position
                    )
                findNavController().navigate(direction)
            }
    }

    private fun askForLocationPermission() {
        when {
            PermissionUtil.isLocationPermissionGranted(requireContext()) -> getLocationDetails()
//            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> { }
            else -> requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun getLocationDetails() {
        // TODO Get Location details from GPS
        forecastViewModel.getForecastContainer()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings -> {
                val direction =
                    ForecastFragmentDirections.actionForecastFragmentToSettingsFragment()
                findNavController().navigate(direction)
            }
            R.id.map_location -> {
                //TODO: Open Google Maps with the user's location
            }
        }
        return super.onOptionsItemSelected(item)
    }
}