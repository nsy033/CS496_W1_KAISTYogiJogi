package com.example.helloworld;

import android.graphics.drawable.Drawable;

public class GridViewItemKaist {

    private Drawable dep_iconDrawable ; //학과아이콘
    private String dep_str ; // 학과이름


    public void setIcon(Drawable icon) { this.dep_iconDrawable = icon ; }
    public void setStr(String title) {
        this.dep_str = title ;
    }

    public Drawable getIcon() { return this.dep_iconDrawable ; }
    public String getStr() {
        return this.dep_str ;
    }

}
