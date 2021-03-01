package com.crnkic.weatherapp.view.forecastlist


import androidx.lifecycle.LiveData
import com.crnkic.weatherapp.forecastResponse.ForecastResponse
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.WEATHER_API_KEY
import com.crnkic.weatherapp.network.GetDataService
import com.crnkic.weatherapp.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherViewModel() : ViewModel() {

    private val _listOfItemsLiveData = MutableLiveData<ForecastResponse>()
    lateinit var listOfItems: ForecastResponse

    val listOfItemsLiveData: LiveData<ForecastResponse?>
        get() = _listOfItemsLiveData

    fun fetchData(isCelsius: Boolean) {
        val units: String = if(isCelsius) "M" else "I"
        val getDataService = RetrofitClient.retrofit?.create(GetDataService::class.java)
        val forecastCall: Call<ForecastResponse>? = getDataService?.getWeatherData("14", "17055", "US", units, WEATHER_API_KEY)
//        val forecastCall: Call<ForecastResponse>? = getDataService?.getWeatherData()

        forecastCall?.enqueue(object : Callback<ForecastResponse> {
            override fun onResponse(call: Call<ForecastResponse>, response: Response<ForecastResponse>) {
                response.body()?.let {
                    listOfItems = it
                }
                _listOfItemsLiveData.value = listOfItems
            }
            override fun onFailure(call: Call<ForecastResponse>, t: Throwable) {}
        })
    }
}