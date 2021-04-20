package ustun.muharrem.weatherforecast.utilities

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import java.util.concurrent.TimeUnit
import kotlin.math.abs

object LocationUtil {

    @SuppressLint("StaticFieldLeak")
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private var currentLocation: Location? = null

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
                     * Each time the fragment is (re)created, it subscribes for the location updates.
                     * Even with small distances (for even a few meters) SharedPref updates.
                     * Each time SharedPref changes, it fetches info from API, which increases the
                     * number of calls from back-end for nothing. In order to prevent this,
                     * SharedPrefs is not updated up to a certain distance change.
                     * (0.01 degree change is around 1.11 km or 0.7 miles)
                     */
                    if (abs(it.longitude - SharedPrefs.lon) > 0.01) SharedPrefs.lon = it.longitude
                    if (abs(it.latitude - SharedPrefs.lat) > 0.01) SharedPrefs.lat = it.latitude
                } ?: Log.d(MY_LOG, "current location is null")
                // TODO: Handle if current location is null !!!
            }
        }
    }

    fun subscribeToLocationUpdates() {
        try {
            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest, locationCallback, Looper.getMainLooper()
            )
        } catch (unlikely: SecurityException) {
            Log.e(MY_LOG, "Lost location permissions. Couldn't remove updates. $unlikely")
        }
    }

    fun unsubscribeToLocationUpdates() {
        try {
            val removeTask = fusedLocationProviderClient.removeLocationUpdates(locationCallback)
            removeTask.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(MY_LOG, "Location Callback removed.")
                } else {
                    Log.d(MY_LOG, "Failed to remove Location Callback.")
                }
            }
        } catch (unlikely: SecurityException) {
            Log.e(MY_LOG, "Lost location permissions. Couldn't remove updates. $unlikely")
        }
    }

    fun isPermissionGranted(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }
}