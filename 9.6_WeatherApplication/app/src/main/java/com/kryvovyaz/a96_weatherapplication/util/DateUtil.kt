package com.kryvovyaz.a96_weatherapplication.util
import java.text.SimpleDateFormat
import java.util.*

object DateUtil {
    fun formatDate(valid_date: String, position: Int): String {
        val date =SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(valid_date)
        return when (position) {
            0 -> "Today"
           in 1..6 -> let { SimpleDateFormat("EEEE", Locale.getDefault()).format(date) }
            else -> SimpleDateFormat("EEE, MMM dd", Locale.getDefault()).format(date)
        }
    }
}