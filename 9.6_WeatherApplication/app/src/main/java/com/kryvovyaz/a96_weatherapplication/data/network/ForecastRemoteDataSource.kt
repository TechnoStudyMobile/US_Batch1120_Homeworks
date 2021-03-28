package com.kryvovyaz.a96_weatherapplication.data.network

import com.kryvovyaz.a96_weatherapplication.data.repository.ForecastContainerResult
import com.kryvovyaz.a96_weatherapplication.util.METRIC
import com.kryvovyaz.a96_weatherapplication.util.RESPONSE_PARSING_ERROR_MESSAGE
import com.kryvovyaz.a96_weatherapplication.util.US
import com.kryvovyaz.a96_weatherapplication.util.WEATHER_API_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Error

object ForecastRemoteDataSource {
    suspend fun fetchForecastContainer(
        isCelsius: Boolean,
        days: Int,
        lat: String,
        long: String
    ): ForecastContainerResult =
        withContext(Dispatchers.IO) {
            val forecastService = RetrofitClient.retrofit?.create(ForecastService::class.java)
            val units = if (isCelsius) METRIC else US
            val forecastCall = lat.let {
                long.let { it1 ->
                    forecastService?.getForecast(
                        days, it,
                        it1, units, WEATHER_API_KEY
                    )
                }
            }
            try {
                val response = forecastCall?.execute()
                val forecastContainer = response?.body()
                forecastContainer?.let {
                    return@withContext ForecastContainerResult.Success(it)
                } ?: run {
                    return@withContext ForecastContainerResult.Failure(
                        Error(RESPONSE_PARSING_ERROR_MESSAGE))
                }
            } catch (ex: Exception) {

                return@withContext ForecastContainerResult
                    .Failure(Error(ex.message))
            }
        }
}