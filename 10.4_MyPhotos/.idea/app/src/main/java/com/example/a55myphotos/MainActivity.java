package com.example.a55myphotos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.a55myphotos.View.GetPosition;
import com.example.a55myphotos.View.PhotoAdapter;
import com.example.a55myphotos.View.SinglePhotoActivity;
import com.example.a55myphotos.model.Photo;
import com.example.a55myphotos.network.GetDataService;
import com.example.a55myphotos.network.RetrofitClient;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements Serializable {
    private RecyclerView recyclerView;
    public List<Photo> photos = new ArrayList<>();
    public Call<List<Photo>> callPhotos;
    protected Response<List<Photo>> response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerview);
        GetDataService getDataService = RetrofitClient.getRetrofit().create(GetDataService.class);
        callPhotos = getDataService.getPhotos();
        retrofitCall();
    }

    public void createRecycler() {
        PhotoAdapter adapter = new PhotoAdapter(photos);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setGetPosition(new GetPosition() {
            @Override
            public void onClick(int position) {

                Intent intent = new Intent(getApplicationContext(), SinglePhotoActivity.class);
                intent.putExtra("MyObject", photos.get(position));
                startActivity(intent);
            }
        });
    }

    public void retrofitCall() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    response = callPhotos.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                photos = response.body();
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        createRecycler();
                    }
                });
            }
        }).start();
    }
}