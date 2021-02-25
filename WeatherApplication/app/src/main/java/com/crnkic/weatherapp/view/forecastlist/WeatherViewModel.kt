package com.crnkic.weatherapp.view.forecastlist


import androidx.lifecycle.LiveData
import com.crnkic.weatherapp.forecastResponse.ForcastResponse
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.WEATHER_API_KEY
import com.crnkic.weatherapp.network.GetDataService
import com.crnkic.weatherapp.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherViewModel() : ViewModel() {

    private val _listOfItemsLiveData = MutableLiveData<ForcastResponse>()
    lateinit var listOfItems: ForcastResponse

    val listOfItemsLiveData: LiveData<ForcastResponse?>
        get() = _listOfItemsLiveData

    fun fetchData() {
        val getDataService = RetrofitClient.retrofit?.create(GetDataService::class.java)
        val forecastCall: Call<ForcastResponse>? = getDataService?.getWeatherData("14", "17055", "US", WEATHER_API_KEY)
//        val forecastCall: Call<ForcastResponse>? = getDataService?.getWeatherData()

        forecastCall?.enqueue(object : Callback<ForcastResponse> {
            override fun onResponse(call: Call<ForcastResponse>, response: Response<ForcastResponse>) {
                response.body()?.let {
                    listOfItems = it
                }
                _listOfItemsLiveData.value = listOfItems
            }
            override fun onFailure(call: Call<ForcastResponse>, t: Throwable) {}
        })
    }
}