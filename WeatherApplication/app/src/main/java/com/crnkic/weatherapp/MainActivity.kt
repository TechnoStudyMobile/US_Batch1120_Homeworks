package com.crnkic.weatherapplication

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.crnkic.weatherapplication.view.WeatherFragment

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, WeatherFragment::class.java, null)
                .setReorderingAllowed(false)
                .commit()
        }
    }
}