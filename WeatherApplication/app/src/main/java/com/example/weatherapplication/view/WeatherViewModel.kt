package com.example.weatherapplication.view

import androidx.lifecycle.LiveData
import com.example.weatherapplication.weatherData.Json4Kotlin_Base
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapplication.network.GetDataService
import com.example.weatherapplication.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherViewModel() : ViewModel() {

    private val _listOfItemsLiveData = MutableLiveData<Json4Kotlin_Base>()

    lateinit var listOfItems: Json4Kotlin_Base

    val listOfItemsLiveData: LiveData<Json4Kotlin_Base>
        get() = _listOfItemsLiveData

    fun getData() {
        val getDataService = RetrofitClient.retrofit?.create(GetDataService::class.java)
        val callResponse: Call<Json4Kotlin_Base>? = getDataService?.getWeatherData()

        callResponse?.enqueue(object : Callback<Json4Kotlin_Base> {
            override fun onResponse(call: Call<Json4Kotlin_Base>, response: Response<Json4Kotlin_Base>) {
                response.body()?.let {
                    listOfItems = it
                }

                _listOfItemsLiveData.value = listOfItems
            }

            override fun onFailure(call: Call<Json4Kotlin_Base>, t: Throwable) {

            }
        })
    }

}
