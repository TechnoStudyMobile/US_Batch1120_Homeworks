package com.kryvovyaz.a96_weatherapplication.util

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import java.lang.reflect.Array.getInt

class Pref(context: Context) {

    val PREFS = "com.kryvovyaz.a96_weatherapplication.prefs"
    private val UNITS_METRCIC = "unit_prefs"
    private val PHONE_LOCATION = "phone_location"
    private val NOTIFICATION = "notification"
    private val DAYS = "days_prefs"
    private val LONGITUDE = "longitude_prefs"
    private val LATITUDE = "latitude_prefs"
    private val ISPERMISSIONGRANTED = "is_permission_granted"
    private val preference: SharedPreferences =
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);

    var icCelsius: Boolean
        get() = preference.getBoolean(UNITS_METRCIC, false)
        set(value) = preference.edit().putBoolean(UNITS_METRCIC, value).apply()
    var days: Int
        get() = preference.getInt(DAYS, 14)
        set(value) = preference.edit().putInt(DAYS, value).apply()
    var lattitude: String?
        get() = preference.getString(LATITUDE, null)
        set(value) = preference.edit().putString(LATITUDE, value).apply()
    var longtitude: String?
        get() = preference.getString(LONGITUDE, null)
        set(value) =preference.edit().putString(LONGITUDE, value).apply()
    var isPhoneLocations: Boolean
        get() = preference.getBoolean(PHONE_LOCATION, true)
        set(value) = preference.edit().putBoolean(PHONE_LOCATION, value).apply()
    var isNotofication: Boolean
        get() = preference.getBoolean(NOTIFICATION, true)
        set(value) = preference.edit().putBoolean(NOTIFICATION, value).apply()
    var isPermissionGranted: Boolean
        get() = preference.getBoolean(ISPERMISSIONGRANTED, false)
        set(value) = preference.edit().putBoolean(ISPERMISSIONGRANTED, value).apply()
}
