package com.kryvovyaz.a96_weatherapplication.util

object Capitalization {
    fun capitalizeWords(sting:String): String {
        return sting.split(" ").map { it.capitalize() }
            .joinToString(" ").replace(".","")
    }
}