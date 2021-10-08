package com.hachung.trainningphoto.adapter;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hachung.trainningphoto.R;
import com.hachung.trainningphoto.databinding.LayoutDecoratorBinding;
import com.hachung.trainningphoto.databinding.LayoutItemDecorationBinding;
import com.hachung.trainningphoto.model.Decoration;
import com.hachung.trainningphoto.model.Filter;
import com.hachung.trainningphoto.onclick.OnClickChangeStickerListener;

import java.util.List;

public class DecorationAdapter extends RecyclerView.Adapter<DecorationAdapter.ViewHolder> {
    List<Decoration> mlList;
    OnClickChangeStickerListener listener;



    public DecorationAdapter(List<Decoration> mlList, OnClickChangeStickerListener listener) {
        this.mlList = mlList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        LayoutItemDecorationBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.layout_item_decoration, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Decoration decoration = mlList.get(position);
        holder.binding.setDecoration(decoration);
        Glide.with(holder.binding.getRoot().getContext()).load(decoration.getDecortion()).into(holder.binding.imageView6);
        holder.binding.imageView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickTypo(decoration.getDecortion());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mlList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LayoutItemDecorationBinding binding;

        public ViewHolder(@NonNull LayoutItemDecorationBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
