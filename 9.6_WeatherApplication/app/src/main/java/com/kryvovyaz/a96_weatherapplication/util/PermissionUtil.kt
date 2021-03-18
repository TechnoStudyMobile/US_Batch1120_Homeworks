package com.kryvovyaz.a96_weatherapplication.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat


object PermissionUtil {
    const val permissionRequested = 1000
    fun isLocationPermissionGranted(context: Context):Boolean  {

        return ContextCompat.checkSelfPermission(context, Manifest.permission
            .ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED
    }
    fun showLocationPermissionGranted(activity: AppCompatActivity){

    }
}