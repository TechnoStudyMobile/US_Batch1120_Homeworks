package com.example.a55myphotos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.a55myphotos.model.Photo;
import com.example.a55myphotos.network.GetDataService;
import com.example.a55myphotos.network.RetrofitClient;
import java.io.IOException;
import java.util.List;
import retrofit2.Call;
import retrofit2.Response;

public class PhotoViewModel extends ViewModel {

    private final MutableLiveData<List<Photo>> _photoListLiveData = new MutableLiveData<>();
    LiveData<List<Photo>> photoListLiveData = _photoListLiveData;

    public void retrievePhotos() {
        GetDataService getDataService = RetrofitClient.getRetrofit().create(GetDataService.class);
        Call<List<Photo>> callPhotos = getDataService.getPhotos();
        new Thread(() -> {
            try {
                Response<List<Photo>> response = callPhotos.execute();
                _photoListLiveData.postValue(response.body());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
