package ustun.muharrem.weatherforecast.screens

import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ustun.muharrem.weatherforecast.R
import ustun.muharrem.weatherforecast.data.ForecastContainerResult
import ustun.muharrem.weatherforecast.database.ForecastDatabase
import ustun.muharrem.weatherforecast.database.ForecastLocalDataService
import ustun.muharrem.weatherforecast.repository.ForecastContainerRepository
import ustun.muharrem.weatherforecast.utilities.*
import java.util.*

class ForecastViewModel(private val forecastContainerRepository: ForecastContainerRepository) :
    ViewModel() {

    private val _forecastContainerResultLiveData =
        forecastContainerRepository.forecastContainerResultLiveData
    val forecastContainerResultLiveData: LiveData<ForecastContainerResult>
        get() = _forecastContainerResultLiveData

    val sharedPrefListener =
        SharedPreferences.OnSharedPreferenceChangeListener { sharedPref, key ->
            when (key) {
                LONGITUDE_KEY,
                LATITUDE_KEY,
                IS_CELSIUS_SETTING_KEY -> viewModelScope.launch {
                    if (LocationUtil.isPermissionGranted(SharedPrefs.application.applicationContext)) {
                        forecastContainerRepository.fetchForecastContainer()
                    }
                }
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

    fun getForecastContainer() {
        _forecastContainerResultLiveData.value = ForecastContainerResult.IsLoading
        viewModelScope.launch {
            if (System.currentTimeMillis() - SharedPrefs.lastEpochTime > THREE_HOUR_EPOCH_TIME) {
                forecastContainerRepository.fetchForecastContainer()
            } else {
                forecastContainerRepository.getSavedForecastContainer()
            }
        }
    }

    fun locationAlertDialog(context: Context): AlertDialog {
        // TODO: create a custom alert dialog layout (optional)
        val builder = AlertDialog.Builder(context)
        builder.setTitle(context.getString(R.string.location_alert_dialog_title))
        // TODO: Write a better message text
        builder.setMessage(context.getString(R.string.location_alert_dialog_message))
        // TODO: change the alert dialog box icon
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        // TODO: extract button names as string
        builder.setNeutralButton("OK") { _, _ -> }
        val alertDialog = builder.create()
        alertDialog.setCancelable(false)
        Log.d(MY_LOG, "on location Alert Dialog creation method")
        return alertDialog
    }

    fun forecastContainerFailureAlertDialog(context: Context) : AlertDialog {
        // TODO: create a custom alert dialog layout (optional)
        val builder = AlertDialog.Builder(context)
        builder.setTitle(context.getString(R.string.forecast_container_failure_alert_dialog_title))
        // TODO: Write a better message text
        builder.setMessage(context.getString(R.string.forecast_container_failure_alert_dialog_message))
        // TODO: change the alert dialog box icon
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        // TODO: extract button names as string
        builder.setNeutralButton("Cancel") { _, _ -> }
        builder.setPositiveButton("Retry") { _, _ ->
            viewModelScope.launch {
                forecastContainerRepository.fetchForecastContainer()
            }
        }
        val alertDialog = builder.create()
        alertDialog.setCancelable(false)
        return alertDialog
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