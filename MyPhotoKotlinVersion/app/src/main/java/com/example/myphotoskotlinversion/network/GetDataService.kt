package com.example.myphotoskotlinversion.network

import com.example.myphotoskotlinversion.model.Photo
import retrofit2.Call
import retrofit2.http.GET

interface GetDataService {
    @GET("/photos")
    fun getPhotos(): Call<MutableList<Photo>>
}