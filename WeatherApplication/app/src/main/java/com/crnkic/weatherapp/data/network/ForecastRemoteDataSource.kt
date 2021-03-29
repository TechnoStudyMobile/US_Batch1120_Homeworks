package com.crnkic.weatherapp.data.network

import android.util.Log
import com.LOG_TAG
import com.RESPONSE_PARSING_ERROR_MESSAGE
import com.WEATHER_API_KEY
import com.crnkic.weatherapp.data.model.ForecastContainer
import com.crnkic.weatherapp.data.model.ForecastContainerResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import java.lang.Error
import java.lang.Exception

object ForecastRemoteDataSource {

    suspend fun fetchForecastContainer(isCelsius: Boolean, days: Int): ForecastContainerResult =
        withContext(Dispatchers.IO) {
            val units: String = if (isCelsius) "M" else "I"
            val getDataService = RetrofitClient.retrofit?.create(GetDataService::class.java)
            val forecastCall: Call<ForecastContainer>? =
                getDataService?.getWeatherData(days.toString(), "17055", "US", units, WEATHER_API_KEY)
            try {
                val response = forecastCall?.execute()
                val forecastContainer = response?.body()
                Log.d(LOG_TAG, "Forecast response from backend" + forecastContainer.toString())

                forecastContainer?.let {
                    return@withContext ForecastContainerResult.Success(it)
                } ?: run {
                    return@withContext ForecastContainerResult.Failure(
                        Error(
                            RESPONSE_PARSING_ERROR_MESSAGE
                        )
                    )
                }
                //TODO: Handle error cases when forecastContainer is null
            } catch (ex: Exception) {
                Log.d(LOG_TAG, ex.toString())
                //Failure case
                return@withContext ForecastContainerResult.Failure(Error(ex.message))
            }
        }
}