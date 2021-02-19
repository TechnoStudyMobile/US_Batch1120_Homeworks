package com.example.a55myphotos.View;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.a55myphotos.MainActivity;
import com.example.a55myphotos.R;
import com.example.a55myphotos.model.Photo;
import com.squareup.picasso.Picasso;
import java.util.List;

public class SinglePhotoActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView textView_title;
    private Photo photo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_photo);
        imageView = findViewById(R.id.single_image);
        textView_title = findViewById(R.id.single);
        photo = (Photo) getIntent().getSerializableExtra("MyObject");
        textView_title.setText(photo.getTitle());
        Picasso.get()
                .load(photo.getUrl())
                .into(imageView);
    }
}