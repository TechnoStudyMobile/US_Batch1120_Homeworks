package com.crnkic.weatherapp.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.WEATHER_API_KEY
import com.crnkic.weatherapp.database.ForecastContainerDao
import com.crnkic.weatherapp.model.ForecastContainer
import com.crnkic.weatherapp.model.ForecastContainerResult
import com.crnkic.weatherapp.network.GetDataService
import com.crnkic.weatherapp.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Error
import java.lang.Exception

class ForecastContainerRepository(var dao: ForecastContainerDao) {

    private lateinit var forecastContainer: ForecastContainer

    val forecastContainerResultLiveData = MutableLiveData<ForecastContainerResult>()

//    val forecastListLiveData: LiveData<ForecastContainer> = dao.getForecastContainer()

    suspend fun fetchForecastContainer(isCelsius: Boolean, days: Int) {
//        forecastListLiveData = ForecastContainerResult.Loading
        withContext(Dispatchers.IO) {
            val units: String = if (isCelsius) "M" else "I"
            val getDataService = RetrofitClient.retrofit?.create(GetDataService::class.java)
            val forecastCall: Call<ForecastContainer>? =
                getDataService?.getWeatherData(days.toString(), "17055", "US", units, WEATHER_API_KEY)


            try {
                val response = forecastCall?.execute()
                val forecastContainer = response?.body()
                forecastContainer?.let {
                    Log.d("WeatherApp", response.message() + forecastContainer.toString())
                    forecastContainerResultLiveData.postValue(ForecastContainerResult.Success(it))
                    insertToDatabase(it)
                }
                //TODO: Handle error cases when forecastContainer is null
            } catch (ex: Exception) {
                Log.d("WeatherApp", ex.toString())
                //Failure case
                forecastContainerResultLiveData.postValue(
                    ForecastContainerResult.Failure(Error(ex.message))
                )
            }
        }
    }

    suspend fun getSavedForecastContainer() {
        withContext(Dispatchers.IO) {
            var forecastContainer = dao.getForecastContainer()
            forecastContainerResultLiveData.postValue(
                ForecastContainerResult.Success(forecastContainer)
            )
        }
    }

    private fun insertToDatabase(forecastContainer: ForecastContainer) {
            dao.deleteAll()
            dao.insert(forecastContainer)



    }
}