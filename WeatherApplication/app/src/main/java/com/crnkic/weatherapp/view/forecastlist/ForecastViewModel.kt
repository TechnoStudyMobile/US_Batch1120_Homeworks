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

    //TODO: Maybe have a separate liveData for DB forecastContainer only
    private val _forecastContainerResultLiveData = MutableLiveData<ForecastContainerResult>()
    val forecastContainerResultLiveData: LiveData<ForecastContainerResult>
        get() = _forecastContainerResultLiveData

    init {
        getSavedForecastContainer()
    }

    fun getForecastContainer(shouldFetch : Boolean) {
        // will get from db first and post to liveData

        //it will try fetch
    }

    fun getSavedForecastContainer() {
        viewModelScope.launch {
            val forecastContainerResult = forecastContainerRepository.getSavedForecastContainer()
            _forecastContainerResultLiveData.value = forecastContainerResult
        }

    }

    fun fetchForecastContainer(isCelsius: Boolean, days: Int) {
        _forecastContainerResultLiveData.value = ForecastContainerResult.IsLoading
        viewModelScope.launch {
            _forecastContainerResultLiveData.value = forecastContainerRepository.fetchForecastContainer(isCelsius, days)
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