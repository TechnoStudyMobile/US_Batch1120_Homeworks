package com.example.a55myphotos.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a55myphotos.R;
import com.example.a55myphotos.model.Photo;
import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {

    List<Photo> list;
    DoOnCLick doOnCLick = new DoOnCLick();

    public PhotoAdapter(List<Photo> list) {
        this.list = list;
    }

    public void setDoOnCLick(DoOnCLick doOnCLick) {
        this.doOnCLick = doOnCLick;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_main_activity, parent, false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        holder.title.setText(list.get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder {

        TextView title;

        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_in_view_holder);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    doOnCLick.doOnCLick(getAdapterPosition());

                }
            });
        }



    }


}
