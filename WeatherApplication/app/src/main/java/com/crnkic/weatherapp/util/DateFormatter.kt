package com.crnkic.weatherapp.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object DateFormatter {

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatDate(date: String): String {
        val formatter: DateTimeFormatter = if (LocalDate.parse(date).isAfter(LocalDate.now().plusDays(6))) {
            DateTimeFormatter.ofPattern("EE, MMM dd")
        } else {
            DateTimeFormatter.ofPattern("EEEE")
        }
        return LocalDate.parse(date, DateTimeFormatter.ISO_DATE).format(formatter).toString()
    }

}