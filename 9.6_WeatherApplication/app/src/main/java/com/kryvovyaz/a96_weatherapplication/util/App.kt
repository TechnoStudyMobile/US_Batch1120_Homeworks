package com.kryvovyaz.a96_weatherapplication.util

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager

val prefs: Pref by lazy {
    App.prefs!!
}

class App : Application() {
    var connected: Boolean = false
    companion object {
        var prefs: Pref? = null

        lateinit var instance: App
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        prefs = Pref(applicationContext)
    //create notification chanel
        NotificationUtil.createNotificationChanel(applicationContext)
    }
    fun isConnected(): Boolean {
        val cm = applicationContext
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork = cm.activeNetworkInfo
        connected = activeNetwork != null && activeNetwork.isConnectedOrConnecting
        return connected
    }

}