package com.samil.app.theweather.utils

import com.samil.app.theweather.R

object GetImageId {

    inline fun <reified T : Class<R.drawable>> T.getImageId(resourceName: String): Int {
        return try {
            val idField = getDeclaredField(resourceName)
            idField.getInt(idField)
        } catch (e: Exception) {
            e.printStackTrace()
            -1
        }
    }
}