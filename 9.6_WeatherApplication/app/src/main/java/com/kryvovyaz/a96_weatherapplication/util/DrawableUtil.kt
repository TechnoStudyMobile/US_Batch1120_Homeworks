package com.kryvovyaz.a96_weatherapplication.util

import com.kryvovyaz.a96_weatherapplication.R

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