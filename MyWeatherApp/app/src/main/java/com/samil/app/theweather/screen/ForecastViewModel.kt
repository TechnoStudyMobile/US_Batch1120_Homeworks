package com.samil.app.theweather.screen

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.samil.app.theweather.WEATHER_API_KEY
import com.samil.app.theweather.model.ForecastResponse
import com.samil.app.theweather.network.ForecastService
import com.samil.app.theweather.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ForecastViewModel : ViewModel() {
    private val forecastList = mutableListOf<ForecastResponse>()

    private val _forecastListLiveData = MutableLiveData<ForecastResponse>()
    val forecastDummyListLiveData: LiveData<ForecastResponse>
        get() = _forecastListLiveData

    fun fetchForecastInfo() {
        val forcastService = RetrofitClient.retrofit?.create(ForecastService::class.java)
        val forecastCall = forcastService?.getForecast("14", "38.123", "-78.543", WEATHER_API_KEY)
        forecastCall?.enqueue(object : Callback<ForecastResponse> {
            override fun onResponse(
                call: Call<ForecastResponse>,
                response: Response<ForecastResponse>
            ) {
                Log.d("WeatherApp", response.body().toString())
                val forecastResponse: ForecastResponse? = response.body()
                _forecastListLiveData.value = forecastResponse
            }

            override fun onFailure(call: Call<ForecastResponse>, t: Throwable) {
                Log.d("WeatherApp", t.localizedMessage)
            }
        })
    }

    //TODO Change these methods
    @RequiresApi(Build.VERSION_CODES.O)
    fun getDays(days: Int): String {
        val formatter = DateTimeFormatter.ofPattern("EE, MMM yy")
        return LocalDateTime.now().plusDays(days.toLong()).format(formatter).toString()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDay(days: Int): String {
        val formatter = DateTimeFormatter.ofPattern("EEEE")
        return LocalDateTime.now().plusDays(days.toLong()).format(formatter).toString()
    }
}