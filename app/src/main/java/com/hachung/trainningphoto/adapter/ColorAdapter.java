package com.hachung.trainningphoto.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hachung.trainningphoto.R;
import com.hachung.trainningphoto.databinding.LayoutItemColorTextBinding;
import com.hachung.trainningphoto.model.Color;
import com.hachung.trainningphoto.onclick.OnClickChangeTextListener;


import java.util.List;

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ViewHolder> {
    private List<Color> mColors;
    private OnClickChangeTextListener listener;

    public ColorAdapter(List<Color> mColors,OnClickChangeTextListener listener) {
        this.mColors = mColors;
        this.listener=listener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        LayoutItemColorTextBinding binding= DataBindingUtil.inflate(layoutInflater, R.layout.layout_item_color_text,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Color color=mColors.get(position);
        holder.binding.setColor(color);
        holder.binding.imgColor.setBackgroundColor(android.graphics.Color.parseColor(color.getColor()));
        holder.binding.imgColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickColor(color.getColor());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mColors.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LayoutItemColorTextBinding binding;

        public ViewHolder(@NonNull  LayoutItemColorTextBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
