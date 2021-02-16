package com.example.weatherapplication.view

import androidx.lifecycle.LiveData
import com.example.weatherapplication.weatherData.Json4Kotlin_Base
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapplication.network.GetDataService
import com.example.weatherapplication.network.RetrofitClient
import kotlinx.android.synthetic.main.weather_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherViewModel() : ViewModel() {

    lateinit var listOfItems : Json4Kotlin_Base
    private val _listOfItemsLiveData = MutableLiveData<Json4Kotlin_Base>()

    val listOfItemsLiveData : LiveData<Json4Kotlin_Base>
        get() = _listOfItemsLiveData

    fun getData() {
        val getDataService = RetrofitClient.retrofit?.create(GetDataService::class.java)
        val callResponse: Call<Json4Kotlin_Base>? = getDataService?.getWeatherData()

        callResponse?.enqueue(object : Callback<Json4Kotlin_Base> {
            override fun onResponse(call: Call<Json4Kotlin_Base>, response: Response<Json4Kotlin_Base>) {
                listOfItems.let {
                    response.body()
                }

                _listOfItemsLiveData.value = listOfItems
            }

            override fun onFailure(call: Call<Json4Kotlin_Base>, t: Throwable) {

            }
        })
    }

    fun getRecyclerList() {
        val adapter = FragmentAdapter(listOfItems) {

        }
        val weatherFragment = WeatherFragment()
        weatherFragment.recyclerView_fragment.layoutManager = LinearLayoutManager(weatherFragment.context)
        weatherFragment.recyclerView_fragment.adapter = adapter
    }


}
