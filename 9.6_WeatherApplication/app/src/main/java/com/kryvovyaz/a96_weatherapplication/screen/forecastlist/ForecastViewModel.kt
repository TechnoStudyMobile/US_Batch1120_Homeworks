package com.kryvovyaz.a96_weatherapplication.screen.forecastlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kryvovyaz.a96_weatherapplication.model.Forecast
import com.kryvovyaz.a96_weatherapplication.network.ForecastService
import com.kryvovyaz.a96_weatherapplication.network.RetrofitClient
import com.kryvovyaz.a96_weatherapplication.util.WEATHER_API_KEY
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForecastViewModel : ViewModel() {
    private val _forecastLiveData = MutableLiveData<Forecast>()
    val forecastLiveData: LiveData<Forecast>
        get() = _forecastLiveData

    fun fetchForecastInfo() {
        val forecastService = RetrofitClient.retrofit?.create(ForecastService::class.java)
        val forecastCall: Call<Forecast>? = forecastService?.getForecast("14","38.123","-78.543",WEATHER_API_KEY)
        forecastCall?.enqueue(object : Callback<Forecast> {
            override fun onResponse(
                call: Call<Forecast>,
                response: Response<Forecast>
            ) {
                response.body()?.let { callForecast ->
                    _forecastLiveData.value = callForecast
                }
            }
            override fun onFailure(call: Call<Forecast>, t: Throwable) {
            }
        })
    }
}