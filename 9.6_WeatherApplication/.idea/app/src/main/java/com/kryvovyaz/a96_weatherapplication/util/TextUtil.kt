package com.kryvovyaz.a96_weatherapplication.util

object TextUtil {
    fun capitalizeWords(sting:String): String {
        return sting.split(" ").map { it.capitalize() }
            .joinToString(" ").replace(".","")
    }
}