package com.kryvovyaz.a96_weatherapplication.screen.forecastlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kryvovyaz.a96_weatherapplication.model.Forecast
import com.kryvovyaz.a96_weatherapplication.network.ForecastService
import com.kryvovyaz.a96_weatherapplication.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForecastViewModel : ViewModel() {
    private val _listOfItemsLiveData = MutableLiveData<Forecast>()
    val listOfItemsLiveData: LiveData<Forecast>
        get() = _listOfItemsLiveData

    fun getData() {
        val getForecastService = RetrofitClient.instance?.create(ForecastService::class.java)
        val callResponse: Call<Forecast>? = getForecastService?.getForecast()
        callResponse?.enqueue(object : Callback<Forecast> {
            override fun onResponse(
                call: Call<Forecast>,
                response: Response<Forecast>
            ) {
                response.body()?.let { callForecast ->
                    _listOfItemsLiveData.value = callForecast
                }
            }
            override fun onFailure(call: Call<Forecast>, t: Throwable) {
            }
        })
    }
}