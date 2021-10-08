package com.hachung.trainningphoto.model;

import android.graphics.Bitmap;

public class Decoration {
    private Bitmap decortion;

    public Decoration(Bitmap decortion) {
        this.decortion = decortion;
    }

    public Bitmap getDecortion() {
        return decortion;
    }

    public void setDecortion(Bitmap decortion) {
        this.decortion = decortion;
    }
}
