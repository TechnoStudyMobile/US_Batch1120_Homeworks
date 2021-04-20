package ustun.muharrem.weatherforecast.screens

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.*
import kotlinx.coroutines.launch
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