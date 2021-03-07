package com.crnkic.weatherapp.repository

import androidx.lifecycle.LiveData
import com.WEATHER_API_KEY
import com.crnkic.weatherapp.database.ForecastContainerDao
import com.crnkic.weatherapp.model.ForecastContainer
import com.crnkic.weatherapp.network.GetDataService
import com.crnkic.weatherapp.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForecastContainerRepository(var dao: ForecastContainerDao) {

    lateinit var forecastContainer: ForecastContainer

    val forecastListLiveData : LiveData<ForecastContainer> = dao.getForecastContainer()

    fun getForecastContainer(isCelsius: Boolean, days : Int) {
        fetchForecastContainer(isCelsius, days)
    }



    fun insertToDatabase(forecastContainer: ForecastContainer) {
        //delete everything
        //save to db
        Thread {
            dao.deleteAll()
            dao.insert(forecastContainer)
        }.start()
    }

    //Backend
    private fun fetchForecastContainer(isCelsius: Boolean, days: Int) {
        val units: String = if (isCelsius) "M" else "I"
        val getDataService = RetrofitClient.retrofit?.create(GetDataService::class.java)
        val forecastCall: Call<ForecastContainer>? =
            getDataService?.getWeatherData(days.toString(), "17055", "US", units, WEATHER_API_KEY)

        forecastCall?.enqueue(object : Callback<ForecastContainer> {
            override fun onResponse(call: Call<ForecastContainer>, container: Response<ForecastContainer>) {
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