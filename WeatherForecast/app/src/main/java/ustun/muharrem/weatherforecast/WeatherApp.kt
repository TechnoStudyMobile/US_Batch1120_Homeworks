package ustun.muharrem.weatherforecast

import android.app.Application
import ustun.muharrem.weatherforecast.utilities.NotificationUtil
import ustun.muharrem.weatherforecast.utilities.SharedPrefs

class WeatherApp: Application() {

    override fun onCreate() {
        super.onCreate()
        SharedPrefs.application = this

//        NotificationUtil.createNotificationChannel(applicationContext)
    }
}