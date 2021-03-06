package com.samil.app.theweather.repository

import android.util.Log
import com.samil.app.theweather.WEATHER_API_KEY
import com.samil.app.theweather.database.ForecastContainerDao
import com.samil.app.theweather.database.WeatherDatabase
import com.samil.app.theweather.model.ForecastContainer
import com.samil.app.theweather.network.ForecastService
import com.samil.app.theweather.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForecastContainerRepository(private var dao: ForecastContainerDao) {

    fun getForecastContainer(isCelsius: Boolean){
        fetchForecastContainer(isCelsius)
    }

    private fun insertToDatabase(forecastContainer: ForecastContainer){
        //delete everthing
        //Save to db
        Thread {
            dao.deleteAll()
            dao.insert(forecastContainer)
        }.start()
    }

    fun fetchForecastContainer(isCelsius: Boolean) {
        val forecastService = RetrofitClient.retrofit?.create(ForecastService::class.java)
        val units = if (isCelsius) "M" else "I"
        val forecastCall =
            forecastService?.getForecast("16", "38.123", "-78.543", units, WEATHER_API_KEY)

        forecastCall?.enqueue(object : Callback<ForecastContainer> {
            override fun onResponse(
                call: Call<ForecastContainer>,
                response: Response<ForecastContainer>
            ) {
                Log.d("WeatherApp", response.body().toString())
                val forecastContainer: ForecastContainer? = response.body()
                //_forecastListLiveData.value = forecastContainer

                //Save to db
                forecastContainer?.let {
                    insertToDatabase(it)
                }
            }

            override fun onFailure(call: Call<ForecastContainer>, t: Throwable) {
                Log.d("WeatherApp", t.localizedMessage)
            }
        })
    }
}