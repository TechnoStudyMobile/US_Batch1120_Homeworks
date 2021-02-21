package com.samil.app.theweather

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.samil.app.theweather.screen.WeatherForecastFragment

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//   if (savedInstanceState == null){
//        supportFragmentManager.beginTransaction()
//        .replace(R.id.fragment_container_weather_forecast, WeatherForecastFragment())
//        .commit()
//}
        val weatherForecastFragment =
            WeatherForecastFragment()
        val manager = supportFragmentManager
        manager.beginTransaction()
            .replace(R.id.fragment_container_weather_forecast, weatherForecastFragment)
            .setReorderingAllowed(true)
            .addToBackStack(null)
            .commit()
    }
}