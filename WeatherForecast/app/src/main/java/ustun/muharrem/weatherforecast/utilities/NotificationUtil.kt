package ustun.muharrem.weatherforecast.utilities

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import ustun.muharrem.weatherforecast.R
import ustun.muharrem.weatherforecast.data.Forecast
import ustun.muharrem.weatherforecast.utilities.DrawableManager.getImageId

object NotificationUtil {

    private const val TODAY_FORECAST_CHANNEL_ID = "today_forecast_channel_id"
    private const val TODAY_FORECAST_NOTIFICATION_ID = 1000

    fun fireTodayNotification(context: Context, forecast: Forecast){
        val notification = NotificationCompat.Builder(context, TODAY_FORECAST_CHANNEL_ID)
            .setSmallIcon(R.drawable::class.java.getImageId(forecast.weather.icon))
            .setContentTitle(context.getString(R.string.today_notification_title))
            .setContentText(forecast.toString())
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()
        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(TODAY_FORECAST_NOTIFICATION_ID, notification)
    }

    fun createNotificationChannel(context: Context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.today_notification_channel_name)
            val descriptionText = context.getString(R.string.today_notification_channel_desc)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(TODAY_FORECAST_CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}