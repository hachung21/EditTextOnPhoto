package com.hachung.trainningphoto.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.hachung.trainningphoto.R;
import com.hachung.trainningphoto.databinding.LayoutItemImageBinding;
import com.hachung.trainningphoto.model.Image;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private List<Image> images;
    private OnClickImageListener  listener;

    public ImageAdapter(List<Image> images, OnClickImageListener listener) {
        this.images = images;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        LayoutItemImageBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.layout_item_image, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Image image=images.get(position);
        holder.binding.setImage(image);
        holder.binding.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickItem(image.getAvatar());
            }
        });
    }
    public interface OnClickImageListener {
        void onClickItem(String image);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        LayoutItemImageBinding binding;

        public ViewHolder(@NonNull LayoutItemImageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
