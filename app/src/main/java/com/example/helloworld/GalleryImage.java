package com.example.helloworld;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class GalleryImage {
    private String path;
    private Drawable d;
    private Drawable rd;

    public void setPath(String path) { this.path = path ;}
    public void setD(Drawable d) { this.d = d; }
    public void setRd(Drawable rd) {this.rd = d; }
    public String getPath() {return path;}
    public Drawable getD() {return d;}
    public Drawable getRd() {return rd;}
}
