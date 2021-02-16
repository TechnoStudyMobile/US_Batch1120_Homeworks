package com.example.weatherapplication.view

import com.example.weatherapplication.weatherData.Json4Kotlin_Base
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapplication.network.GetDataService
import com.example.weatherapplication.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherViewModel() : ViewModel() {

    var listOfItemsLiveData = MutableLiveData<Json4Kotlin_Base>()

    val daysLiveData = MutableLiveData<String>()
    val forecastLiveData = MutableLiveData<String>()
    val imageLiveData = MutableLiveData<String>()
    val temperatureLiveData = MutableLiveData<String>()


//    var listOfItems = listOf<com.example.weatherapplication.data.WeatherData.Weather>()


    fun getData() {
        var getDataService = RetrofitClient.retrofit?.create(GetDataService::class.java)
        var callResponse: Call<Json4Kotlin_Base>? = getDataService?.getWeatherData()

        callResponse?.enqueue(object : Callback<Json4Kotlin_Base> {
            override fun onResponse(call: Call<Json4Kotlin_Base>, response: Response<Json4Kotlin_Base>) {
                listOfItemsLiveData.value = response.body()

//                val weatherFragment = WeatherFragment()
//
//                val adapter = FragmentAdapter(listOfItems!!) {
//
//                }
//                weatherFragment.recyclerView_fragment.layoutManager = LinearLayoutManager(weatherFragment.context)
//                weatherFragment.recyclerView_fragment.adapter = adapter
//            }
            }

            override fun onFailure(call: Call<Json4Kotlin_Base>, t: Throwable) {

            }

        })


    }

//    fun getRecycler() {
//        val adapter = FragmentAdapter(listOfItemsLiveData) {
//
//        }
//        recyclerView_fragment.layoutManager = LinearLayoutManager(context)
//        recyclerView_fragment.adapter = adapter
//    }


}
