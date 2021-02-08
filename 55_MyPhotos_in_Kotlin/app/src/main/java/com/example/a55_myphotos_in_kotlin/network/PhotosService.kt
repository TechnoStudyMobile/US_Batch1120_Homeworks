package com.example.a55_myphotos_in_kotlin.network

import com.example.a55_myphotos_in_kotlin.model.Photo
import retrofit2.Call
import retrofit2.http.GET

interface PhotosService {

    @GET("/photos")
    fun getPhotos(): Call<MutableList<Photo>>
}