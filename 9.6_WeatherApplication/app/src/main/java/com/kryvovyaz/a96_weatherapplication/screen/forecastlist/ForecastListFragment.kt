package com.kryvovyaz.a96_weatherapplication.screen.forecastlist

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import android.util.Log
import android.view.*
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.*
import com.google.android.material.snackbar.Snackbar
import com.kryvovyaz.a96_weatherapplication.util.ConnectionBroadcastReceiver
import com.kryvovyaz.a96_weatherapplication.ForecastViewModel
import com.kryvovyaz.a96_weatherapplication.ForecastViewModelFactory
import com.kryvovyaz.a96_weatherapplication.R
import com.kryvovyaz.a96_weatherapplication.databinding.FragmentForecastListBinding
import com.kryvovyaz.a96_weatherapplication.data.model.ForecastContainer
import com.kryvovyaz.a96_weatherapplication.data.repository.ForecastContainerResult
import com.kryvovyaz.a96_weatherapplication.screen.view.WeatherAdapter
import com.kryvovyaz.a96_weatherapplication.App
import com.kryvovyaz.a96_weatherapplication.util.NotificationUtil


class ForecastListFragment : Fragment() {
    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null
    private var mLastKnownLocation: Location? = null
    private var _binding: FragmentForecastListBinding? = null
    private val binding get() = _binding!!
    private lateinit var forecastViewModel: ForecastViewModel
    private var locationCallback: LocationCallback? = null
    // private val requestPermissionLauncher =
//        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
//            if (isGranted) {
//                Log.d("myApp",":Permission is Granted Getting  location details")
//                getLocationDetails()
//            } else {
//                Snackbar.make(
//                    binding.forecastListFragment,
//                    "Permission denied.Can't load the data",
//                    Snackbar.LENGTH_INDEFINITE
//                )
//                    .setAction("Give Permission") {
////                        .setPositiveButton("App Settings", new DialogInterface.OnClickListener() {
////                        @Override public void onClick(DialogInterface dialogInterface, int i) {
////                            Intent intent = new Intent();
////                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
////                            Uri uri = Uri.fromParts("package", context.getApplicationContext().getPackageName(), null);
////                            intent.setData(uri);
////                            context.startActivity(intent);
////                            dismiss();
//                      //  }
//                    }
//                    .show()
//            }
//        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // askForLocationPermission()
        requestLocationPermission()
        val factory = ForecastViewModelFactory(requireActivity().application)
        mFusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())
        forecastViewModel =
            ViewModelProvider(requireActivity(), factory).get(ForecastViewModel::class.java)
        forecastViewModel.getSavedForecastContainer()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        ConnectionBroadcastReceiver.registerToFragmentAndAutoUnregister(
            requireActivity(),
            this,
            object : ConnectionBroadcastReceiver() {
                override fun onConnectionChanged(hasConnection: Boolean) {
                    if (hasConnection) {
                        Log.d("myApp", "Getting forecast container if has connection")
                        // getForecastContainer()
                    }
                }
            })
        _binding = FragmentForecastListBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun getForecastContainer() {
        Log.d(
            "myApp",
            "ForecastListFragment getting device location inside of getForecastContainer() "
        )
        if (App.prefs!!.isPhoneLocations) {
            getDeviceLocation()
        }

        App.prefs!!.longtitude?.let {
            App.prefs!!.lattitude?.let { it1 ->
                forecastViewModel.fetchForecastContainer(
                    App.prefs!!.icCelsius, App.prefs!!.days,
                    it1, it
                )
            }
            Log.d(
                "myApp",
                "getForecastContainer(),Lat: ${App.prefs!!.lattitude} +Long: ${App.prefs!!.longtitude}"
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

                        Log.d(
                            "myApp",
                            "ForecastListFragment ForecastContainer ,result success,creating ForecastList"
                        )
                        createForecastList(forecastContainerResult.forecastContainer)

                        //Fire a notification
                        if (App.prefs!!.isNotofication) {
                            Log.d("myApp", "ForecastListFragment notifications enabled")
                            forecastContainerResult.forecastContainer.forecastList.firstOrNull()
                                ?.let { forecast ->
                                    //in-app notification
                                    NotificationUtil.fireTodayForecastNotification(
                                        requireContext(),
                                        forecast
                                    )
                                }
                        } else Log.d("myApp", "ForecastListFragment notifications disabled")
                    }
                }
            }
        })
    }

//    private fun askForLocationPermission() {
//        when {
//            PermissionUtil.isLocationPermissionGranted(requireContext()) -> getLocationDetails()
////            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
////                showAlertDialog()
////            }
//            else -> {
//
//                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
//            }
//        }
//    }

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
            R.id.settings -> {
                val directions =
                    ForecastListFragmentDirections.actionForecastListFragmentToSettingsFragment()
                findNavController()
                    .navigate(directions)
            }
        }
        return super.onOptionsItemSelected(item)
    }

