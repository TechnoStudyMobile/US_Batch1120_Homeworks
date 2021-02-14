package com.example.a96_weatherapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.a96_weatherapplication.View.ForecastListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val forecastListFragment: ForecastListFragment = ForecastListFragment();
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.fragment_container, forecastListFragment)
        transaction.addToBackStack(null)
        transaction.commit()





    }
}