package com.kryvovyaz.a96_weatherapplication

import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        Log.d("myApp", "New Token")

    }

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
        Log.d("myApp", "message received: ${p0.data}")
        if (p0.notification != null) {
            val title = p0.notification!!.title
            val body = p0.notification!!.body
            Log.d("myApp", "Title: $title, Body : $body")
            val builder = NotificationCompat.Builder(this)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.sunrise)//TODO:change icon here and in manifest
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setDefaults(NotificationCompat.DEFAULT_SOUND)
                .build()

            val manager = NotificationManagerCompat.from(applicationContext)
            manager.notify(1, builder)
        }
    }
}