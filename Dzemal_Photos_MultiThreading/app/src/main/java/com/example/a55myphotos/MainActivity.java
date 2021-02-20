package com.example.a55myphotos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.a55myphotos.model.Photo;
import com.example.a55myphotos.network.GetDataService;
import com.example.a55myphotos.network.RetrofitClient;
import com.example.a55myphotos.view.DoOnCLick;
import com.example.a55myphotos.view.PhotoAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view_in_main_activity);

        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        viewModel.getData();
        viewModel.getListLiveData().observe(this, new Observer() {

            @Override
            public void onChanged(Object o) {
                getRecycler(viewModel.getPhotos());
            }

        });


    }

    public void getRecycler(List<Photo> list) {

        PhotoAdapter adapter = new PhotoAdapter(list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setDoOnCLick(new DoOnCLick() {

        });
    }


}