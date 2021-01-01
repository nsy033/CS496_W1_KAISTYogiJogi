package com.example.myapplication;

public class PagerModel {
    String id;
    String title;
    int resId;

    public PagerModel(String id, String title, int resId){
        this.id=id;
        this.title=title;
        this.resId = resId;
    }

    public String getId(){
        return id;
    }
    public String getTitle(){
        return title;
    }
    public int getResIdesId(){
        return resId;
    }

    public void setId(String id){
        this.id=id;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public void setResId(int resId){
        this.resId = resId;
    }
}
