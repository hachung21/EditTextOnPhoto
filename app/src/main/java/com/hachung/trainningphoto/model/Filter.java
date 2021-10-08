package com.hachung.trainningphoto.model;

import android.graphics.Bitmap;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

import net.alhazmy13.imagefilter.ImageFilter;

import java.util.List;

public class Filter {
    private Bitmap img;

    public Filter(Bitmap img) {
        this.img = img;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    @BindingAdapter("profileImage")
    public static void loadImage(ImageView imageView, Bitmap avatar) {
        Glide.with(imageView.getContext()).load(avatar).into(imageView);
    }
}
