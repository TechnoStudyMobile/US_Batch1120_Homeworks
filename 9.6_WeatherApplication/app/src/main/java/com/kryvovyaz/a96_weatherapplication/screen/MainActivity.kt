package com.kryvovyaz.a96_weatherapplication.screen

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.kryvovyaz.a96_weatherapplication.R
import com.kryvovyaz.a96_weatherapplication.screen.forecastlist.ForecastListFragment

class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val forecastListFragment = ForecastListFragment();
        val manager = supportFragmentManager
        manager.beginTransaction()
            .replace(R.id.fragment_container, forecastListFragment)
            .setReorderingAllowed(true)
            .addToBackStack(null)
            .commit()
    }
}