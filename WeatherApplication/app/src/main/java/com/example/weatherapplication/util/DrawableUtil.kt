package com.example.weatherapplication.util

import com.example.weatherapplication.R

object DrawableUtil {
    inline fun <reified T: Class<R.drawable>> T.getImageId(resourceName: String?): Int {
        return try {
            val idField = getDeclaredField (resourceName)
            idField.getInt(idField)
        } catch (e:Exception) {
            e.printStackTrace()
            -1
        }
    }
}