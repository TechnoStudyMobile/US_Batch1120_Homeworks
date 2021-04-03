package ustun.muharrem.weatherforecast.screens.forecastfragment

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.*
import ustun.muharrem.weatherforecast.R
import ustun.muharrem.weatherforecast.data.ForecastContainer
import ustun.muharrem.weatherforecast.data.ForecastContainerResult
import ustun.muharrem.weatherforecast.databinding.FragmentForecastBinding
import ustun.muharrem.weatherforecast.screens.ForecastViewModel
import ustun.muharrem.weatherforecast.screens.ForecastViewModelFactory
import ustun.muharrem.weatherforecast.screens.adapters.ForecastListAdapter
import ustun.muharrem.weatherforecast.utilities.*
import java.util.concurrent.TimeUnit


class ForecastFragment : Fragment() {

    private var _binding: FragmentForecastBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var forecastViewModel: ForecastViewModel

    private fun locationPermissionGranted(): Boolean {
        var isGranted = ContextCompat.checkSelfPermission(
            requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (!isGranted) {
            val requestPermissionLauncher = registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { permissionGiven -> isGranted = permissionGiven }
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
        return isGranted
    }

//    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
//    private lateinit var locationRequest: LocationRequest
//    private lateinit var locationCallback: LocationCallback
//    private var currentLocation: Location? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = ForecastViewModelFactory(requireActivity().application)
        forecastViewModel =
            ViewModelProvider(requireActivity(), factory).get(ForecastViewModel::class.java)
        forecastViewModel.initializeAppLangCode()

        SharedPrefs.sharedPref.registerOnSharedPreferenceChangeListener(forecastViewModel.sharedPrefListener)

        forecastViewModel.initializeLocationService(requireContext())
//        fusedLocationProviderClient =
//            LocationServices.getFusedLocationProviderClient(requireContext())
//        locationRequest = LocationRequest.create().apply {
//            interval = TimeUnit.SECONDS.toMillis(0)
//            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
//            smallestDisplacement = ONE_KM_DISTANCE
//        }

//        locationCallback = object : LocationCallback() {
//            override fun onLocationResult(locationResult: LocationResult) {
//                super.onLocationResult(locationResult)
//                currentLocation = locationResult.lastLocation
//                currentLocation?.let {
//                    /**
//                     * Each time the activity is (re)created it subscribes for the location updates.
//                     * Even with small distances (for even a few meters) SharedPref updates.
//                     * Each time SharedPref changes, it fetches info from API, which increases the
//                     * number of calls from back-end for nothing. In order to prevent this,
//                     * SharedPrefs is not updated up to a certain distance change.
//                     * (0.01 degree change is around 1.11 km or 0.7 miles)
//                     */
//                    if (it.longitude - SharedPrefs.lon > 0.01) SharedPrefs.lon = it.longitude
//                    Log.d("WeatherApp", "longitude ${it.longitude}")
//                    Log.d("WeatherApp", "SharedPref.lon ${SharedPrefs.lon}")
//                    if (it.latitude - SharedPrefs.lat > 0.01) SharedPrefs.lat = it.latitude
//                    Log.d("WeatherApp", "latitude ${it.latitude}")
//                    Log.d("WeatherApp", "SharedPref.lat ${SharedPrefs.lat}")
//                } ?: Log.d("WeatherApp", "current location is null")
//                // TODO: Handle if current location is null !!!
//            }
//        }
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
            forecastViewModel.getForecastContainer()
            binding.swipeToRefresh.isRefreshing = false
        }

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

        // TODO: What if the permission is not granted? Manage with an alert box or snack-bar!!!
        if (locationPermissionGranted()) {
            Log.d("WeatherApp", "requestLauncher permission granted")
            forecastViewModel.subscribeToLocationUpdates()
            forecastViewModel.getForecastContainer()
            binding.recyclerViewForecastFragment.visibility = View.VISIBLE
            binding.includedLocPermLayout.layoutLocationPermission.visibility = View.GONE
        } else {
            Log.d("WeatherApp", "requestLauncher permission is NOT granted")
            binding.recyclerViewForecastFragment.visibility = View.GONE
            binding.includedLocPermLayout.layoutLocationPermission.visibility = View.VISIBLE
        }
    }

    override fun onResume() {
        super.onResume()
        if (locationPermissionGranted())
            forecastViewModel.subscribeToLocationUpdates()
    }

    override fun onPause() {
        super.onPause()
        if (locationPermissionGranted())
        forecastViewModel.unsubscribeToLocationUpdates()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


//    fun subscribeToLocationUpdates() {
//        Log.d("WeatherApp", "subscribeToLocationUpdates()")
//        try {
//            Log.d("WeatherApp", "fusedLocationProviderClient requests LocationUpdates")
//            fusedLocationProviderClient.requestLocationUpdates(
//                locationRequest, locationCallback, Looper.getMainLooper()
//            )
//        } catch (unlikely: SecurityException) {
//            Log.e("WeatherApp", "Lost location permissions. Couldn't remove updates. $unlikely")
//        }
//    }
//
//    fun unsubscribeToLocationUpdates() {
//        Log.d("WeatherApp", "unsubscribeToLocationUpdates()")
//        try {
//            val removeTask = fusedLocationProviderClient.removeLocationUpdates(locationCallback)
//            removeTask.addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    Log.d("WeatherApp", "Location Callback removed.")
//                } else {
//                    Log.d("WeatherApp", "Failed to remove Location Callback.")
//                }
//            }
//        } catch (unlikely: SecurityException) {
//            Log.e("WeatherApp", "Lost location permissions. Couldn't remove updates. $unlikely")
//        }
//    }

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
}