//    private fun showPermissionDialog() {
//        val builder = AlertDialog.Builder(context, R.style.AlertDialogTheme)
//        val view = LayoutInflater.from(context)
//            .inflate(
//                R.layout.layout_permission_dialog,
//                requireView().findViewById(R.id.layoutDialogContainer)
//            );
//        builder.setView(view)
//        val alertDialog = builder.create()
//        view.findViewById<Button>(R.id.errorDialogButtonNo).setOnClickListener {
//            Snackbar.make(
//                binding.forecastListFragment,
//                "Permission denied.Can't load the data",
//                Snackbar.LENGTH_INDEFINITE
//            )
//                .setAction("Give Permission") {
//                    PackageManager.PERMISSION_GRANTED
//                    getLocationDetails()
//                }
//                .show()
//            alertDialog.dismiss()
//        }
//        view.findViewById<Button>(R.id.errorDialogButtonYes).setOnClickListener {
//            alertDialog.dismiss()
//        }
//        alertDialog.show()
//    }

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

    @SuppressLint("MissingPermission")
    private fun getDeviceLocation() {//add saved to prefs lat and long
        mFusedLocationProviderClient?.lastLocation?.addOnCompleteListener(requireActivity()) { task ->
            if (task.isSuccessful && task.result != null) {
                mLastKnownLocation = task.result
                App.prefs!!.lattitude = (mLastKnownLocation)!!.latitude.toString()
                App.prefs!!.longtitude = (mLastKnownLocation)!!.longitude.toString()
                Log.d("myApp", "lattitude is ${App.prefs!!.lattitude}")
                Log.d("myApp", "longtitude is ${App.prefs!!.longtitude}")
            } else {
                Log.d(
                    "myApp",
                    "ForecastListFragment location is not successful .Making a new request"
                )
                val locationRequest = LocationRequest.create()
                locationRequest.interval = 10000
                locationRequest.fastestInterval = 5000
                locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

                locationCallback = object : LocationCallback() {
                    override fun onLocationResult(locationResult: LocationResult?) {
                        if (locationResult == null) {
                            Log.d("myApp", "ForecastListFragment location is not successful")
                            return
                        }
                        Log.d("myApp", "ForecastListFragment location is successful")
                        mLastKnownLocation = locationResult.lastLocation
                        Log.d("myApp", "ForecastListFragment removing location updates")
                        mFusedLocationProviderClient?.run {
                            removeLocationUpdates(
                                locationCallback!!
                            )
                        }
                    }
                }
                mFusedLocationProviderClient?.run {
                    locationRequest.interval = 10000
                    locationRequest.fastestInterval = 5000
                    locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

                    locationCallback = object : LocationCallback() {
                        override fun onLocationResult(locationResult: LocationResult?) {
                            if (locationResult == null) {
                                return
                            }
                            mLastKnownLocation = locationResult.lastLocation

                        }
                    }
                    Log.d("myApp", "ForecastListFragment requesting location update")
                    requestLocationUpdates(
                        locationRequest,
                        locationCallback!!,
                        Looper.getMainLooper()
                    )
                }
            }
        }
    }

    companion object {
        private const val REQUEST_LOCATION_PERMISSION = 1
    }

    private val locationPermission = ACCESS_FINE_LOCATION
//    private fun checkLocationPermission(): Boolean {
//
//        locationPermissions.forEach {
//            if (ContextCompat.checkSelfPermission(context!!, it) != PackageManager.PERMISSION_GRANTED) {
//                return false
//            }
//        }
//
//        return (ContextCompat.checkSelfPermission(
//            requireContext(),
//            locationPermission
//        ) == PackageManager.PERMISSION_GRANTED)
//    }

    private fun requestLocationPermission() {
        // optional implementation of shouldShowRequestPermissionRationale
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                locationPermission
            )
        ) {
            AlertDialog.Builder(requireContext())
                .setMessage("Need location permission to get current place")
                .setPositiveButton(android.R.string.ok) { _, _ ->
                    // ActivityCompat.requestPermissions(activity!!, locationPermissions, REQUEST_LOCATION_PERMISSION)
                    requestPermissions(arrayOf(locationPermission), REQUEST_LOCATION_PERMISSION)
                }
                .setNegativeButton(android.R.string.cancel, null)
                .show()
        } else {
            // ActivityCompat.requestPermissions(activity!!, locationPermissions, REQUEST_LOCATION_PERMISSION)
            requestPermissions(arrayOf(locationPermission), REQUEST_LOCATION_PERMISSION)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                App.prefs!!.isPermissionGranted = true
                getForecastContainer()
            } else {
                Log.d("myApp", "Location permission denied")

                val isNeverAskAgain = !ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    locationPermission
                )

                if (isNeverAskAgain) {
                    Snackbar.make(
                        binding.forecastListFragment,
                        "Location Permission required",
                        Snackbar.LENGTH_LONG
                    )
                        .setAction("Settings") {
                            Intent(
                                ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.parse("package:${requireActivity().packageName}")
                            ).apply {
                                addCategory(Intent.CATEGORY_DEFAULT)
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(this)
                            }
                        }
                        .show()
                } else {
                    Log.d("myApp", "Location permission denied")
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getForecastContainer()
    }
}

