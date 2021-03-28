package com.kryvovyaz.a96_weatherapplication

import android.app.Application
import com.kryvovyaz.a96_weatherapplication.util.NotificationUtil
import com.kryvovyaz.a96_weatherapplication.util.Pref
class App : Application() {
    companion object {
        var prefs: Pref? = null
    }
    override fun onCreate() {
        super.onCreate()
        prefs = Pref(applicationContext)
        NotificationUtil.createNotificationChanel(applicationContext)
    }
}