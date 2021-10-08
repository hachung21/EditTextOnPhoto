package com.hachung.trainningphoto.model;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.Date;

public class Image implements Serializable {
    private String avatar;


    public Image() {
    }

    public Image(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @BindingAdapter("profileImage")
    public static void loadImage(ImageView imageView, String avatar) {
        Glide.with(imageView.getContext()).load(avatar).into(imageView);
    }
}
