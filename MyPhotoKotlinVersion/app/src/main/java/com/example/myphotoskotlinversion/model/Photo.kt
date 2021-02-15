package com.example.myphotoskotlinversion.model

import java.io.Serializable

data class Photo(
    val albumID: Int,
    val id: Int,
    val title: String,
    val url: String,
    val thumbnailURL: String
): Serializable