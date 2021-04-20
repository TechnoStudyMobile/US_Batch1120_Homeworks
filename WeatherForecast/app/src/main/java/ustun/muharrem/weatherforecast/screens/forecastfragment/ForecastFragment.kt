package ustun.muharrem.weatherforecast.screens.forecastfragment

import android.Manifest
import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import ustun.muharrem.weatherforecast.R
import ustun.muharrem.weatherforecast.data.ForecastContainer
import ustun.muharrem.weatherforecast.data.ForecastContainerResult
import ustun.muharrem.weatherforecast.databinding.FragmentForecastBinding
import ustun.muharrem.weatherforecast.screens.ForecastViewModel
import ustun.muharrem.weatherforecast.screens.ForecastViewModelFactory
import ustun.muharrem.weatherforecast.screens.adapters.ForecastListAdapter
import ustun.muharrem.weatherforecast.utilities.*

class ForecastFragment : Fragment() {

    private var _binding: FragmentForecastBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var forecastViewModel: ForecastViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = ForecastViewModelFactory(requireActivity().application)
        forecastViewModel =
            ViewModelProvider(requireActivity(), factory).get(ForecastViewModel::class.java)
        forecastViewModel.initializeAppLangCode()

        SharedPrefs.sharedPref.registerOnSharedPreferenceChangeListener(forecastViewModel.sharedPrefListener)

        LocationUtil.initializeLocationService(requireContext())
        if (!LocationUtil.isPermissionGranted(requireContext())) requestLocationPermission()
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
            if (LocationUtil.isPermissionGranted(requireContext())) {
                forecastViewModel.getForecastContainer()
            } else {
                showLocationAlertDialog()
            }
            binding.swipeToRefresh.isRefreshing = false
        }

        forecastViewModel.forecastContainerResultLiveData.observe(viewLifecycleOwner, {
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
                        when (forecastContainerResult.error.message) {
                            DB_IS_EMPTY_MESSAGE -> {

                            }
                        }
                    }
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        if (LocationUtil.isPermissionGranted(requireContext())) {
            CoroutineScope(Main).launch {
                LocationUtil.subscribeToLocationUpdates()
                forecastViewModel.getForecastContainer()
            }
        }
        else {
            showLocationAlertDialog()
        }
    }

    override fun onPause() {
        super.onPause()
        LocationUtil.unsubscribeToLocationUpdates()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun requestLocationPermission() {
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) {}.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    private fun showLocationAlertDialog() {
        // TODO: create a custom alert dialog layout (optional)
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.location_alert_dialog_title))
        // TODO: Write a better message text
        builder.setMessage(getString(R.string.location_alert_dialog_message))
        // TODO: change the alert dialog box icon
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        // TODO: extract button names as string
        builder.setNeutralButton("OK") { _, _ -> }
        val alertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
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