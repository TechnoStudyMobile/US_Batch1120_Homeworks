package com.example.a55myphotosretrofit_kotlinversion.network

import com.example.a55myphotosretrofit_kotlinversion.model.Photo
import retrofit2.Call
import retrofit2.http.GET

interface GetDataService {

    @GET("/photos")
    fun getPhotos(): Call<List<Photo>>


}