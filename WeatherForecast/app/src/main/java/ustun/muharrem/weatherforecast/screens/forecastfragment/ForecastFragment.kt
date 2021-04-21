package ustun.muharrem.weatherforecast.screens.forecastfragment

import android.Manifest
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
    private var requestLocationPermissionDismissed: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = ForecastViewModelFactory(requireActivity().application)
        forecastViewModel =
            ViewModelProvider(requireActivity(), factory).get(ForecastViewModel::class.java)
        forecastViewModel.initializeAppLangCode()

        SharedPrefs.sharedPref.registerOnSharedPreferenceChangeListener(forecastViewModel.sharedPrefListener)

        LocationUtil.initializeLocationService(requireContext())

        if (!LocationUtil.isPermissionGranted(requireContext())) {
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { requestLocationPermissionDismissed = true
//                Log.d(MY_LOG, "request permission inside")
            }.launch(Manifest.permission.ACCESS_FINE_LOCATION)
//            Log.d(MY_LOG, "request permission outside")
        }
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
                binding.textviewLocationPermission.visibility = View.GONE
                forecastViewModel.getForecastContainer()
            } else {
                forecastViewModel.locationAlertDialog(requireContext()).show()
                binding.textviewLocationPermission.visibility = View.VISIBLE
//                Log.d(MY_LOG, "on swipe to refresh permission is granted")
            }
            if(forecastViewModel.forecastContainerResultLiveData.value is ForecastContainerResult.Failure){
                binding.textviewNoAccess.visibility = View.VISIBLE
                binding.recyclerViewForecastFragment.visibility = View.GONE
                forecastViewModel.forecastContainerFailureAlertDialog(requireContext())
                    .show()
            }
            binding.swipeToRefresh.isRefreshing = false
        }

        forecastViewModel.forecastContainerResultLiveData.observe(viewLifecycleOwner, {
            it?.let { forecastContainerResult ->
                when (forecastContainerResult) {
                    is ForecastContainerResult.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.textviewNoAccess.visibility = View.GONE
                        binding.recyclerViewForecastFragment.visibility = View.VISIBLE
                        createRecyclerView(forecastContainerResult.forecastContainer)
//                        forecastContainerResult.forecastContainer.data.firstOrNull()
//                            ?.let { forecast ->
//                                NotificationUtil.fireTodayNotification(requireContext(), forecast)
//                            }

                    }
                    ForecastContainerResult.IsLoading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        //TODO Show loading animation
                    }
                    is ForecastContainerResult.Failure -> {
//                        binding.progressBar.visibility = View.VISIBLE
                        binding.textviewNoAccess.visibility = View.VISIBLE
                        binding.recyclerViewForecastFragment.visibility = View.GONE
                        forecastViewModel.forecastContainerFailureAlertDialog(requireContext())
                            .show()
                    }
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        if (LocationUtil.isPermissionGranted(requireContext())) {
            CoroutineScope(Main).launch {
                binding.textviewLocationPermission.visibility = View.GONE
                LocationUtil.subscribeToLocationUpdates()
                forecastViewModel.getForecastContainer()
//                Log.d(MY_LOG, "onResume permission is granted")
            }
        } else if (requestLocationPermissionDismissed){
            forecastViewModel.locationAlertDialog(requireContext()).show()
            binding.textviewLocationPermission.visibility = View.VISIBLE
//            Log.d(MY_LOG, "onResume permission is NOT granted")
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