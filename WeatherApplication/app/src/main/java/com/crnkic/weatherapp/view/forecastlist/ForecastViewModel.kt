package com.crnkic.weatherapp.view.forecastlist


import android.app.Application
import androidx.lifecycle.*
import com.crnkic.weatherapp.data.database.ForecastLocalDataSource
import com.crnkic.weatherapp.data.database.WeatherDatabase
import com.crnkic.weatherapp.data.model.ForecastContainerResult
import com.crnkic.weatherapp.data.repository.ForecastContainerRepository
import kotlinx.coroutines.launch

class ForecastViewModel(private val forecastContainerRepository: ForecastContainerRepository) :
    ViewModel() {

    private val _forecastContainerResultLiveData = forecastContainerRepository.forecastContainerResultLiveData

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
            val localDataSource = ForecastLocalDataSource(dao)
            val repository = ForecastContainerRepository(localDataSource)
            @Suppress("UNCHECKED_CAST")
            return ForecastViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}