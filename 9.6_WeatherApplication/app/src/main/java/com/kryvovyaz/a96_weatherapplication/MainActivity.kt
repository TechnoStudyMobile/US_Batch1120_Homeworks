package com.kryvovyaz.a96_weatherapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.kryvovyaz.a96_weatherapplication.ForecastViewModel.model.Json4Kotlin_Base
import com.kryvovyaz.a96_weatherapplication.View.ForecastListFragment
import com.kryvovyaz.a96_weatherapplication.network.ForecastService
import com.kryvovyaz.a96_weatherapplication.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val getForecastService = RetrofitClient.instance?.create(ForecastService::class.java)
        getForecastService?.getForecast()?.enqueue(object : Callback<Json4Kotlin_Base> {
            override fun onResponse(
                call: Call<Json4Kotlin_Base>,
                response: Response<Json4Kotlin_Base>
            ) {
                Log.d("MyApp", "On response ${response.body().toString()}")
                response.body()?.let { callForecast ->
                    createWeaterListFragment(callForecast)
                }
            }

            override fun onFailure(call: Call<Json4Kotlin_Base>, t: Throwable) {
                Toast.makeText(
                    this@MainActivity, "Retrofit Failed", Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    fun createWeaterListFragment(forecastDay: Json4Kotlin_Base) {
        val forecastListFragment =
            ForecastListFragment(forecastDay);
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.fragment_container, forecastListFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
