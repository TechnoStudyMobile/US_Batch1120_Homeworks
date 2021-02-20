package com.crnkic.weatherapplication.view


import androidx.lifecycle.LiveData
import com.crnkic.weatherapplication.weatherData.Json4Kotlin_Base
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.crnkic.weatherapplication.network.GetDataService
import com.crnkic.weatherapplication.network.RetrofitClient
import kotlinx.android.synthetic.main.weather_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherViewModel() : ViewModel() {

    private val _listOfItemsLiveData = MutableLiveData<Json4Kotlin_Base>()

    lateinit var listOfItems: Json4Kotlin_Base

    val listOfItemsLiveData: LiveData<Json4Kotlin_Base?>
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

    fun getRecyclerList(context: WeatherFragment) {
        val adapter = FragmentAdapter(listOfItems) {

        }
        context.recyclerView_fragment.layoutManager = LinearLayoutManager(context.context)
        context.recyclerView_fragment.adapter = adapter
    }

}
