package com.example.myphotos_kotlin.network

import com.example.myphotos_kotlin.model.Photo
import retrofit2.Call
import retrofit2.http.GET

interface PhotoService {
    @GET("/photos")
    fun getPhotos(): Call<List<Photo>>
}