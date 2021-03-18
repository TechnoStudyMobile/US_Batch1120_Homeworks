package com.kryvovyaz.a96_weatherapplication.util

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import java.lang.reflect.Array.getInt

class Pref(context: Context) {

    val PREFS_C = "com.kryvovyaz.a96_weatherapplication.prefs"
    val PREFS_DAYS = "com.kryvovyaz.a96_weatherapplication.prefs"
    val PREFS_LOCATION = "com.kryvovyaz.a96_weatherapplication.prefs"
    private val UNITS_METRCIC = "unit_prefs"
    private val DAYS = "days_prefs"
    private val LONGITUDE = "longitude_prefs"
    private val LATITUDE = "latitude_prefs"
    private val prefsUnit: SharedPreferences =
        context.getSharedPreferences(PREFS_C, Context.MODE_PRIVATE);
    private val prefsDays: SharedPreferences =
        context.getSharedPreferences(PREFS_DAYS, Context.MODE_PRIVATE);

    var icCelsius: Boolean
        get() = prefsUnit.getBoolean(UNITS_METRCIC, false)
        set(value) = prefsUnit.edit().putBoolean(UNITS_METRCIC, value).apply()
    var days: Int
        get() = prefsDays.getInt(DAYS, 14)
        set(value) = prefsDays.edit().putInt(DAYS, value).apply()
    var location: Int
        get() = prefsDays.getInt(DAYS, 14)
        set(value) = prefsDays.edit().putInt(DAYS, value).apply()

}
