package com.samil.app.theweather.utils

import android.app.Activity
import android.content.Context
import com.samil.app.theweather.IS_CELSIUS_DEFAULT_SETTINGS_VALUE
import com.samil.app.theweather.IS_CELSIUS_SETTINGS_PREF_KEY

object Prefs {
    fun retrieveIsCelsiusSetting(activity: Activity): Boolean {
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
        val value = sharedPref.getBoolean(IS_CELSIUS_SETTINGS_PREF_KEY, IS_CELSIUS_DEFAULT_SETTINGS_VALUE )
    return value
    }

    fun setIsCelsiusSetting(activity: Activity, value: Boolean) {
val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean(IS_CELSIUS_SETTINGS_PREF_KEY, value)
        editor?.apply()
    }


}

/*
//Save
val sharedPref = context?.
getSharedPreferences("com.samil.app.theweather.sharedPrefFile", Context.MODE_PRIVATE)
val sharedPref2 = activity?.getPreferences(Context.MODE_PRIVATE)


//Save
val editor = sharedPref?.edit()
editor?.putBoolean(IS_METRIC_SETTINGS_PREF_KEY,true)
editor?.apply()

//Retrieve
val value = sharedPref?.getBoolean("key", IS_METRIC_DEFAULT_SETTINGS_VALUE)

//Remove
editor?.remove("key")
editor?.apply()

//Clear all
editor?.clear()
editor?.apply()
*/