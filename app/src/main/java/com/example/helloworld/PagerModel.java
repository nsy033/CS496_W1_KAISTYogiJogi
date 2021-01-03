package com.example.helloworld;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class PagerModel {
    String id;
    String title;
    Drawable res;

    public PagerModel(String id, String title, Drawable res){
        this.id=id;
        this.title=title;
        this.res = res;
    }

    public String getId(){
        return id;
    }
    public String getTitle(){
        return title;
    }
    public Drawable getResIdes(){
        return res;
    }

    public void setId(String id){
        this.id=id;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public void setRes(Drawable res){
        this.res = res;
    }
}
