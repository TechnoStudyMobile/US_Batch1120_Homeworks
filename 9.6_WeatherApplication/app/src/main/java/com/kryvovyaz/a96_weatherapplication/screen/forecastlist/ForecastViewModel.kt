package com.kryvovyaz.a96_weatherapplication.screen.forecastlist

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kryvovyaz.a96_weatherapplication.model.Json4Kotlin_Base
import com.kryvovyaz.a96_weatherapplication.network.ForecastService
import com.kryvovyaz.a96_weatherapplication.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForecastViewModel : ViewModel() {
    private val _listOfItemsLiveData = MutableLiveData<Json4Kotlin_Base>()
    val listOfItemsLiveData: LiveData<Json4Kotlin_Base>
        get() = _listOfItemsLiveData

    fun getData() {
        val getForecastService = RetrofitClient.instance?.create(ForecastService::class.java)
        val callResponse: Call<Json4Kotlin_Base>? = getForecastService?.getForecast()
        callResponse?.enqueue(object : Callback<Json4Kotlin_Base> {
            override fun onResponse(
                call: Call<Json4Kotlin_Base>,
                response: Response<Json4Kotlin_Base>
            ) {
                response.body()?.let { callForecast ->
                    _listOfItemsLiveData.value = callForecast
                }
            }
            override fun onFailure(call: Call<Json4Kotlin_Base>, t: Throwable) {
            }
        })
    }
}