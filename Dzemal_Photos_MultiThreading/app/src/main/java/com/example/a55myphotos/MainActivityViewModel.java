package com.example.a55myphotos;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.a55myphotos.model.Photo;
import com.example.a55myphotos.network.GetDataService;
import com.example.a55myphotos.network.RetrofitClient;
import com.example.a55myphotos.view.DoOnCLick;
import com.example.a55myphotos.view.PhotoAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityViewModel extends ViewModel {
    private final MutableLiveData<List<Photo>> listLiveData = new MutableLiveData();

    private List<Photo> photos;

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setText(List<Photo> list) {
        listLiveData.setValue(list);
    }

    public MutableLiveData<List<Photo>> getListLiveData() {
        return listLiveData;
    }

    public void getData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                GetDataService getDataService = RetrofitClient.getRetrofit().create(GetDataService.class);
                Call<List<Photo>> callPhotos = getDataService.getPhotos();

                callPhotos.enqueue(new Callback<List<Photo>>() {
                    @Override
                    public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
                        // We have a response
                        photos = response.body();
                        listLiveData.setValue(photos);

                        if (photos != null) {
                            for (Photo photo : photos) {
                                Log.d("MyPhotos", "My Photos: " + photo.getTitle());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Photo>> call, Throwable t) {


                    }
                });

            }
        }).start();


    }

}
