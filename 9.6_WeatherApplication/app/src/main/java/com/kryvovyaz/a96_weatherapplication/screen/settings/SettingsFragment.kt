package com.kryvovyaz.a96_weatherapplication.screen.settings

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Typeface
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.messaging.Constants
import com.kryvovyaz.a96_weatherapplication.App
import com.kryvovyaz.a96_weatherapplication.R
import com.kryvovyaz.a96_weatherapplication.databinding.*
import com.kryvovyaz.a96_weatherapplication.util.PLACES_API_KEY
import com.mancj.materialsearchbar.MaterialSearchBar
import com.mancj.materialsearchbar.adapter.SuggestionsAdapter
import com.kryvovyaz.a96_weatherapplication.util.REQUEST_LOCATION_PERMISSION

class SettingsFragment : Fragment(), AdapterView.OnItemSelectedListener, OnMapReadyCallback,
    MaterialSearchBar.OnSearchActionListener {
    private var placesClient: PlacesClient? = null
    private var predictionList = listOf<AutocompletePrediction>()
    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null
    private var mLastKnownLocation: Location? = null
    private var locationCallback: LocationCallback? = null
    private var mMap: GoogleMap? = null
    private val DEFAULT_ZOOM = 10F
    private var mapView: View? = null
    private var latlng: LatLng? = null
    private var _binding: FragmentSettingsBinding? = null
    private var _bindingDays: LayoutSettingsDaysBinding? = null
    private var _bindingUnits: LayoutSettingsUnitBinding? = null
    private var _bindingNotification: LayoutSettingsNotificationBinding? = null
    private var _bindingLocation: LayoutSettingsLocationBinding? = null
    private var _bindingContentMap: ContentMapBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        _bindingDays = binding.settingDays
        _bindingUnits = binding.settingsUnits
        _bindingNotification = binding.settingsNotifications
        _bindingLocation = binding.settingsLocation
        _bindingContentMap = binding.settingsMap
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        val supportMapFragment = mapFragment as? SupportMapFragment
        supportMapFragment?.getMapAsync(this)
        mapView = mapFragment.view
        if (mapView != null) {
            Log.d("myApp", "Settings Fragment mapView is not  null")
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        loadSpinnerSelectedDays()
        setDegreeViews()
        setLocationSwitchPosition()
        seNotificationSwitchPosition()
        setMapItemsVisibility()
        setLocationSubtitle()
        showDeviceLocation()
        // _bindingLocation?.locationSettingsSwitch?.isEnabled = App.prefs!!.isPermissionGranted
//        val latandlong = App.prefs!!.longtitude?.let { it1 ->
//            App.prefs!!.lattitude?.let { it2 ->
//                LatLng(
//                    it2.toDouble(),
//                    it1.toDouble()
//                )
//            }
//        }
//
//        mMap?.moveCamera(
//            CameraUpdateFactory.newLatLngZoom(
//                latandlong,
//                DEFAULT_ZOOM
//            )
//        )

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        //map
        _bindingLocation?.locationSettingsSwitch?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                App.prefs!!.isPhoneLocations = true
                getDeviceLocation()
                setLocationSubtitle()
                setMapItemsVisibility()
                showDeviceLocation()
            } else {
                setMapItemsVisibility()
            }

        }
        _bindingNotification?.notificationSettingsSwitch?.setOnCheckedChangeListener { _, isChecked ->
            App.prefs!!.isNotofication = isChecked}
        // _bindingLocation?.locationSettingsSwitch?.isEnabled = App.prefs!!.isPermissionGranted
        //enable fuse location
        mFusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())
        //enable  fuse GPS
        context?.let { Places.initialize(it, PLACES_API_KEY) }
        placesClient = context?.let { Places.createClient(it) }
        if (placesClient != null) {
            Log.d("myApp", " Settings Fragment Places client is not null")
        }

        val token = AutocompleteSessionToken.newInstance()
        _bindingContentMap?.searchBar?.setOnSearchActionListener(this)
        _bindingContentMap?.searchBar?.addTextChangeListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                val predictionsRequest = FindAutocompletePredictionsRequest.builder()
                    .setTypeFilter(TypeFilter.CITIES).setSessionToken(token)
                    .setQuery(charSequence.toString()).build()
                placesClient!!.findAutocompletePredictions(predictionsRequest)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val predictionsResponse = task.result
                            if (predictionsResponse != null) {
                                predictionList = predictionsResponse.autocompletePredictions
                                val suggestionsList: MutableList<String> = mutableListOf()
                                for (i in predictionList.indices) {
                                    val prediction = predictionList[i]
                                    suggestionsList.add(prediction.getFullText(null).toString())
                                }
                                _bindingContentMap?.searchBar?.updateLastSuggestions(suggestionsList)
                                if (!_bindingContentMap?.searchBar?.isSuggestionsVisible!!) {
                                    _bindingContentMap?.searchBar?.showSuggestionsList()
                                }

                            } else {
                                Toast.makeText(
                                    context,
                                    "Can't find your city",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            Toast.makeText(
                                context,
                                "No internet connection.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }


            }

            override fun afterTextChanged(editable: Editable) {}
        })
        _bindingContentMap?.searchBar?.setSuggstionsClickListener(object :
            SuggestionsAdapter.OnItemViewClickListener {
            override fun OnItemClickListener(position: Int, v: View?) {
                if (position >= predictionList.size) {
                    return
                }
                val selectedPrediction = predictionList[position]
                val suggestion =
                    _bindingContentMap?.searchBar?.lastSuggestions?.get(position)?.toString()
                _bindingContentMap?.searchBar?.text = suggestion
                Handler(Looper.getMainLooper()).postDelayed({
                    _bindingContentMap?.searchBar?.clearSuggestions()
                }, 1000)

                val inputManager =
                    requireActivity().getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
                inputManager.hideSoftInputFromWindow(
                    _bindingContentMap?.searchBar?.windowToken,
                    InputMethodManager.HIDE_IMPLICIT_ONLY
                )
                val placeId = selectedPrediction.placeId
                //here we get lat and long from google by ID

                val cityField = listOf(Place.Field.LAT_LNG)

                val fetchCityRequest = FetchPlaceRequest.builder(placeId, cityField).build()
                placesClient?.fetchPlace(fetchCityRequest)
                    ?.addOnSuccessListener { fetchPlaceResponse ->
                        val city = fetchPlaceResponse.place
                        val latlong = city.latLng////here we have LAT AND LONG

                        _bindingContentMap?.btnSaveLocation?.setOnClickListener {
                            if (latlong != null) {
                                App.prefs!!.lattitude = latlong.latitude.toString()
                                App.prefs!!.longtitude = latlong.longitude.toString()
                                _bindingContentMap?.searchBar?.disableSearch()
                                setLocationSubtitle()
                                App.prefs!!.isPhoneLocations = false
                            }
                        }


                        latlong.let {
                            mMap?.moveCamera(
                                CameraUpdateFactory.newLatLngZoom(
                                    it,
                                    DEFAULT_ZOOM
                                )
                            )
                        }
                    }?.addOnFailureListener {
                        if (it is ApiException) {
                            val apiException = it.printStackTrace()
                            Log.d(
                                "myApp",
                                "Settings fragment place not found " + it.message + " " + it.statusCode
                            )
                        }
                    }
            }

            override fun OnItemDeleteListener(position: Int, v: View?) {}
        })
        loadSpinnerSelectedDays()
        _bindingDays?.daySettingsSpinner?.onItemSelectedListener = this
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.days_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            _bindingDays?.daySettingsSpinner?.adapter = adapter
        }
    }

    override fun onMapReady(p0: GoogleMap?) {
        mMap = p0
        mMap?.uiSettings?.isMyLocationButtonEnabled = false
        mMap?.uiSettings?.isZoomControlsEnabled = false;
        mMap?.setMaxZoomPreference(10F)

        //check if gps is enabled or not and then request user to enable it
//        val locationRequest = LocationRequest.create()
//        locationRequest.interval = 10000
//        locationRequest.fastestInterval = 5000
//        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
//        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
//        val settingsClient = LocationServices.getSettingsClient(requireContext())
//        val task = settingsClient.checkLocationSettings(builder.build())

//        task.addOnSuccessListener {
//            Log.d("myApp", "response is ok")
        Log.d("myApp", "Fragment settings showing device location on map ready")
        showDeviceLocation()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (requestCode == AppCompatActivity.RESULT_OK) {
                getDeviceLocation()
                Log.d("myApp", "Settings Fragment getting device location on result")
            }
        }
    }

    private fun getDeviceLocation() {

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestLocationPermission()
            return
        }
        mFusedLocationProviderClient?.lastLocation?.addOnCompleteListener {
            if (it.isSuccessful) {
                mLastKnownLocation = it.result
                Log.d(
                    "myApp", "Fragment Settings  location is : " +
                            "${(mLastKnownLocation)!!.latitude.toString()}, + ${(mLastKnownLocation)!!.longitude.toString()}"
                )
                App.prefs!!.lattitude = (mLastKnownLocation)!!.latitude.toString()
                App.prefs!!.longtitude = (mLastKnownLocation)!!.longitude.toString()


                if (mLastKnownLocation != null) {
                    Log.d("myApp", "Fragment Settings known location is not null")
                    latlng =
                        LatLng(this.mLastKnownLocation!!.latitude, mLastKnownLocation!!.longitude)

                    Log.d(
                        "myApp",
                        "Fragment Settings moving camera to show location inside of getDeviceLocation() "
                    )

                    mMap?.moveCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            latlng,
                            DEFAULT_ZOOM
                        )
                    )

                } else {
                    Log.d(
                        "myApp",
                        "Fragment Settings known location is null in method getDeviceLocation()"
                    )
                    val locationRequest = LocationRequest.create()
                    locationRequest.interval = 10000
                    locationRequest.fastestInterval = 5000
                    locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

                    locationCallback = object : LocationCallback() {
                        override fun onLocationResult(locationResult: LocationResult?) {
                            if (locationResult == null) {
                                return
                            }

                            mLastKnownLocation = locationResult.lastLocation
                            App.prefs!!.lattitude = (mLastKnownLocation)!!.latitude.toString()
                            App.prefs!!.longtitude = (mLastKnownLocation)!!.longitude.toString()
                            mMap?.moveCamera(
                                CameraUpdateFactory.newLatLngZoom(
                                    latlng,
                                    DEFAULT_ZOOM
                                )
                            )
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
                                App.prefs!!.lattitude = (mLastKnownLocation)!!.latitude.toString()
                                App.prefs!!.longtitude = (mLastKnownLocation)!!.longitude.toString()
                                mMap?.moveCamera(
                                    CameraUpdateFactory.newLatLngZoom(
                                        latlng,
                                        DEFAULT_ZOOM
                                    )
                                )
                            }
                        }
                        Log.d("myApp", "Fragment Settings requesting location update")
                        requestLocationUpdates(locationRequest, locationCallback!!, null)
                    }
                }
                setLocationSubtitle()
            } else {
                Toast.makeText(context, "Unable to get last location", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun showDeviceLocation() {
        val latandlong = App.prefs!!.longtitude?.let { it1 ->
            App.prefs!!.lattitude?.let { it2 ->
                LatLng(
                    it2.toDouble(),
                    it1.toDouble()
                )
            }
        }

        mMap?.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                latandlong,
                DEFAULT_ZOOM
            )
        )
    }

    private fun getAddress(): String {
        val geocoder = Geocoder(context)
        val list = App.prefs!!.lattitude?.let {
            App.prefs!!.longtitude?.let { it1 ->
                geocoder.getFromLocation(
                    it.toDouble(),
                    it1.toDouble(), 1
                )
            }
        }
        return if (list != null) "${list[0]?.locality}" else ""
    }

    private fun initViews() {
        setSettingsTitles()
        setSettingsSubtitles()
        setClickListeners()
        setLocationSwitchPosition()
        seNotificationSwitchPosition()
        setMapItemsVisibility()

    }

    private fun setMapItemsVisibility() {
        if (_bindingLocation?.locationSettingsSwitch?.isChecked == true) {
            _bindingContentMap?.searchBar?.visibility = View.INVISIBLE
            _bindingContentMap?.btnSaveLocation?.visibility = View.INVISIBLE
        } else {
            _bindingContentMap?.searchBar?.visibility = View.VISIBLE
            _bindingContentMap?.btnSaveLocation?.visibility = View.VISIBLE


        }
    }

    private fun setClickListeners() {
        _bindingUnits?.celsiusDegreeTextView?.setOnClickListener { celsius_degree ->
            App.prefs?.icCelsius = true
            setDegreeViews()
            setUnitSubtitle()
        }
        _bindingUnits?.fahrenheitDegreeTextView?.setOnClickListener {
            App.prefs?.icCelsius = false
            setDegreeViews()
            setUnitSubtitle()
        }
    }

    private fun setSettingsTitles() {
        _bindingNotification?.notificationSettingsItem?.settingsName?.text =
            getString(R.string.weather_notification_setting_title)
        _bindingUnits?.unitsSettingsItem?.settingsName?.text =
            getString(R.string.units_settings_label)
        _bindingDays?.daysSettingsItem?.settingsName?.text = getString(R.string.days_setting_title)
        _bindingLocation?.locationSettingsItem?.settingsName?.text =
            getString(R.string.settings_location_title)
    }

    private fun setDegreeViews() {

        if (App.prefs!!.icCelsius) {
            _bindingUnits?.celsiusDegreeTextView?.setTextColor(
                requireActivity()
                    .resources.getColor(R.color.bar_header)
            )
            _bindingUnits?.fahrenheitDegreeTextView?.setTextColor(Color.GRAY)
            _bindingUnits?.celsiusDegreeTextView?.typeface = Typeface.DEFAULT_BOLD
            _bindingUnits?.fahrenheitDegreeTextView?.typeface = Typeface.DEFAULT
        } else {
            _bindingUnits?.celsiusDegreeTextView?.setTextColor(Color.GRAY)
            _bindingUnits?.fahrenheitDegreeTextView?.setTextColor(
                requireActivity()
                    .resources.getColor(R.color.bar_header)
            )
            _bindingUnits?.celsiusDegreeTextView?.typeface = Typeface.DEFAULT
            _bindingUnits?.fahrenheitDegreeTextView?.typeface = Typeface.DEFAULT_BOLD
        }
    }

    private fun setLocationSwitchPosition() {
        _bindingLocation?.locationSettingsSwitch?.isChecked = App.prefs!!.isPhoneLocations
    }

    private fun seNotificationSwitchPosition() {
        _bindingNotification?.notificationSettingsSwitch?.isChecked = App.prefs!!.isNotofication
    }

    private fun setSettingsSubtitles() {
        setUnitSubtitle()
        setDaysSubtitle()
        setLocationSubtitle()
    }

    private fun setLocationSubtitle() {
        _bindingLocation?.locationSettingsItem?.settingsValue?.text = getAddress()
    }

    private fun setDaysSubtitle() {
        _bindingDays?.daysSettingsItem?.settingsValue?.text =
            getString(R.string.days_selected).plus(
                resources.getString(
                    R.string.space
                )
            ).plus(if (App.prefs!!.days == 14) 14 else 7)

    }

    private fun setUnitSubtitle() {
        _bindingUnits?.unitsSettingsItem?.settingsValue?.text = if (App.prefs!!.icCelsius) {
            getString(R.string.degree_celsius)
        } else {
            getString(R.string.degree_fahrenheit)
        }
    }

    private fun loadSpinnerSelectedDays() {
        _bindingDays?.daySettingsSpinner?.setSelection(if (App.prefs!!.days == 14) 0 else 1)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        let {
            (parent?.getChildAt(0) as TextView)
                .setTextColor(resources.getColor(R.color.bar_header))
            App.prefs?.days = if (position == 0) 14 else 7
            setDaysSubtitle()
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        _bindingLocation = null
        _bindingNotification = null
        _bindingDays = null
        _bindingUnits = null
    }
    override fun onSearchStateChanged(enabled: Boolean) {}

    override fun onSearchConfirmed(text: CharSequence?) {}

    override fun onButtonClicked(buttonCode: Int) {
        if (buttonCode == MaterialSearchBar.BUTTON_NAVIGATION) {
        } else if (buttonCode == MaterialSearchBar.BUTTON_BACK) {
            _bindingContentMap?.searchBar?.disableSearch()
        }
    }



    private val locationPermission = Manifest.permission.ACCESS_FINE_LOCATION
    private fun checkLocationPermission(): Boolean {
        /*
        locationPermissions.forEach {
            if (ContextCompat.checkSelfPermission(context!!, it) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
         */
        return (ContextCompat.checkSelfPermission(
            requireContext(),
            locationPermission
        ) == PackageManager.PERMISSION_GRANTED)
    }

    private fun requestLocationPermission() {
        // optional implementation of shouldShowRequestPermissionRationale
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                locationPermission
            )
        ) {
            AlertDialog.Builder(requireContext())
                .setMessage("Need location permission to get current location")
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

                getDeviceLocation()
            } else {
                Log.d("myApp", "Fragment Settings Location permission denied")

                val isNeverAskAgain = !ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    locationPermission
                )

                if (isNeverAskAgain) {
                    binding.fragmentSettings?.rootView?.let {
                        Snackbar.make(
                            it,
                            "Permission denied.Can't load the data",
                            Snackbar.LENGTH_INDEFINITE
                        )
                            .setAction("Settings") {
                                Intent(
                                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                    Uri.parse("package:${requireActivity().packageName}")
                                ).apply {
                                    addCategory(Intent.CATEGORY_DEFAULT)
                                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                    startActivity(this)
                                }
                            }
                            .show()
                    }

                } else {
                    Log.d("myApp", "Fragment Settings Location permission denied")
                }
            }
        }
    }
}