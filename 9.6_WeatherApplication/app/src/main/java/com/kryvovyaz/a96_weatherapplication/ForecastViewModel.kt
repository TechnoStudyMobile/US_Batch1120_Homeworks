package com.kryvovyaz.a96_weatherapplication

import android.app.Application
import androidx.lifecycle.*
import com.kryvovyaz.a96_weatherapplication.data.database.ForecastLocalDataSource
import com.kryvovyaz.a96_weatherapplication.data.database.WeatherDatabase
import com.kryvovyaz.a96_weatherapplication.data.repository.ForecastContainerRepository
import com.kryvovyaz.a96_weatherapplication.data.repository.ForecastContainerResult
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
//TODO:remove parameters from fun
    fun fetchForecastContainer(isCelsius: Boolean, days: Int,lat:String,long:String) {
        _forecastContainerResultLiveData.value = ForecastContainerResult.IsLoading
        viewModelScope.launch {
            forecastContainerRepository.fetchForecastContainer(isCelsius, days,lat,long)
           // forecastContainerRepository.localDataSource
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