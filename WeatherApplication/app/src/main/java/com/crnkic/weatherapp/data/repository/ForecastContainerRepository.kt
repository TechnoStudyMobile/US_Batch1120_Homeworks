package com.crnkic.weatherapp.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.WEATHER_API_KEY
import com.crnkic.weatherapp.data.database.ForecastContainerDao
import com.crnkic.weatherapp.data.database.ForecastLocalDataSource
import com.crnkic.weatherapp.data.database.ForecastLocalDataSourceImp
import com.crnkic.weatherapp.data.model.ForecastContainer
import com.crnkic.weatherapp.data.model.ForecastContainerResult
import com.crnkic.weatherapp.data.network.ForecastRemoteDataSource
import com.crnkic.weatherapp.data.network.GetDataService
import com.crnkic.weatherapp.data.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import java.lang.Error
import java.lang.Exception

class ForecastContainerRepository(private val localDataSource: ForecastLocalDataSourceImp) {

    val forecastContainerResultLiveData = MutableLiveData<ForecastContainerResult>()
//    private val localDataSource = ForecastLocalDataSource(dao)

//    val forecastListLiveData: LiveData<ForecastContainer> = dao.getForecastContainer()

    suspend fun fetchForecastContainer(isCelsius: Boolean, days: Int) {
        val forecastContainerResult =
            ForecastRemoteDataSource.fetchForecastContainer(isCelsius, days)
        forecastContainerResultLiveData.postValue(forecastContainerResult)
        if (forecastContainerResult is ForecastContainerResult.Success) {
            insertToDatabase(forecastContainerResult.forecastContainer)
        }
    }

    suspend fun getSavedForecastContainer() {
        val forecastContainerResult = localDataSource.getSavedForecastContainer()
        forecastContainerResultLiveData.postValue(forecastContainerResult)
    }

    private suspend fun insertToDatabase(forecastContainer: ForecastContainer) {
        localDataSource.deleteAll()
        localDataSource.insert(forecastContainer)
    }
}