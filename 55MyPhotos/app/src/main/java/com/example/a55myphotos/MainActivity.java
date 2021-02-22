package com.example.a55myphotos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import com.example.a55myphotos.model.Photo;
import com.example.a55myphotos.views.PhotoListAdaptor;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PhotoViewModel photoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        photoViewModel = new ViewModelProvider(this).get(PhotoViewModel.class);
        recyclerView = findViewById(R.id.recycler_View);

        photoViewModel.retrievePhotos();
        photoViewModel.photoListLiveData.observe(this, new Observer<List<Photo>>() {
            @Override
            public void onChanged(List<Photo> photoList) {
                createRecyclerView(photoList);
            }
        });
    }

    public void createRecyclerView(List<Photo> photoList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        PhotoListAdaptor adapter = new PhotoListAdaptor(photoList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}