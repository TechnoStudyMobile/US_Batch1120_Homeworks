package com.crnkic.weatherapp.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.crnkic.weatherapp.MainActivity
import com.crnkic.weatherapp.R
import com.crnkic.weatherapp.model.Forecast

object NotificationUtil {
    private const val TODAY_FORECAST_CHANNEL_ID = "today_forecast_channel_id"
    private const val TODAY_FORECAST_NOTIFICATION_ID = 1000

    fun fireTodayForecastNotification(context: Context, forecast: Forecast) {
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)


        val contentShort = "Today weather is ${forecast.weather.description}"
        val contentLong = "Temperature is ${forecast.temp}, the low temperature is ${forecast.low_temp} " +
                "and the high temperature is ${forecast.high_temp}"

        val notification = NotificationCompat.Builder(context, TODAY_FORECAST_CHANNEL_ID)
            .setSmallIcon(R.drawable.a03d)
            .setContentTitle(context.getString(R.string.today_notification_title))
            .setContentText(contentShort)
            .setContentIntent(pendingIntent)
            .setStyle(NotificationCompat.BigTextStyle().bigText(contentLong))

            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(TODAY_FORECAST_NOTIFICATION_ID, notification)
    }

    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.today_notification_channel_name)
            val descriptionText = context.getString(R.string.today_notification_channel_description)
            val importance = NotificationManager.IMPORTANCE_HIGH
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