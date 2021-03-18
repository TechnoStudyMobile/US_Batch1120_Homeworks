package com.crnkic.weatherapp

import android.app.Application
import com.crnkic.weatherapp.util.NotificationUtil

class WeatherApp : Application() {

    override fun onCreate() {
        super.onCreate()

        NotificationUtil.createNotificationChannel(applicationContext)
    }

}