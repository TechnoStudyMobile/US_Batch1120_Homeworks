package ustun.muharrem.weatherforecast.screens

import android.app.Application
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

    fun getForecastContainer() {
        _forecastContainerResultLiveData.value = ForecastContainerResult.IsLoading
        viewModelScope.launch {
                forecastContainerRepository.getForecastContainer()
        }
    }

    fun fetchForecastContainer(){
        _forecastContainerResultLiveData.value = ForecastContainerResult.IsLoading
        viewModelScope.launch {
            forecastContainerRepository.fetchForecastContainer()
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