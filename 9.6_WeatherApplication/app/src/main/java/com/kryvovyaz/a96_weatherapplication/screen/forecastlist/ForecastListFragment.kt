package com.kryvovyaz.a96_weatherapplication.screen.forecastlist

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.kryvovyaz.a96_weatherapplication.ConnectionBroadcastReceiver
import com.kryvovyaz.a96_weatherapplication.ForecastViewModel
import com.kryvovyaz.a96_weatherapplication.ForecastViewModelFactory
import com.kryvovyaz.a96_weatherapplication.R
import com.kryvovyaz.a96_weatherapplication.databinding.FragmentForecastListBinding
import com.kryvovyaz.a96_weatherapplication.model.ForecastContainer
import com.kryvovyaz.a96_weatherapplication.repository.ForecastContainerResult
import com.kryvovyaz.a96_weatherapplication.screen.view.WeatherAdapter
import com.kryvovyaz.a96_weatherapplication.util.App
import com.kryvovyaz.a96_weatherapplication.util.NotificationUtil
import com.kryvovyaz.a96_weatherapplication.util.PermissionUtil


class ForecastListFragment : Fragment() {

    private var _binding: FragmentForecastListBinding? = null
    private val binding get() = _binding!!
    private lateinit var forecastViewModel: ForecastViewModel
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                getLocationDetails()
            } else {
                Snackbar.make(
                    binding.forecastListFragment,
                    "Permission denied.Can't load the data",
                    Snackbar.LENGTH_INDEFINITE
                )
                    .setAction("Give Permission") {
                        showPermissionDialog()
                    }
                    .show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = ForecastViewModelFactory(requireActivity().application)
        forecastViewModel =
            ViewModelProvider(requireActivity(), factory).get(ForecastViewModel::class.java)
        forecastViewModel.getSavedForecastContainer()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        /*
        *Connection Broadcast,will register and register itself.See Utils ConnectionBroadcastReceiver.kt
         */
        ConnectionBroadcastReceiver.registerToFragmentAndAutoUnregister(requireActivity(),
            this, object : ConnectionBroadcastReceiver() {
                override fun onConnectionChanged(hasConnection: Boolean) {
                    getForecastContainer()
                }
            })
        _binding = FragmentForecastListBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun getLocationDetails() {
        //TODO:Get location
        getForecastContainer()
    }

    private fun getForecastContainer() {
        forecastViewModel.fetchForecastContainer(App.prefs!!.icCelsius, App.prefs!!.days)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        askForLocationPermission()

        forecastViewModel.forecastContainerResultLiveData.observe(viewLifecycleOwner, Observer {
            it?.let { forecastContainerResult ->
                when (forecastContainerResult) {
                    is ForecastContainerResult.Failure -> {
                        showErrorDialog()
                    }
                    ForecastContainerResult.IsLoading -> {
                        // show animation
                    }
                    is ForecastContainerResult.Success -> {
                        createForecastList(forecastContainerResult.forecastContainer)

                        //Fire a notification
                        forecastContainerResult.forecastContainer.forecastList.firstOrNull()
                            ?.let { forecast ->
                                //in-app notification
                                NotificationUtil.fireTodayForecastNotification(
                                    requireContext(),
                                    forecast
                                )
                            }

                    }
                }
            }
        })
    }

    private fun askForLocationPermission() {
        when {
            PermissionUtil.isLocationPermissionGranted(requireContext()) -> getLocationDetails()
//            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
//                showAlertDialog()
//            }
            else -> {
                showPermissionDialog()
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    private fun createForecastList(forecastContainer: ForecastContainer) {
        val adapter =
            WeatherAdapter(
                forecastContainer,
                App.prefs!!.icCelsius
            ) { position ->
                val direction =
                    ForecastListFragmentDirections
                        .actionForecastListFragmentToForecastDetailsFragment(
                            position
                        )
                findNavController().navigate(direction)
            }
        binding.weatherRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.weatherRecyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_list_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.map_location -> Toast.makeText(
                context,
                "Search location option to be implemented",
                Toast.LENGTH_SHORT
            ).show()
            R.id.settings -> {
                val directions =
                    ForecastListFragmentDirections.actionForecastListFragmentToSettingsFragment()
                findNavController()
                    .navigate(directions)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showPermissionDialog() {
        val builder = AlertDialog.Builder(context, R.style.AlertDialogTheme)
        val view = LayoutInflater.from(context)
            .inflate(
                R.layout.layout_permission_dialog,
                requireView().findViewById(R.id.layoutDialogContainer)
            );
        builder.setView(view)
        val alertDialog = builder.create()
        view.findViewById<Button>(R.id.errorDialogButtonNo).setOnClickListener {
            Snackbar.make(
                binding.forecastListFragment,
                "Permission denied.Can't load the data",
                Snackbar.LENGTH_INDEFINITE
            )
                .setAction("Give Permission") {
                    PackageManager.PERMISSION_GRANTED
                    getLocationDetails()
                }
                .show()
            alertDialog.dismiss()
        }
        view.findViewById<Button>(R.id.errorDialogButtonYes).setOnClickListener {
            alertDialog.dismiss()
        }
        alertDialog.show()
    }

    private fun showErrorDialog() {
        val builder = AlertDialog.Builder(context, R.style.AlertDialogTheme)
        val view = LayoutInflater.from(context)
            .inflate(
                R.layout.layout_error_dialog,
                requireView().findViewById(R.id.layoutDialogContainer)
            );
        builder.setView(view)
        val alertDialog = builder.create()
        view.findViewById<Button>(R.id.errorDialogButtonOK).setOnClickListener {
            alertDialog.dismiss()
        }
        alertDialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
