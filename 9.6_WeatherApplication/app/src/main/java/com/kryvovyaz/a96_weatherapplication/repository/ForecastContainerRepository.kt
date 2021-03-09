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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForecastContainerRepository(val dao: ForecastContainerDao) {

    val forecastListLiveData: LiveData<ForecastContainer> = dao.getForecastContainer()
    lateinit var forecastContainer: ForecastContainer
    var latestRequestTime: Long = -1//implementing updater
  /*************   implemanting location listener  *********************************/
    private var listener: ((Location) -> Unit)? = null

    fun setOnLocationChangedListener(listener: (Location) -> Unit) {
        this.listener = listener
    }

    private fun onLocationUpdated(location: Location) {
        listener?.invoke(location)
    }
    /***********************************************************************/

    fun getForecastContainer(isCelsius: Boolean, days: Int) {
        fetchForecastContainer(isCelsius, days)
    }

    //Repository
    fun insertToDatabase(forecastContainer: ForecastContainer) {
        Thread {
            dao.deleteAll()
            dao.insert(forecastContainer)

        }.start()
    }

    fun fetchForecastContainer(isCelsius: Boolean, days: Int) {

//        if ((System.currentTimeMillis() - latestRequestTime) < 1800000) {//updater
//            return
//        }
        val forecastService = RetrofitClient.retrofit?.create(ForecastService::class.java)
        val units = if (isCelsius) METRIC else US
        val forecastCall = forecastService?.getForecast(
            days, "38.123",
            "-78.543", units, WEATHER_API_KEY
        )
        forecastCall?.enqueue(object : Callback<ForecastContainer> {
            override fun onResponse(
                call: Call<ForecastContainer>,
                container: Response<ForecastContainer>
            ) {
                latestRequestTime = System.currentTimeMillis()//updater
                container.body()?.let {
                    forecastContainer = it
                }

                //Save to DB
                insertToDatabase(forecastContainer)
            }


            override fun onFailure(call: Call<ForecastContainer>, t: Throwable) {}
        })
    }
}