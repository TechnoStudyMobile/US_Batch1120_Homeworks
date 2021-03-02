package com.crnkic.weatherapp.util

import android.app.Activity
import android.content.Context
import android.widget.Spinner
import com.DAYS_DEFAULT_SETTINGS_VALUE
import com.DAYS_SETTING_PREF_KEY
import com.IS_CELSIUS_DEFAULT_SETTINGS_VALUE
import com.IS_CELSIUS_SETTING_PREF_KEY

object Prefs {
    fun retrieveIsCelsiusSetting(activity: Activity): Boolean {
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
        return sharedPref.getBoolean(IS_CELSIUS_SETTING_PREF_KEY, IS_CELSIUS_DEFAULT_SETTINGS_VALUE)
    }

    fun setIsCelsiusSettings(activity: Activity, value: Boolean) {
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean(IS_CELSIUS_SETTING_PREF_KEY, value)
        editor.apply()
    }

    fun retrieveDaysSetting(activity: Activity): Int {
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
        return sharedPref.getInt(DAYS_SETTING_PREF_KEY, 0)
    }

    fun setDaysSettings(activity: Activity, spinner : Spinner) {
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putInt(DAYS_SETTING_PREF_KEY, spinner.selectedItemPosition)
        editor.apply()
    }

    fun loadDaysPositon(activity: Activity) : Int {
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
        val position = sharedPref.getInt(DAYS_SETTING_PREF_KEY, 0)
        return when (position) {
            0 -> 7
            else ->14
        }
    }


}

    /**

//    //Save
//    val sharedPref = context?.getSharedPreferences("com.crnkic.weatherapp.sharedPrefFile", Context.MODE_PRIVATE)
//    val sharedPref2 = activity?.getPreferences(Context.MODE_PRIVATE)
//
//    val editor = sharedPref?.edit()
//    editor?.putBoolean("key", true)
//    editor?.apply()
//
//    //Retrieve
//    val value = sharedPref.getBoolean("key", false)
//
//    //Remove
//    editor?.remove("key")
//
//    //Clear all
//    editor?.clear()
//    editor?.apply()


**/
