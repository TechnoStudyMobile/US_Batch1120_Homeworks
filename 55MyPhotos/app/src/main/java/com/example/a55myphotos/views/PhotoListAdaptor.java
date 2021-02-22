package com.example.a55myphotos.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.a55myphotos.R;
import com.example.a55myphotos.model.Photo;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class PhotoListAdaptor extends RecyclerView.Adapter<PhotoListAdaptor.PhotoViewHolder> {

    private List<Photo> photoList = new ArrayList<>();

    public PhotoListAdaptor(List<Photo> photoList) {
        this.photoList = photoList;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_view_item, parent, false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        holder.photoTitle.setText(photoList.get(position).getTitle());
        Picasso.get().load(photoList.get(position).getUrl()).into(holder.photoImage);
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    class PhotoViewHolder extends RecyclerView.ViewHolder {
        private ImageView photoImage;
        private TextView photoTitle;

        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            photoImage = itemView.findViewById(R.id.photo_image);
            photoTitle = itemView.findViewById(R.id.photo_title);
        }
    }
}
