package com.kryvovyaz.a96_weatherapplication.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.kryvovyaz.a96_weatherapplication.MainActivity
import com.kryvovyaz.a96_weatherapplication.R
import com.kryvovyaz.a96_weatherapplication.model.Forecast

object NotificationUtil {
    private const val TODAY_FORCAST_NOTIFICATION_ID = 1000
    private const val TODAY_FORECAST_CHANEL_ID = "today_forecast_chanel_id"

    fun fireTodayForecastNotification(context: Context, forecast: Forecast) {
        val intent = Intent(context, MainActivity::class.java)
       // intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        val contentShort = "Today's forecast : ${forecast.weather.description}."   //TODO:fix
        val contentLong =
            "Today weather:Max ${forecast.high_temp} and min is: ${forecast.low_temp}."//TODO:fix

        val notification = NotificationCompat.Builder(context, TODAY_FORECAST_CHANEL_ID)
            .setSmallIcon(R.drawable.ic_notification)//TODO : update icon
            .setContentTitle(context.getString(R.string.today_notification_title))
            .setContentText(contentShort)
            .setStyle(NotificationCompat.BigTextStyle().bigText(contentLong))//TODO:fix text
            .setContentIntent(pendingIntent)
                //TODO:adjust notifications priorities
            .setNotificationSilent()
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()
        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(TODAY_FORCAST_NOTIFICATION_ID, notification)
    }

    fun createNotificationChanel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.today_notofication_chanel_name)
            val descriptionText = context.getString(R.string.today_notofication_chanel_description)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(TODAY_FORECAST_CHANEL_ID, name, importance)
                .apply {
                    description = descriptionText
                }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}