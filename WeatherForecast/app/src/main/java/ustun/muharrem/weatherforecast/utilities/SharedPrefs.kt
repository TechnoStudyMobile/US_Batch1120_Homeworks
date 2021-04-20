package ustun.muharrem.weatherforecast.utilities

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

object SharedPrefs {

    lateinit var application : Application

    val sharedPref: SharedPreferences
        get() = application.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    var isCelsius: Boolean
        get() = sharedPref.getBoolean(IS_CELSIUS_SETTING_KEY, IS_CELSIUS_DEFAULT_VALUE)
        set(value) = sharedPref.edit().putBoolean(IS_CELSIUS_SETTING_KEY, value).apply()

    var numberOfDays: String?
        get() = sharedPref.getString(NUMBER_OF_DAYS_SETTING_KEY, "16")
        set(value) = sharedPref.edit().putString(NUMBER_OF_DAYS_SETTING_KEY, value).apply()

    var notificationEnabled: Boolean
        get() = sharedPref.getBoolean(IS_NOTIFICATIONS_ENABLED, true)
        set(value) = sharedPref.edit().putBoolean(IS_NOTIFICATIONS_ENABLED, value).apply()

    var langCode: String?
        get() = sharedPref.getString(LANG_CODE_SETTINGS_KEY, null)
        set(value) = sharedPref.edit().putString(LANG_CODE_SETTINGS_KEY, value).apply()

    var lastEpochTime: Long
        get() = sharedPref.getLong(LAST_EPOCH_TIME_KEY, 0)
        set(value) = sharedPref.edit().putLong(LAST_EPOCH_TIME_KEY, value).apply()

    var lon: Double
        get() = sharedPref.getDouble(LONGITUDE_KEY, -1.0)
        set(value) = sharedPref.edit().putDouble(LONGITUDE_KEY, value).apply()

    var lat: Double
        get() = sharedPref.getDouble(LATITUDE_KEY, -1.0)
        set(value) = sharedPref.edit().putDouble(LATITUDE_KEY, value).apply()

    private fun SharedPreferences.Editor.putDouble(key: String, double: Double) =
        putLong(key, java.lang.Double.doubleToRawLongBits(double))

    private fun SharedPreferences.getDouble(key: String, default: Double) =
        java.lang.Double.longBitsToDouble(
            getLong(key, java.lang.Double.doubleToRawLongBits(default))
        )
}