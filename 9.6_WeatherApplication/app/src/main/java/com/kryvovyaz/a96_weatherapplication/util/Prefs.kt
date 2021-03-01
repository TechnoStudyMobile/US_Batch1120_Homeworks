package com.kryvovyaz.a96_weatherapplication.util

import android.app.Activity
import android.content.Context
import android.widget.Spinner

object Prefs {
    fun retrieveIsCelsiusSetting(activity: Activity): Boolean {
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
        return sharedPref.getBoolean(IS_CELSIUS_SETTING_PREF_KEY, IS_CELSIUS_DEFAULT_SETTINGS_VALUE)
    }

    fun setIsCelsiusSetting(activity: Activity, value: Boolean) {
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean(IS_CELSIUS_SETTING_PREF_KEY, value)
        editor.apply()
    }

    fun retrieveSpinnerPosition(activity: Activity): Int {
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
        return sharedPref.getInt(IS_DAYS_SELECTED_KEY, 0)
    }

    fun setDaysPreferenceSelected(activity: Activity, spinner: Spinner) {
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putInt(IS_DAYS_SELECTED_KEY, spinner.selectedItemPosition)
        editor.apply()
    }

    fun loadDaysSelected(activity: Activity): Int {
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
        val position = sharedPref.getInt(IS_DAYS_SELECTED_KEY, 0)
        return when (position) {
            0 -> 14
            else -> 7
        }
    }
}