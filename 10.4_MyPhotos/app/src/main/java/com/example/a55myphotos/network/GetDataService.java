package com.example.a55myphotos.network;

import com.example.a55myphotos.model.Photo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetDataService {

    @GET("/photos")
    Call<List<Photo>> getPhotos();
}
