package com.example.a55myphotos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.a55myphotos.model.Photo;
import com.example.a55myphotos.network.GetDataService;
import com.example.a55myphotos.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GetDataService getDataService = RetrofitClient.getRetrofit().create(GetDataService.class);
        Call<List<Photo>> callPhotos = getDataService.getPhotos();

        callPhotos.enqueue(new Callback<List<Photo>>() {
            @Override
            public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
                Log.d("MyPhotos", "Response: " + response.body());
                // We have a response
                List<Photo> photos = response.body();
                if (photos != null) {
                    for (Photo photo: photos){
                        Log.d("MyPhotos", "My Photos: " + photo.getTitle());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Photo>> call, Throwable t) {
                // Something is wrong. it's failed.
            }
        });
    }
}