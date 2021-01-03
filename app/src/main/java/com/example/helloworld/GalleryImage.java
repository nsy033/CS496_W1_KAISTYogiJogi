package com.example.helloworld;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class GalleryImage {
    private String path;
    private Bitmap d;
    private Bitmap rd;

    public void setPath(String path) { this.path = path ;}
    public void setD(Bitmap d) { this.d = d; }
    public void setRd(Bitmap rd) {this.rd = rd; }
    public String getPath() {return path;}
    public Bitmap getD() {return d;}
    public Bitmap getRd() {return rd;}
}
