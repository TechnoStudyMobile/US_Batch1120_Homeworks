package com.kryvovyaz.a96_weatherapplication.util

import java.security.Timestamp
import java.text.SimpleDateFormat
import java.util.*

object TimeUtil {
    fun convertTime(date:Int):String{
  return SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date(date.toLong()*1000))
    }
}