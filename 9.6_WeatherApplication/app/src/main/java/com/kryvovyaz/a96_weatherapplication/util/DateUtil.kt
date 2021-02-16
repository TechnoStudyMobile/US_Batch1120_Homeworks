package com.kryvovyaz.a96_weatherapplication.util

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object DateUtil {
    @RequiresApi(Build.VERSION_CODES.O)
        fun formatDate(date: String): String {
        val formatter: DateTimeFormatter
        if (LocalDate.parse(date).isAfter(LocalDate.now().plusDays(6))  ) {
            formatter = DateTimeFormatter.ofPattern("EE, MMM dd")
        } else {
            formatter = DateTimeFormatter.ofPattern("EEEE")
        }
        return LocalDate.parse(date, DateTimeFormatter.ISO_DATE).format(formatter).toString()
    }
}