package com.kryvovyaz.a96_weatherapplication.repository

import androidx.lifecycle.MutableLiveData
import com.kryvovyaz.a96_weatherapplication.database.ForecastContainerDao
import com.kryvovyaz.a96_weatherapplication.model.ForecastContainer
import com.kryvovyaz.a96_weatherapplication.network.ForecastService
import com.kryvovyaz.a96_weatherapplication.network.RetrofitClient
import com.kryvovyaz.a96_weatherapplication.util.METRIC
import com.kryvovyaz.a96_weatherapplication.util.US
import com.kryvovyaz.a96_weatherapplication.util.WEATHER_API_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Error

class ForecastContainerRepository(private val dao: ForecastContainerDao) {

    val forecastContainerResultLiveData = MutableLiveData<ForecastContainerResult>()

   suspend fun fetchForecastContainer(isCelsius: Boolean, days: Int) {
       withContext(Dispatchers.IO) {
           val forecastService = RetrofitClient.retrofit?.create(ForecastService::class.java)
           val units = if (isCelsius) METRIC else US
           val forecastCall = forecastService?.getForecast(
               days, "38.123",
               "-78.543", units, WEATHER_API_KEY
           )
           try {
               val response = forecastCall?.execute()
               val forecastContainer = response?.body()
               forecastContainer?.let {
                   forecastContainerResultLiveData
                       .postValue(ForecastContainerResult.Success(it))
                   insertToDatabase(it)
               }
               //TODO:handle error cases when  forecastContainer is null
           } catch (ex: Exception) {

               forecastContainerResultLiveData.postValue(
                   ForecastContainerResult
                       .Failure(Error(ex.message))
               )
           }
       }
   }
    suspend fun getSavedForecastContainer() {
        withContext(Dispatchers.IO) {
            val forecastContainer = dao.getForecastContainer()
            forecastContainer?.let{
                forecastContainerResultLiveData.postValue(ForecastContainerResult.Success(forecastContainer))
        }

        }
    }

    private fun insertToDatabase(forecastContainer: ForecastContainer) {
        dao.deleteAll()
        dao.insert(forecastContainer)
    }
}