package com.hachung.trainningphoto.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.hachung.trainningphoto.R;
import com.hachung.trainningphoto.databinding.LayoutItemFontBinding;
import com.hachung.trainningphoto.model.Font;
import com.hachung.trainningphoto.onclick.OnClickChangeTextListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;

public class FontAdapter extends RecyclerView.Adapter<FontAdapter.ViewHolder> {
    private List<Font> mFontList;
    private OnClickChangeTextListener listener;

    public FontAdapter(List<Font> mFontList, OnClickChangeTextListener listener) {
        this.mFontList = mFontList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        LayoutItemFontBinding binding= DataBindingUtil.inflate(inflater, R.layout.layout_item_font,parent,false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Font font=mFontList.get(position);
        holder.binding.setFont(font);
        holder.binding.tvFont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickFont(font.getFont());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFontList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LayoutItemFontBinding binding;

        public ViewHolder(@NonNull  LayoutItemFontBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
