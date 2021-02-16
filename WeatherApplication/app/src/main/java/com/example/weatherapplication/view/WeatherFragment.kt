package com.example.weatherapplication.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapplication.R
import com.example.weatherapplication.weatherData.Json4Kotlin_Base
import com.example.weatherapplication.network.GetDataService
import com.example.weatherapplication.network.RetrofitClient
import kotlinx.android.synthetic.main.weather_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherFragment : Fragment() {

    private lateinit var viewModel: WeatherViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)

//        viewModel.getData()
//        viewModel.listOfItemsLiveData.observe(this.viewLifecycleOwner, Observer<List<com.example.weatherapplication.data.WeatherData.Weather>>() {
//            val adapter = FragmentAdapter(listOfItems) {
//
//                }
//                recyclerView_fragment.layoutManager = LinearLayoutManager(context)
//                recyclerView_fragment.adapter = adapter
//        })


        val getDataService = RetrofitClient.retrofit?.create(GetDataService::class.java)
        val callResponse: Call<Json4Kotlin_Base>? = getDataService?.getWeatherData()

        callResponse?.enqueue(object : Callback<Json4Kotlin_Base> {
            override fun onResponse(call: Call<Json4Kotlin_Base>, response: Response<Json4Kotlin_Base>) {
                var listOfItems: Json4Kotlin_Base? = response.body()

                val adapter = FragmentAdapter(listOfItems!!) {
                    Toast.makeText(context, "this is toast message", Toast.LENGTH_SHORT).show()

                }
                recyclerView_fragment.layoutManager = LinearLayoutManager(context)
                recyclerView_fragment.adapter = adapter
            }

            override fun onFailure(call: Call<Json4Kotlin_Base>, t: Throwable) {
                Toast.makeText(context, "this is toast message", Toast.LENGTH_SHORT).show()

            }

        })


    }
}