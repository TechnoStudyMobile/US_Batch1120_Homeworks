package ustun.muharrem.weatherforecast.screens.forecastfragment

import android.Manifest
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import ustun.muharrem.weatherforecast.R
import ustun.muharrem.weatherforecast.data.ForecastContainer
import ustun.muharrem.weatherforecast.data.ForecastContainerResult
import ustun.muharrem.weatherforecast.databinding.FragmentForecastBinding
import ustun.muharrem.weatherforecast.screens.ForecastViewModel
import ustun.muharrem.weatherforecast.screens.ForecastViewModelFactory
import ustun.muharrem.weatherforecast.screens.adapters.ForecastListAdapter
import ustun.muharrem.weatherforecast.utilities.IS_CELSIUS_SETTING_KEY
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
            Log.d("Deneme", "requestLauncher permission granted")
            updateLocationInfo()
            binding.includedLocPermLayout.layoutLocationPermission.visibility = View.GONE
        } else {
            Log.d("Deneme", "requestLauncher permission is NOT granted")
            binding.includedLocPermLayout.layoutLocationPermission.visibility = View.VISIBLE
        }
    }

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val factory = ForecastViewModelFactory(requireActivity().application)
        forecastViewModel =
            ViewModelProvider(requireActivity(), factory).get(ForecastViewModel::class.java)
        forecastViewModel.initializeAppLangCode()
        SharedPrefs.sharedPref.registerOnSharedPreferenceChangeListener(sharedPrefListener)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
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
//            askForLocationPermission()
            updateLocationInfo()
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

//        askForLocationPermission()
        updateLocationInfo()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun askForLocationPermission() {
//        when {
//            PermissionUtil.isLocationPermissionGranted(requireContext()) -> updateLocationInfo()
////            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> { }
//            else -> requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
//        }
    }

    private fun updateLocationInfo() {
    if (ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        Log.d("Deneme", "permission granted in updateLocationInfo")
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                Log.d("Deneme", "addOnSuccessListener")
                location?.let {
                    Log.d("Deneme", "location is NOT null")
                    SharedPrefs.lon = location.longitude
                    SharedPrefs.lat = location.latitude
                } ?: Log.d("Deneme", "location NULL")

                // TODO: What if the location is null!
            }
    } else {
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }



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