package ustun.muharrem.weatherforecast.screens

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.*
import com.google.android.gms.location.*
import kotlinx.coroutines.launch
import ustun.muharrem.weatherforecast.data.ForecastContainerResult
import ustun.muharrem.weatherforecast.database.ForecastDatabase
import ustun.muharrem.weatherforecast.database.ForecastLocalDataService
import ustun.muharrem.weatherforecast.repository.ForecastContainerRepository
import ustun.muharrem.weatherforecast.utilities.*
import java.util.*
import java.util.concurrent.TimeUnit

class ForecastViewModel(private val forecastContainerRepository: ForecastContainerRepository) :
    ViewModel() {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private var currentLocation: Location? = null

    private val _forecastContainerResultLiveData =
        forecastContainerRepository.forecastContainerResultLiveData
    val forecastContainerResultLiveData: LiveData<ForecastContainerResult>
        get() = _forecastContainerResultLiveData

    val sharedPrefListener =
        SharedPreferences.OnSharedPreferenceChangeListener { sharedPref, key ->
            when (key) {
                LONGITUDE_KEY,
                LATITUDE_KEY,
                IS_CELSIUS_SETTING_KEY -> fetchForecastContainer()
            }
        }

    fun initializeAppLangCode() {
        if (SharedPrefs.langCode == null) {
            SharedPrefs.langCode = when (Locale.getDefault().language) {
                "tr" -> "tr"
                else -> "en"
            }
        }
    }

//    fun locationPermissionGranted(context: Context): Boolean {
//        var isGranted = ContextCompat.checkSelfPermission(
//            context, Manifest.permission.ACCESS_FINE_LOCATION
//        ) == PackageManager.PERMISSION_GRANTED
//
//        if (!isGranted) {
//            val requestPermissionLauncher = registerForActivityResult(
//                ActivityResultContracts.RequestPermission()
//            ) { permissionGiven -> isGranted = permissionGiven }
//            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
//        }
//        return isGranted
//    }

    fun initializeLocationService(context: Context) {
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(context)

        locationRequest = LocationRequest.create().apply {
            interval = TimeUnit.SECONDS.toMillis(0)
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            smallestDisplacement = ONE_KM_DISTANCE
        }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                currentLocation = locationResult.lastLocation
                currentLocation?.let {
                    /**
                     * Each time the activity is (re)created it subscribes for the location updates.
                     * Even with small distances (for even a few meters) SharedPref updates.
                     * Each time SharedPref changes, it fetches info from API, which increases the
                     * number of calls from back-end for nothing. In order to prevent this,
                     * SharedPrefs is not updated up to a certain distance change.
                     * (0.01 degree change is around 1.11 km or 0.7 miles)
                     */
                    if (Math.abs(it.longitude - SharedPrefs.lon) > 0.01) SharedPrefs.lon = it.longitude
                    Log.d("WeatherApp", "longitude ${it.longitude}")
                    Log.d("WeatherApp", "SharedPref.lon ${SharedPrefs.lon}")
                    if (Math.abs(it.latitude - SharedPrefs.lat) > 0.01) SharedPrefs.lat = it.latitude
                    Log.d("WeatherApp", "latitude ${it.latitude}")
                    Log.d("WeatherApp", "SharedPref.lat ${SharedPrefs.lat}")
                } ?: Log.d("WeatherApp", "current location is null")
                // TODO: Handle if current location is null !!!
            }
        }
    }

    fun subscribeToLocationUpdates() {
        Log.d("WeatherApp", "subscribeToLocationUpdates()")
        try {
            Log.d("WeatherApp", "fusedLocationProviderClient requests LocationUpdates")
            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest, locationCallback, Looper.getMainLooper()
            )
        } catch (unlikely: SecurityException) {
            Log.e("WeatherApp", "Lost location permissions. Couldn't remove updates. $unlikely")
        }
    }

    fun unsubscribeToLocationUpdates() {
        Log.d("WeatherApp", "unsubscribeToLocationUpdates()")
        try {
            val removeTask = fusedLocationProviderClient.removeLocationUpdates(locationCallback)
            removeTask.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("WeatherApp", "Location Callback removed.")
                } else {
                    Log.d("WeatherApp", "Failed to remove Location Callback.")
                }
            }
        } catch (unlikely: SecurityException) {
            Log.e("WeatherApp", "Lost location permissions. Couldn't remove updates. $unlikely")
        }
    }

    fun getForecastContainer() {
        _forecastContainerResultLiveData.value = ForecastContainerResult.IsLoading
        viewModelScope.launch {
            forecastContainerRepository.getForecastContainer()
        }
    }

    fun fetchForecastContainer() {
        _forecastContainerResultLiveData.value = ForecastContainerResult.IsLoading
        viewModelScope.launch {
            forecastContainerRepository.fetchForecastContainer()
        }
    }
}


class ForecastViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ForecastViewModel::class.java)) {
            val dao = ForecastDatabase.getDatabase(application).getForecastContainerDao()
            val forecastLocalDataService = ForecastLocalDataService(dao)
            val repository = ForecastContainerRepository(forecastLocalDataService)
            @Suppress("UNCHECKED_CAST")
            return ForecastViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}