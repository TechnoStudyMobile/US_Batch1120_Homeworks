package com.kryvovyaz.a96_weatherapplication

import android.app.Application
import androidx.lifecycle.*
import com.kryvovyaz.a96_weatherapplication.database.WeatherDatabase
import com.kryvovyaz.a96_weatherapplication.repository.ForecastContainerRepository
import com.kryvovyaz.a96_weatherapplication.repository.ForecastContainerResult
import kotlinx.coroutines.launch

class ForecastViewModel(private val forecastContainerRepository: ForecastContainerRepository) :
    ViewModel() {

    private val _forecastContainerResultLiveData = forecastContainerRepository.
    forecastContainerResultLiveData
    val forecastContainerResultLiveData: LiveData<ForecastContainerResult>
        get() = _forecastContainerResultLiveData

    fun getSavedForecastContainer() {
        viewModelScope.launch {
            forecastContainerRepository.getSavedForecastContainer()
        }
    }

    fun fetchForecastContainer(isCelsius: Boolean, days: Int) {
        _forecastContainerResultLiveData.value = ForecastContainerResult.IsLoading
        viewModelScope.launch {
            forecastContainerRepository.fetchForecastContainer(isCelsius, days)
        }
    }
}

class ForecastViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ForecastViewModel::class.java)) {
            val dao = WeatherDatabase.getDatabase(application).getForecastContainerDao()
            val repository = ForecastContainerRepository(dao)
            @Suppress("UNCHECKED_CAST")
            return ForecastViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}