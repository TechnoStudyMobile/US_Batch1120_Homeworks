package com.example.a55myphotos.View;

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

import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {
    private GetPosition getPosition;

    public void setGetPosition(GetPosition getPosition) {
        this.getPosition = getPosition;
    }

    private List<Photo> list_photo;

    public PhotoAdapter(List<Photo> list_photo) {
        this.list_photo = list_photo;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_view_holder, parent, false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        holder.textView.setText(list_photo.get(position).getTitle());
        Picasso.get().load(list_photo.get(position).getUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return list_photo.size();
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            textView = itemView.findViewById(R.id.text);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getPosition.onClick(getAdapterPosition());
                }
            });

        }
    }
}
