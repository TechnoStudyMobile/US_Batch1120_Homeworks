package com.kryvovyaz.a96_weatherapplication.repository

import android.location.Location
import androidx.lifecycle.LiveData
import com.kryvovyaz.a96_weatherapplication.database.ForecastContainerDao
import com.kryvovyaz.a96_weatherapplication.model.ForecastContainer
import com.kryvovyaz.a96_weatherapplication.network.ForecastService
import com.kryvovyaz.a96_weatherapplication.network.RetrofitClient
import com.kryvovyaz.a96_weatherapplication.util.METRIC
import com.kryvovyaz.a96_weatherapplication.util.US
import com.kryvovyaz.a96_weatherapplication.util.WEATHER_API_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForecastContainerRepository(val dao: ForecastContainerDao) {

    val forecastListLiveData: LiveData<ForecastContainer> = dao.getForecastContainer()

    /*************   implemanting location listener  *********************************/
    private var listener: ((Location) -> Unit)? = null

    fun setOnLocationChangedListener(listener: (Location) -> Unit) {
        this.listener = listener
    }

    private fun onLocationUpdated(location: Location) {
        listener?.invoke(location)
    }

    /***********************************************************************/

  suspend fun getForecastContainer(isCelsius: Boolean, days: Int) {
        withContext(Dispatchers.IO) {
            fetchForecastContainer(isCelsius, days)
        }
    }
    private fun fetchForecastContainer(isCelsius: Boolean, days: Int) {
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
                insertToDatabase(it)
            }
            //TODO:fix if response is null
        } catch (ex: Exception) {
        }
    }

    private fun insertToDatabase(forecastContainer: ForecastContainer) {
        dao.deleteAll()
        dao.insert(forecastContainer)
    }
}