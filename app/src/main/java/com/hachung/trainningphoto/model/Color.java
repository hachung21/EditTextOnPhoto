package com.hachung.trainningphoto.model;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

public class Color {
    private String color;

    public Color(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    @BindingAdapter("android:src")
    public static void loadTypo(ImageView imageView, String color) {
        Glide.with(imageView.getContext()).load(color).into(imageView);
    }

}
