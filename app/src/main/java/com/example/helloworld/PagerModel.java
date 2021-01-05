package com.example.helloworld;

import android.graphics.Bitmap;

public class PagerModel {
    String id;
    String title;
    Bitmap res;

    public PagerModel(String id, String title, Bitmap res){
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
    public Bitmap getResIdes(){
        return res;
    }

    public void setId(String id){
        this.id=id;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public void setRes(Bitmap res){
        this.res = res;
    }
}